# Based on https://github.com/vaticle/bazel-distribution/blob/master/maven/rules.bzl
# SPDX-License-Identifier: Apache-2.0

load(":jvm_assembly.bzl",
    "aggregate_dependency_info",
    "JarInfo",
    _jvm_assembly="jvm_assembly",
    "parse_maven_coordinates",
    "runtime_output_jar",
    "VersionInfo",
)

JvmExportInfo = provider(
    fields={
        "artifacts": "JAR file to deploy",
        "pom": "Accompanying pom.xml file",
    }
)


SOURCES_CLASSIFIER = "sources"
JAVADOC_CLASSIFIER = "javadoc"

def _jvm_export_impl(ctx):
    version = ctx.attr.version[VersionInfo].value
    pom_file = _generate_pom_file(ctx, version)
    output_files = [pom_file]
    pom_xml_link = pom_file.basename
    symlinks = {
        pom_xml_link: pom_file,
    }
    source_jar = None
    # create a dictionary of class_jar symlinks and classifier
    artifacts = {}
    for artifact in ctx.attr.artifacts:
        this_coordinate = parse_maven_coordinates(artifact[JarInfo].name)
        classifier = getattr(this_coordinate, "classifier", "")
        class_jar = runtime_output_jar(artifact)
        output_files.append(class_jar)
        symlinks[class_jar.basename] = class_jar
        artifacts[classifier] = class_jar.basename
        if not classifier:
            source_jar = _source_jar(artifact)
            if source_jar:
                output_files.append(source_jar)
                src_jar_link = "lib.srcjar"
                symlinks[src_jar_link] = source_jar
                artifacts[SOURCES_CLASSIFIER] = src_jar_link

    # TODO(vmax): use real Javadoc instead of srcjar
    if (SOURCES_CLASSIFIER in artifacts) and (JAVADOC_CLASSIFIER not in artifacts):
        artifacts[JAVADOC_CLASSIFIER] = artifacts[SOURCES_CLASSIFIER]

    deploy_script = ctx.actions.declare_file(
        "jvm-export/{}-deploy.py".format(ctx.attr.name)
    )
    pom_xml_link = pom_file.basename
    ctx.actions.expand_template(
        template=ctx.file._deployment,
        output=deploy_script,
        substitutions={
            "$ARTIFACTS": str(artifacts),
            "$POM_PATH": pom_xml_link,
            "$PYTHON_PATH": ctx.attr.python_path,
            "{snapshot}": ctx.attr.snapshot_repo,
            "{release}": ctx.attr.release_repo,
        },
    )
    return [
        DefaultInfo(
            executable=deploy_script,
            files=depset(output_files),
            runfiles=ctx.runfiles(files=output_files, symlinks=symlinks),
        ),
        JvmExportInfo(
            artifacts=artifacts,
            pom=pom_file,
        ),
    ]


def _generate_pom_file(ctx, version):
    artifacts = ctx.attr.artifacts
    for artifact in artifacts:
        if not artifact[JarInfo].name:
            fail("{} missing 'maven_coordinates=' tags".format(artifact))

    pom_file = ctx.actions.declare_file("{}_pom.xml".format(ctx.attr.name))
    maven_coordinates0 = parse_maven_coordinates(artifacts[0][JarInfo].name)

    pom_deps = []

    # Note that all dependencies in all artifacts will be appended to
    # the same POM as dependencies.
    for artifact in artifacts:
        this_coordinate = parse_maven_coordinates(artifact[JarInfo].name)
        for pom_dependency in [
            dep[JarInfo] for dep in artifact[JarInfo].deps if dep[JarInfo].name
        ]:
            pom_dependency_coordinates = parse_maven_coordinates(pom_dependency.name, False)
            if pom_dependency == this_coordinate:
                continue
            pom_dependency_artifact = (
                pom_dependency_coordinates.group_id
                + ":"
                + pom_dependency_coordinates.artifact_id
            )
            pom_dependency_version = pom_dependency_coordinates.version
            pom_dependency_classifier = getattr(pom_dependency_coordinates, "classifier", "")
            pom_dependency_packaging = getattr(pom_dependency_coordinates, "packaging", "jar")
            pom_dependency_neverlink = getattr(pom_dependency, "neverlink", False)
            if pom_dependency_neverlink:
                pom_dependency_classifier = "provided"
            v = ctx.attr.version_overrides.get(
                pom_dependency_artifact, pom_dependency_version
            )
            pom_dep = "{}:{}".format(pom_dependency_artifact, v)
            if pom_dependency_classifier:
                pom_dep = "{}:{}:{}:{}".format(
                    pom_dependency_artifact,
                    pom_dependency_packaging,
                    pom_dependency_classifier,
                    v)
            pom_deps.append(pom_dep)

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
            "--group_id=" + maven_coordinates0.group_id,
            "--artifact_id=" + maven_coordinates0.artifact_id,
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


def _source_jar(target):
    if JavaInfo in target:
        return target[JavaInfo].source_jars[0]
    else:
        return None


def make_jvm_export_rule():
  return rule(
    attrs={
        "artifacts": attr.label_list(
            mandatory=True,
            doc="Java target for subsequent deployment",
            aspects=[
                aggregate_dependency_info,
            ],
            # providers=[JavaInfo],
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
        "_pom_generator": attr.label(
            default="@twitter_rules_jvm_export//jvm_export/support:pom_generator.py",
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


jvm_export = make_jvm_export_rule()


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


jvm_assembly = _jvm_assembly
