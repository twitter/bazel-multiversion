# DO NOT EDIT: this file is auto-generated
def _jvm_deps_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_jvm_deps():
    http_file(name = "com.google.guava_guava_24.1.1-jre", urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/24.1.1-jre/guava-24.1.1-jre.jar"], sha256 = "490c16878c7a2c22e136728ad473c4190b21b82b46e261ba84ad2e4a5c28fbcf")
    http_file(name = "com.google.guava_guava_29.0-jre", urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/29.0-jre/guava-29.0-jre.jar"], sha256 = "b22c5fb66d61e7b9522531d04b2f915b5158e80aa0b40ee7282c8bfb07b0da25")
    http_file(name = "com.google.protobuf_protobuf-java_3.13.0", urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.13.0/protobuf-java-3.13.0.jar"], sha256 = "97d5b2758408690c0dc276238707492a0b6a4d71206311b6c442cdc26c5973ff")
    http_file(name = "org.apache.thrift_libthrift_0.10.0", urls = ["https://repo1.maven.org/maven2/org/apache/thrift/libthrift/0.10.0/libthrift-0.10.0.jar"], sha256 = "8591718c1884ac8001b4c5ca80f349c0a6deec691de0af720c5e3bc3a581dada")
    http_file(name = "org.scala-lang_scala-library_2.12.12", urls = ["https://repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.12/scala-library-2.12.12.jar"], sha256 = "1673ffe8792021f704caddfe92067ed1ec75229907f84380ad68fe621358c925")
    http_file(name = "org.scalameta_scalameta_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/scalameta_2.12/4.0.0/scalameta_2.12-4.0.0.jar"], sha256 = "e59f985f29ef19e5ffb0a26ef6a52cceb7a43968de21b0f2c208f773ac696a1e")

'''
    ctx.file("jvm_deps.bzl", content, executable = False)
    build_content = '''
load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")

genrule(name = "genrules/com.google.guava_guava_24.1.1-jre", srcs = ["@com.google.guava_guava_24.1.1-jre//file"], outs = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.guava_guava_24.1.1-jre", jars = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.guava:guava", "jvm_version=24.1.1-jre"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.guava_guava_29.0-jre", srcs = ["@com.google.guava_guava_29.0-jre//file"], outs = ["@maven//:com.google.guava/guava-29.0-jre.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.guava_guava_29.0-jre", jars = ["@maven//:com.google.guava/guava-29.0-jre.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.guava:guava", "jvm_version=29.0-jre"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.protobuf_protobuf-java_3.13.0", srcs = ["@com.google.protobuf_protobuf-java_3.13.0//file"], outs = ["@maven//:com.google.protobuf/protobuf-java-3.13.0.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.protobuf_protobuf-java_3.13.0", jars = ["@maven//:com.google.protobuf/protobuf-java-3.13.0.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.protobuf:protobuf-java", "jvm_version=3.13.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.apache.thrift_libthrift_0.10.0", srcs = ["@org.apache.thrift_libthrift_0.10.0//file"], outs = ["@maven//:org.apache.thrift/libthrift-0.10.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.apache.thrift_libthrift_0.10.0", jars = ["@maven//:org.apache.thrift/libthrift-0.10.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.apache.thrift:libthrift", "jvm_version=0.10.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scala-lang_scala-library_2.12.12", srcs = ["@org.scala-lang_scala-library_2.12.12//file"], outs = ["@maven//:org.scala-lang/scala-library-2.12.12.jar"], cmd = "cp $< $@")
scala_import(name = "org.scala-lang_scala-library_2.12.12", jars = ["@maven//:org.scala-lang/scala-library-2.12.12.jar"], deps = [], exports = [], tags = ["jvm_module=org.scala-lang:scala-library", "jvm_version=2.12.12"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_scalameta_2.12_4.0.0", srcs = ["@org.scalameta_scalameta_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/scalameta_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_scalameta_2.12_4.0.0", jars = ["@maven//:org.scalameta/scalameta_2.12-4.0.0.jar"], deps = ["org.scala-lang_scala-library_2.12.12", "com.google.protobuf_protobuf-java_3.13.0"], exports = ["org.scala-lang_scala-library_2.12.12", "com.google.protobuf_protobuf-java_3.13.0"], tags = ["jvm_module=org.scalameta:scalameta_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])
'''
    ctx.file("BUILD", build_content, executable = False)

jvm_deps_rule = repository_rule(
    implementation = _jvm_deps_impl,
)
def jvm_deps():
  jvm_deps_rule(name = "maven")
