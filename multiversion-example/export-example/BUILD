load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library")
load("@twitter_rules_jvm_export//jvm_export:jvm_export.bzl", "jvm_assembly", "jvm_export")

scala_library(
    name = "io1",
    srcs = ["IO1.scala"],
    tags = [
        "maven_coordinates=com.twitter.dpb:io1:{pom_version}",
    ],
    deps = [
        "@maven//:3rdparty/jvm/com/google/guava/guava",
    ],
)

jvm_export(
    name = "io1.publish",
    target = ":io1",
    project_name = "IO 1",
    project_description = "IO library",
    project_url = "http://example.com/",
    license = "Apache-2.0",
    scm_url = "http://github.com/",
    release_repo = "https://localhost/",
    snapshot_repo = "https://localhost/",
    # python_path = "/opt/ee/python/3.10/bin/python3.10",
)

jvm_assembly(
    name = "io1.assembly",
    target = ":io1",
    tags = [
        "maven_coordinates=com.twitter.dpb:io1-assembly:{pom_version}",
    ],
    # python_path = "/opt/ee/python/3.10/bin/python3.10",
)

jvm_export(
    name = "io1.assembly.publish",
    target = ":io1.assembly",
    project_name = "IO 1",
    project_description = "IO library",
    project_url = "http://example.com/",
    license = "Apache-2.0",
    scm_url = "http://github.com/",
    release_repo = "https://localhost/",
    snapshot_repo = "https://localhost/",
    # python_path = "/opt/ee/python/3.10/bin/python3.10",
)

scala_library(
    name = "io2",
    srcs = ["IO2.scala"],
    tags = [
        "maven_coordinates=com.twitter.dpb:io2:{pom_version}",
    ],
    deps = [
        ":io1",
    ],
)

jvm_export(
    name = "io2.publish",
    target = ":io2",
    project_name = "IO 2",
    project_description = "IO library",
    project_url = "http://example.com/",
    license = "Apache-2.0",
    scm_url = "http://github.com/",
    release_repo = "https://localhost/",
    snapshot_repo = "https://localhost/",
    # python_path = "/opt/ee/python/3.10/bin/python3.10",
)
