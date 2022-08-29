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
        "jar": "JAR file to deploy",
        "srcjar": "JAR file with sources",
        "pom": "Accompanying pom.xml file",
    }
)


def _jvm_export_impl(ctx):
    version = ctx.attr.version[VersionInfo].value
    pom_file = _generate_pom_file(ctx, version)
    class_jar = runtime_output_jar(ctx.attr.target)
    source_jar = _source_jar(ctx.attr.target)
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


def _generate_pom_file(ctx, version):
    target = ctx.attr.target
    if not target[JarInfo].name:
        fail("{} missing 'maven_coordinates=' tags".format(target))

    maven_coordinates = parse_maven_coordinates(target[JarInfo].name)
    pom_file = ctx.actions.declare_file("{}_pom.xml".format(ctx.attr.name))

    pom_deps = []
    for pom_dependency in [
        dep[JarInfo] for dep in target[JarInfo].deps if dep[JarInfo].name
    ]:
        pom_dependency = pom_dependency.name
        if pom_dependency == target[JarInfo].name:
            continue
        pom_dependency_coordinates = parse_maven_coordinates(pom_dependency, False)
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


def _source_jar(target):
    if len(target[JavaInfo].source_jars) < 1:
        fail("Could not find source JAR to deploy in {}".format(target))
    else:
        return target[JavaInfo].source_jars[0]

jvm_export = rule(
    attrs={
        "target": attr.label(
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
