load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary")

scala_library(
  name = "CrossBuildV1",
  srcs = ["CrossBuild.scala"],
  deps = ["//tricky/lib_v1/src/main/scala/bincompat:LibraryTrait"],
  exports = ["//tricky/lib_v1/src/main/scala/bincompat:LibraryTrait"],
)

scala_library(
  name = "CrossBuildV2",
  srcs = ["CrossBuild.scala"],
  deps = ["//tricky/lib_v2/src/main/scala/bincompat:LibraryTrait"],
  exports = ["//tricky/lib_v2/src/main/scala/bincompat:LibraryTrait"],
)

scala_binary(
  name = "NeedsVersion1",
  srcs = ["NeedsVersion1.scala"],
  deps = [":CrossBuildV1"],
  main_class = "bincompat.NeedsVersion1",
)

scala_binary(
  name = "NeedsVersion2",
  srcs = ["NeedsVersion2.scala"],
  deps = [":CrossBuildV2"],
  main_class = "bincompat.NeedsVersion2",
)
