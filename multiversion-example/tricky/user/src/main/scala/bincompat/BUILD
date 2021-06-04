load("@io_bazel_rules_scala//scala:scala.bzl", "scala_library", "scala_binary")
load("cross_scala_library.bzl", "cross_scala_library")

cross_scala_library(
    name="cross-build",
    srcs=["CrossBuild.scala"],
    deps=["//tricky/{lib}/src/main/scala/bincompat:LibraryTrait"],
    exports=["//tricky/{lib}/src/main/scala/bincompat:LibraryTrait"],
    cross_build=[
        {"lib": "v1"},
        {"lib": "v2"},
    ],
)

scala_binary(
    name="bin-needs-version1",
    srcs=["NeedsVersion1.scala"],
    deps=[":cross-build-lib_v1"],
    main_class="bincompat.NeedsVersion1",
)

scala_binary(
    name="bin-needs-version2",
    srcs=["NeedsVersion2.scala"],
    deps=[":cross-build-lib_v2"],
    main_class="bincompat.NeedsVersion2",
)

scala_binary(
    name="bin-needs-version3",
    srcs=["NeedsVersion3.scala"],
    deps=[":cross-build-lib_v2", ":cross-build-lib_v1"],
    main_class="bincompat.NeedsVersion3",
)

scala_binary(
    name="bin-needs-guava24",
    srcs=["NeedsGuava24.scala"],
    deps=[
        "@maven//:3rdparty/jvm/com/google/guava/guava-24",
    ],
    main_class="bincompat.NeedsGuava24",
)

scala_binary(
    name="bin-needs-guava29",
    srcs=["NeedsGuava29.scala"],
    deps=[
        "@maven//:3rdparty/jvm/com/google/guava/guava",
    ],
    main_class="bincompat.NeedsGuava29",
)

scala_library(
    name="lib-needs-guava-illegal",
    srcs=["NeedsGuavaIllegal.scala"],
    deps=[
        "@maven//:3rdparty/jvm/com/google/guava/guava-24",
        "@maven//:3rdparty/jvm/com/google/guava/guava",
    ],
)

scala_binary(
    name="bin-needs-guava-illegal",
    deps=[":lib-needs-guava-illegal"],
    main_class="bincompat.NeedsGuavaIllegal",
)
