load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary")
load("cross_scala_library.bzl", "cross_scala_library")

cross_scala_library(
  name = "CrossBuild",
  srcs = ["CrossBuild.scala"],
  deps = ["//tricky/{lib}/src/main/scala/bincompat:LibraryTrait"],
  exports = ["//tricky/{lib}/src/main/scala/bincompat:LibraryTrait"],
  cross_build = [
    {"lib": "v1"},
    {"lib": "v2"},
  ],
)

scala_binary(
  name = "NeedsVersion1",
  srcs = ["NeedsVersion1.scala"],
  deps = [":CrossBuild-lib_v1"],
  main_class = "bincompat.NeedsVersion1",
)

scala_binary(
  name = "NeedsVersion2",
  srcs = ["NeedsVersion2.scala"],
  deps = [":CrossBuild-lib_v2"],
  main_class = "bincompat.NeedsVersion2",
)

scala_binary(
  name = "NeedsVersion3",
  srcs = ["NeedsVersion3.scala"],
  deps = [":CrossBuild-lib_v2", ":CrossBuild-lib_v1"],
  main_class = "bincompat.NeedsVersion3",
)

scala_binary(
  name = "NeedsGuava",
  srcs = ["NeedsGuava.scala"],
  deps = ["@maven//:com.google.guava_guava_29.0-jre"],
  main_class = "bincompat.NeedsGuava",
)
