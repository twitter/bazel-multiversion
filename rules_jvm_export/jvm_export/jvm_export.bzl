# Based on https://github.com/vaticle/bazel-distribution/blob/master/maven/rules.bzl
# SPDX-License-Identifier: Apache-2.0

JvmExportInfo = provider(
    fields={
        "jar": "JAR file to deploy",
        "srcjar": "JAR file with sources",
        "pom": "Accompanying pom.xml file",
    }
)


VersionInfo = provider(
    doc="A singleton provider that contains the raw value of a build setting",
    fields={"value": "Version number"},
)


def _jvm_export_impl(ctx):
    version = ctx.attr.version[VersionInfo].value
    pom_file = _generate_pom_file(ctx, version)
    if ctx.attr.assemble:
        class_jar = _generate_class_jar(ctx, pom_file)
    else:
        class_jar = _class_jar(ctx.attr.target)
    source_jar = _generate_source_jar(ctx)

    deploy_script = ctx.actions.declare_file(
        "jvm-export/{}-deploy.py".format(ctx.attr.name)
    )
    lib_jar_link = "lib.jar"
    src_jar_link = "lib.srcjar"
    pom_xml_link = pom_file.basename
    ctx.actions.expand_template(
        template=ctx.file._deployment,
        output=deploy_script,
        substitutions={
            "$JAR_PATH": lib_jar_link,
            "$SRCJAR_PATH": src_jar_link,
            "$POM_PATH": pom_xml_link,
            "$PYTHON_PATH": ctx.attr.python_path,
            "{snapshot}": ctx.attr.snapshot_repo,
            "{release}": ctx.attr.release_repo,
        },
    )
    output_files = [pom_file, class_jar]

    symlinks = {
        lib_jar_link: class_jar,
        pom_xml_link: pom_file,
    }
    if source_jar:
        output_files.append(source_jar)
        symlinks[src_jar_link] = source_jar

    return [
        DefaultInfo(
            executable=deploy_script,
            files=depset(output_files),
            runfiles=ctx.runfiles(files=output_files, symlinks=symlinks),
        ),
        JvmExportInfo(
            jar=class_jar,
            pom=pom_file,
            srcjar=source_jar,
        ),
    ]


def _parse_maven_coordinates(coordinates_string, enforce_version_template=True):
    coordinates = coordinates_string.split(":")
    # Maven coordinates in the bazel ecosystem can include more than three fields.
    # The group and artifact IDs are always the first 2 and the version is always the last field.
    group_id, artifact_id = coordinates[0:2]
    version = coordinates[-1]
    if enforce_version_template and version != "{pom_version}":
        fail("should assign {pom_version} as Maven version via `tags` attribute")
    return struct(group_id=group_id, artifact_id=artifact_id, version=version)


def _generate_pom_file(ctx, version):
    target = ctx.attr.target
    maven_coordinates = _parse_maven_coordinates(target[JarInfo].name)
    pom_file = ctx.actions.declare_file("{}_pom.xml".format(ctx.attr.name))

    pom_deps = []
    for pom_dependency in [
        dep for dep in target[JarInfo].deps.to_list() if dep.type == "pom"
    ]:
        pom_dependency = pom_dependency.maven_coordinates
        if pom_dependency == target[JarInfo].name:
            continue
        pom_dependency_coordinates = _parse_maven_coordinates(pom_dependency, False)
        pom_dependency_artifact = (
            pom_dependency_coordinates.group_id
            + ":"
            + pom_dependency_coordinates.artifact_id
        )
        pom_dependency_version = pom_dependency_coordinates.version

        v = ctx.attr.version_overrides.get(
            pom_dependency_artifact, pom_dependency_version
        )
        pom_deps.append(pom_dependency_artifact + ":" + v)

    pom_gen_script = ctx.actions.declare_file(
        "jvm-export/{}-pom-gen.py".format(ctx.attr.name)
    )
    ctx.actions.expand_template(
        template=ctx.file._pom_generator,
        output=pom_gen_script,
        substitutions={
            "$PYTHON_PATH": ctx.attr.python_path,
        },
    )
    ctx.actions.run(
        executable=pom_gen_script,
        inputs=[],
        outputs=[pom_file],
        arguments=[
            "--group_id=" + maven_coordinates.group_id,
            "--artifact_id=" + maven_coordinates.artifact_id,
            "--version=" + version,
            "--project_name=" + ctx.attr.project_name,
            "--project_description=" + ctx.attr.project_description,
            "--project_url=" + ctx.attr.project_url,
            "--license=" + ctx.attr.license,
            "--scm_url=" + ctx.attr.scm_url,
            "--target_deps_coordinates=" + ";".join(pom_deps),
            "--output_file=" + pom_file.path,
        ],
    )

    return pom_file


def _jar_assembler(ctx):
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


def _class_jar(target):
    if len(target[JavaInfo].runtime_output_jars) == 1:
        return target[JavaInfo].runtime_output_jars[0]
    else:
        fail(
            "expected size 1, but runtime_output_jars in {} was {}".format(
                target, target[JavaInfo].runtime_output_jars
            )
        )


def _generate_class_jar(ctx, pom_file):
    target = ctx.attr.target
    maven_coordinates = _parse_maven_coordinates(target[JarInfo].name)

    jar = _class_jar(target)
    output_jar = ctx.actions.declare_file(
        "{}:{}.jar".format(maven_coordinates.group_id, maven_coordinates.artifact_id)
    )

    class_jar_deps = [
        dep.class_jar for dep in target[JarInfo].deps.to_list() if dep.type == "jar"
    ]
    class_jar_paths = [jar.path] + [target.path for target in class_jar_deps]

    ctx.actions.run(
        executable=_jar_assembler(ctx),
        inputs=[jar, pom_file] + class_jar_deps,
        outputs=[output_jar],
        arguments=[
            "--group_id=" + maven_coordinates.group_id,
            "--artifact_id=" + maven_coordinates.artifact_id,
            "--pom_file=" + pom_file.path,
            "--output=" + output_jar.path,
        ]
        + class_jar_paths,
    )

    return output_jar


def _generate_source_jar(ctx):
    target = ctx.attr.target
    maven_coordinates = _parse_maven_coordinates(target[JarInfo].name)

    srcjar = None
    if len(target[JavaInfo].source_jars) < 1:
        fail("Could not find source JAR to deploy in {}".format(target))
    else:
        srcjar = target[JavaInfo].source_jars[0]

    if not ctx.attr.assemble:
        return srcjar

    output_jar = ctx.actions.declare_file(
        "{}:{}-sources.jar".format(
            maven_coordinates.group_id, maven_coordinates.artifact_id
        )
    )

    source_jar_deps = [
        dep.source_jar
        for dep in target[JarInfo].deps.to_list()
        if dep.type == "jar" and dep.source_jar
    ]
    source_jar_paths = [srcjar.path] + [target.path for target in source_jar_deps]

    ctx.actions.run(
        executable=_jar_assembler(ctx),
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
        "deps": "The list of dependencies of this JAR. A dependency may be of two types, POM or JAR.",
    },
)


def _aggregate_dependency_info_impl(target, ctx):
    tags = getattr(ctx.rule.attr, "tags", [])
    deps = getattr(ctx.rule.attr, "deps", [])
    runtime_deps = getattr(ctx.rule.attr, "runtime_deps", [])
    exports = getattr(ctx.rule.attr, "exports", [])
    deps_all = deps + exports + runtime_deps

    maven_coordinates = find_maven_coordinates(target, tags)
    dependencies = []

    # depend via POM
    if maven_coordinates:
        dependencies = [struct(type="pom", maven_coordinates=maven_coordinates)]
    # include runtime output jars
    elif target[JavaInfo].runtime_output_jars:
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
    else:
        fail("Unsure how to package dependency for target: %s" % target)

    return JarInfo(
        name=maven_coordinates,
        deps=depset(
            dependencies,
            transitive=[
                # Filter transitive JARs from dependency that has maven coordinates
                # because those dependencies will already include the JARs as part
                # of their classpath
                depset(
                    [dep for dep in target[JarInfo].deps.to_list() if dep.type == "pom"]
                )
                if target[JarInfo].name
                else target[JarInfo].deps
                for target in deps_all
            ],
        ),
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

jvm_export = rule(
    attrs={
        "target": attr.label(
            mandatory=True,
            aspects=[
                aggregate_dependency_info,
            ],
            doc="Java target for subsequent deployment",
        ),
        "version": attr.label(
            doc="""
            A target that represents version string.
            Alternatively, pass --@twitter_rules_jvm_export//jvm_export:version=0.1.0
            to Bazel invocation.
            """,
            default="@twitter_rules_jvm_export//jvm_export:version",
            providers=[VersionInfo],
        ),
        "version_overrides": attr.string_dict(
            default={},
            doc="Dictionary of maven artifact : version to pin artifact versions to",
        ),
        "project_name": attr.string(
            mandatory=True,
            doc="Project name to fill into pom.xml",
        ),
        "project_description": attr.string(
            default="PROJECT_DESCRIPTION",
            doc="Project description to fill into pom.xml",
        ),
        "project_url": attr.string(
            default="PROJECT_URL",
            doc="Project URL to fill into pom.xml",
        ),
        "license": attr.string(
            values=["Apache-2.0", "MIT"],
            default="Apache-2.0",
            doc="Project license to fill into pom.xml",
        ),
        "scm_url": attr.string(
            default="PROJECT_URL",
            doc="Project source control URL to fill into pom.xml",
        ),
        "snapshot_repo": attr.string(
            mandatory=True,
            doc="Snapshot repository to release maven artifacts",
        ),
        "release_repo": attr.string(
            mandatory=True,
            doc="Release repository to release maven artifacts",
        ),
        "python_path": attr.string(
            default="/usr/bin/env python",
            doc="Path to python command",
        ),
        "assemble": attr.bool(
            default=False,
            doc="Aggregate JAR files into one über JAR",
        ),
        "_pom_generator": attr.label(
            default="@twitter_rules_jvm_export//jvm_export/support:pom_generator.py",
            executable=True,
            allow_single_file=True,
            cfg="host",
        ),
        "_jar_assembler": attr.label(
            default="@twitter_rules_jvm_export//jvm_export/support:jar_assembler.py",
            executable=True,
            allow_single_file=True,
            cfg="host",
        ),
        "_deployment": attr.label(
            allow_single_file=True,
            default="@twitter_rules_jvm_export//jvm_export/support:deploy.py",
        ),
    },
    executable=True,
    implementation=_jvm_export_impl,
    doc="Publish Java package to a Maven repo",
)


def _jvm_export_version_impl(ctx):
    value = ctx.build_setting_value
    return VersionInfo(value=value)


_jvm_export_version = rule(
    implementation=_jvm_export_version_impl,
    build_setting=config.string(flag=True),
    doc="Version used to publish JVM artifacts",
)


def jvm_export_version(name, version):
    return _jvm_export_version(
        name=name,
        build_setting_default=version,
        visibility=["//visibility:public"],
    )
