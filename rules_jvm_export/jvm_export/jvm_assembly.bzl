VersionInfo = provider(
    doc="A singleton provider that contains the raw value of a build setting",
    fields={"value": "Version number"},
)


def _jvm_assembly_impl(ctx):
    # version = ctx.attr.version[VersionInfo].value
    class_jar = generate_class_jar(ctx, None)
    source_jar = _generate_source_jar(ctx)
    output_files = [class_jar]
    return [
        DefaultInfo(
            files=depset(output_files),
        ),
        JavaInfo(
            output_jar=class_jar,
            compile_jar=class_jar,
            source_jar=source_jar,
        ),
    ]


def parse_maven_coordinates(coordinates_string, enforce_version_template=True):
    """
    Given a string containing a standard Maven coordinate (g:a:[p:[c:]]v),
    returns a Maven artifact map (see above).
    See also https://github.com/bazelbuild/rules_jvm_external/blob/4.3/specs.bzl
    """
    parts = coordinates_string.split(":")
    group_id, artifact_id = parts[0:2]
    if len(parts) == 3:
        version = parts[2]
        result = struct(group_id=group_id, artifact_id=artifact_id, version=version)
    elif len(parts) == 4:
        packaging = parts[2]
        version = parts[3]
        result = struct(group_id=group_id, artifact_id=artifact_id, packaging=packaging, version=version)
    elif len(parts) == 5:
        packaging = parts[2]
        classifier = parts[3]
        version = parts[4]
        result = struct(group_id=group_id, artifact_id=artifact_id, packaging=packaging, classifier=classifier, version=version)
    else:
        fail("failed to parse '{}'".format(coordinates_string))

    if enforce_version_template and version != "{pom_version}":
        fail("should assign {pom_version} as Maven version via `tags` attribute")
    return result

def jar_assembler(ctx):
    script = ctx.actions.declare_file(
        "jvm-export/{}-jar-assembler.py".format(ctx.attr.name)
    )
    ctx.actions.expand_template(
        template=ctx.file._jar_assembler,
        output=script,
        substitutions={
            "$PYTHON_PATH": ctx.attr.python_path,
        },
    )
    return script


def runtime_output_jar(target):
    if JavaInfo in target:
        if len(target[JavaInfo].runtime_output_jars) == 1:
            return target[JavaInfo].runtime_output_jars[0]
        elif len(target[JavaInfo].runtime_output_jars) == 2:
            for jar in target[JavaInfo].runtime_output_jars:
                if jar.path.endswith("_java.jar"):
                    return jar

            fail(
                "expected size 1, or the file name ends with _java.jar, but runtime_output_jars in {} was {}".format(
                    target, target[JavaInfo].runtime_output_jars
                )
            )
        else:
            fail(
                "expected size 1, but runtime_output_jars in {} was {}".format(
                    target, target[JavaInfo].runtime_output_jars
                )
            )
    else:
        outputs = target[DefaultInfo].files.to_list()
        return outputs[0]


def generate_class_jar(ctx, pom_file):
    target = ctx.attr.target
    maven_coordinates = parse_maven_coordinates(target[JarInfo].name)

    jar = runtime_output_jar(target)
    output_jar = ctx.actions.declare_file(
        "{}:{}.jar".format(maven_coordinates.group_id, maven_coordinates.artifact_id)
    )

    class_jar_deps = [
        dep.class_jar
        for dep in target[JarInfo].jar_infos.to_list()
        if dep.type == "jar"
    ]
    class_jar_paths = [jar.path] + [target.path for target in class_jar_deps]

    args = (
        [
            "--group_id=" + maven_coordinates.group_id,
            "--artifact_id=" + maven_coordinates.artifact_id,
        ]
        + ([pom_file] if pom_file else [])
        + [
            "--output=" + output_jar.path,
        ]
        + class_jar_paths
    )
    inputs = [jar] + ([pom_file] if pom_file else []) + class_jar_deps
    ctx.actions.run(
        executable=jar_assembler(ctx),
        inputs=inputs,
        outputs=[output_jar],
        arguments=args,
    )

    return output_jar


def _generate_source_jar(ctx):
    target = ctx.attr.target
    maven_coordinates = parse_maven_coordinates(target[JarInfo].name)

    srcjar = None
    if len(target[JavaInfo].source_jars) < 1:
        fail("Could not find source JAR to deploy in {}".format(target))
    else:
        srcjar = target[JavaInfo].source_jars[0]

    output_jar = ctx.actions.declare_file(
        "{}:{}-sources.jar".format(
            maven_coordinates.group_id, maven_coordinates.artifact_id
        )
    )

    source_jar_deps = [
        dep.source_jar
        for dep in target[JarInfo].jar_infos.to_list()
        if dep.type == "jar" and dep.source_jar
    ]
    source_jar_paths = [srcjar.path] + [target.path for target in source_jar_deps]

    ctx.actions.run(
        executable=jar_assembler(ctx),
        inputs=[srcjar] + source_jar_deps,
        outputs=[output_jar],
        arguments=[
            "--output=" + output_jar.path,
        ]
        + source_jar_paths,
    )

    return output_jar


def find_maven_coordinates(target, tags):
    _TAG_KEY_MAVEN_COORDINATES = "maven_coordinates="
    _TAG_KEY_JVM_MODULE = "jvm_module="
    _TAG_KEY_JVM_VERSION = "jvm_version="
    mod = None
    ver = None
    for tag in tags:
        if tag.startswith(_TAG_KEY_MAVEN_COORDINATES):
            coordinates = tag[len(_TAG_KEY_MAVEN_COORDINATES) :]
            return coordinates
        elif tag.startswith(_TAG_KEY_JVM_MODULE):
            mod = tag[len(_TAG_KEY_JVM_MODULE) :]
        elif tag.startswith(_TAG_KEY_JVM_VERSION):
            ver = tag[len(_TAG_KEY_JVM_VERSION) :]

    if mod and ver:
        return "{}:{}".format(mod, ver)


JarInfo = provider(
    fields={
        "name": "The name of a the JAR (Maven coordinates)",
        "deps": "Direct dependencies",
        "jar_infos": "The list of dependencies of this JAR. A dependency may be of two types, POM or JAR.",
        "neverlink": "Forward neverlink from target",
    },
)


def _aggregate_dependency_info_impl(target, ctx):
    tags = getattr(ctx.rule.attr, "tags", [])
    deps = getattr(ctx.rule.attr, "deps", [])
    runtime_deps = getattr(ctx.rule.attr, "runtime_deps", [])
    exports = getattr(ctx.rule.attr, "exports", [])
    deps_all = deps + exports + runtime_deps
    neverlink = getattr(ctx.rule.attr, "neverlink", False)

    maven_coordinates = find_maven_coordinates(target, tags)
    dependencies = []

    # depend via POM
    if maven_coordinates:
        dependencies = [struct(type="pom", maven_coordinates=maven_coordinates)]
    # include runtime output jars
    elif (JavaInfo in target) and target[JavaInfo].runtime_output_jars:
        jars = target[JavaInfo].runtime_output_jars
        source_jars = target[JavaInfo].source_jars
        dependencies = [
            struct(
                type="jar",
                class_jar=jar,
                source_jar=source_jar,
            )
            for (jar, source_jar) in zip(
                jars, source_jars + [None] * (len(jars) - len(source_jars))
            )
        ]

    return JarInfo(
        name=maven_coordinates,
        deps=deps,
        jar_infos=depset(
            dependencies,
            transitive=[
                # Filter transitive JARs from dependency that has maven coordinates
                # because those dependencies will already include the JARs as part
                # of their classpath
                depset(
                    [
                        dep
                        for dep in jar[JarInfo].jar_infos.to_list()
                        if dep.type == "pom"
                    ]
                )
                if jar[JarInfo].name
                else jar[JarInfo].jar_infos
                for jar in deps_all
            ],
        ),
        neverlink=neverlink,
    )


aggregate_dependency_info = aspect(
    attr_aspects=[
        "jars",
        "deps",
        "exports",
        "runtime_deps",
    ],
    doc="Collects the Maven coordinates of the given java_library, its direct dependencies, and its transitive dependencies",
    implementation=_aggregate_dependency_info_impl,
    provides=[JarInfo],
)

jvm_assembly = rule(
    attrs={
        "target": attr.label(
            mandatory=True,
            aspects=[
                aggregate_dependency_info,
            ],
            doc="Java target for subsequent deployment",
        ),
        "python_path": attr.string(
            default="/usr/bin/env python",
            doc="Path to python command",
        ),
        "_jar_assembler": attr.label(
            default="@twitter_rules_jvm_export//jvm_export/support:jar_assembler.py",
            executable=True,
            allow_single_file=True,
            cfg="host",
        ),
    },
    implementation=_jvm_assembly_impl,
    doc="Aggregated JVM target",
)
