# DO NOT EDIT: this file is auto-generated
def _jvm_deps_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_jvm_deps():
    http_file(name = "com.google.guava_guava_24.1.1-jre", urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/24.1.1-jre/guava-24.1.1-jre.jar"], sha256 = "490c16878c7a2c22e136728ad473c4190b21b82b46e261ba84ad2e4a5c28fbcf")
    http_file(name = "com.google.code.findbugs_jsr305_1.3.9", urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar"], sha256 = "905721a0eea90a81534abb7ee6ef4ea2e5e645fa1def0a5cd88402df1b46c9ed")
    http_file(name = "org.checkerframework_checker-compat-qual_2.0.0", urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-compat-qual/2.0.0/checker-compat-qual-2.0.0.jar"], sha256 = "a40b2ce6d8551e5b90b1bf637064303f32944d61b52ab2014e38699df573941b")
    http_file(name = "com.google.errorprone_error_prone_annotations_2.1.3", urls = ["https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.1.3/error_prone_annotations-2.1.3.jar"], sha256 = "03d0329547c13da9e17c634d1049ea2ead093925e290567e1a364fd6b1fc7ff8")
    http_file(name = "com.google.j2objc_j2objc-annotations_1.1", urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar"], sha256 = "2994a7eb78f2710bd3d3bfb639b2c94e219cedac0d4d084d516e78c16dddecf6")
    http_file(name = "org.codehaus.mojo_animal-sniffer-annotations_1.14", urls = ["https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.14/animal-sniffer-annotations-1.14.jar"], sha256 = "2068320bd6bad744c3673ab048f67e30bef8f518996fa380033556600669905d")

'''
    ctx.file("jvm_deps.bzl", content, executable = False)
    build_content = '''
load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")

genrule(name = "com.google.guava_guava_24.1.1-jre_extension", srcs = ["@com.google.guava_guava_24.1.1-jre//file"], outs = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.guava_guava_24.1.1-jre", jars = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"], deps = ["com.google.code.findbugs_jsr305_1.3.9", "org.checkerframework_checker-compat-qual_2.0.0", "com.google.errorprone_error_prone_annotations_2.1.3", "com.google.j2objc_j2objc-annotations_1.1", "org.codehaus.mojo_animal-sniffer-annotations_1.14"], exports = ["com.google.code.findbugs_jsr305_1.3.9", "org.checkerframework_checker-compat-qual_2.0.0", "com.google.errorprone_error_prone_annotations_2.1.3", "com.google.j2objc_j2objc-annotations_1.1", "org.codehaus.mojo_animal-sniffer-annotations_1.14"], tags = ["jvm_module=com.google.guava:guava", "jvm_version=24.1.1-jre"], visibility = ["//visibility:public"])

genrule(name = "com.google.code.findbugs_jsr305_1.3.9_extension", srcs = ["@com.google.code.findbugs_jsr305_1.3.9//file"], outs = ["@maven//:com.google.code.findbugs/jsr305-1.3.9.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.code.findbugs_jsr305_1.3.9", jars = ["@maven//:com.google.code.findbugs/jsr305-1.3.9.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.code.findbugs:jsr305", "jvm_version=1.3.9"], visibility = ["//visibility:public"])

genrule(name = "org.checkerframework_checker-compat-qual_2.0.0_extension", srcs = ["@org.checkerframework_checker-compat-qual_2.0.0//file"], outs = ["@maven//:org.checkerframework/checker-compat-qual-2.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.checkerframework_checker-compat-qual_2.0.0", jars = ["@maven//:org.checkerframework/checker-compat-qual-2.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.checkerframework:checker-compat-qual", "jvm_version=2.0.0"], visibility = ["//visibility:public"])

genrule(name = "com.google.errorprone_error_prone_annotations_2.1.3_extension", srcs = ["@com.google.errorprone_error_prone_annotations_2.1.3//file"], outs = ["@maven//:com.google.errorprone/error_prone_annotations-2.1.3.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.errorprone_error_prone_annotations_2.1.3", jars = ["@maven//:com.google.errorprone/error_prone_annotations-2.1.3.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.1.3"], visibility = ["//visibility:public"])

genrule(name = "com.google.j2objc_j2objc-annotations_1.1_extension", srcs = ["@com.google.j2objc_j2objc-annotations_1.1//file"], outs = ["@maven//:com.google.j2objc/j2objc-annotations-1.1.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.j2objc_j2objc-annotations_1.1", jars = ["@maven//:com.google.j2objc/j2objc-annotations-1.1.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.j2objc:j2objc-annotations", "jvm_version=1.1"], visibility = ["//visibility:public"])

genrule(name = "org.codehaus.mojo_animal-sniffer-annotations_1.14_extension", srcs = ["@org.codehaus.mojo_animal-sniffer-annotations_1.14//file"], outs = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.14.jar"], cmd = "cp $< $@")
scala_import(name = "org.codehaus.mojo_animal-sniffer-annotations_1.14", jars = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.14.jar"], deps = [], exports = [], tags = ["jvm_module=org.codehaus.mojo:animal-sniffer-annotations", "jvm_version=1.14"], visibility = ["//visibility:public"])
'''
    ctx.file("BUILD", build_content, executable = False)

jvm_deps_rule = repository_rule(
    implementation = _jvm_deps_impl,
)
def jvm_deps():
  jvm_deps_rule(name = "maven")
