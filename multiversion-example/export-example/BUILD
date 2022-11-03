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
    artifacts = [":io1"],
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
    artifacts = [":io1.assembly"],
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
        "maven_coordinates=com.twitter.dpb:io2:jar:abc:{pom_version}",
    ],
    deps = [
        ":io1",
    ],
)

jvm_export(
    name = "io2.publish",
    artifacts = [":io2"],
    project_name = "IO 2",
    project_description = "IO library",
    project_url = "http://example.com/",
    license = "Apache-2.0",
    scm_url = "http://github.com/",
    release_repo = "https://localhost/",
    snapshot_repo = "https://localhost/",
    # python_path = "/opt/ee/python/3.10/bin/python3.10",
)

scala_library(
    name = "io3",
    srcs = ["IO3.scala"],
    tags = [
        "maven_coordinates=com.twitter.dpb:io3:jar:abc:{pom_version}",
    ],
    deps = [
        ":io2",
    ],
)

jvm_export(
    name = "io3.publish",
    artifacts = [":io3"],
    project_name = "IO 3",
    project_description = "IO library",
    project_url = "http://example.com/",
    license = "Apache-2.0",
    scm_url = "http://github.com/",
    release_repo = "https://localhost/",
    snapshot_repo = "https://localhost/",
    # python_path = "/opt/ee/python/3.10/bin/python3.10",
)

scala_library(
    name = "io4",
    srcs = ["IO4.scala"],
    tags = [
        "maven_coordinates=com.twitter.dpb:io4:{pom_version}",
    ],
    neverlink = True,
)

jvm_export(
    name = "io4.publish",
    artifacts = [":io4"],
    project_name = "IO 4",
    project_description = "IO library",
    project_url = "http://example.com/",
    license = "Apache-2.0",
    scm_url = "http://github.com/",
    release_repo = "https://localhost/",
    snapshot_repo = "https://localhost/",
    # python_path = "/opt/ee/python/3.10/bin/python3.10",
)

scala_library(
    name = "io5",
    srcs = ["IO5.scala"],
    tags = [
        "maven_coordinates=com.twitter.dpb:io5:{pom_version}",
    ],
    deps = [
        ":io4",
    ],
)

jvm_export(
    name = "io5.publish",
    artifacts = [":io5"],
    project_name = "IO 5",
    project_description = "IO library",
    project_url = "http://example.com/",
    license = "Apache-2.0",
    scm_url = "http://github.com/",
    release_repo = "https://localhost/",
    snapshot_repo = "https://localhost/",
    # python_path = "/opt/ee/python/3.10/bin/python3.10",
)
