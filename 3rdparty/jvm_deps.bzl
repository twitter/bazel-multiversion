# DO NOT EDIT: this file is auto-generated
def _jvm_deps_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_jvm_deps():
    http_file(
      name = "com.google.guava_guava_29.0-jre",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/29.0-jre/guava-29.0-jre.jar"],
      sha256 = "b22c5fb66d61e7b9522531d04b2f915b5158e80aa0b40ee7282c8bfb07b0da25"
    )
    http_file(
      name = "com.google.guava_failureaccess_1.0.1",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar"],
      sha256 = "a171ee4c734dd2da837e4b16be9df4661afab72a41adaf31eb84dfdaf936ca26"
    )
    http_file(
      name = "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
      urls = [
        "https://repo1.maven.org/maven2/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"
      ],
      sha256 = "b372a037d4230aa57fbeffdef30fd6123f9c0c2db85d0aced00c91b974f33f99"
    )
    http_file(
      name = "com.google.code.findbugs_jsr305_3.0.2",
      urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar"],
      sha256 = "766ad2a0783f2687962c8ad74ceecc38a28b9f72a2d085ee438b7813e928d0c7"
    )
    http_file(
      name = "org.checkerframework_checker-qual_2.11.1",
      urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-qual/2.11.1/checker-qual-2.11.1.jar"],
      sha256 = "015224a4b1dc6de6da053273d4da7d39cfea20e63038169fc45ac0d1dc9c5938"
    )
    http_file(
      name = "com.google.errorprone_error_prone_annotations_2.3.4",
      urls = [
        "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.3.4/error_prone_annotations-2.3.4.jar"
      ],
      sha256 = "baf7d6ea97ce606c53e11b6854ba5f2ce7ef5c24dddf0afa18d1260bd25b002c"
    )
    http_file(
      name = "com.google.j2objc_j2objc-annotations_1.3",
      urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/1.3/j2objc-annotations-1.3.jar"],
      sha256 = "21af30c92267bd6122c0e0b4d20cccb6641a37eaf956c6540ec471d584e64a7b"
    )
    http_file(
      name = "org.eclipse.lsp4j_org.eclipse.lsp4j_0.9.0",
      urls = ["https://repo1.maven.org/maven2/org/eclipse/lsp4j/org.eclipse.lsp4j/0.9.0/org.eclipse.lsp4j-0.9.0.jar"],
      sha256 = "3c73ad81b48be9ca2fa5b569577f6662e460d877381de17e54b959b5eea2451a"
    )
    http_file(
      name = "org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/lsp4j/org.eclipse.lsp4j.generator/0.9.0/org.eclipse.lsp4j.generator-0.9.0.jar"
      ],
      sha256 = "a2bbb4591613d7c87075e9acdf7a654cf91f0ac11c97bc5aa144291f2a0b7841"
    )
    http_file(
      name = "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/lsp4j/org.eclipse.lsp4j.jsonrpc/0.9.0/org.eclipse.lsp4j.jsonrpc-0.9.0.jar"
      ],
      sha256 = "f4c06e8816063c8b64ba370c2e76b289ad77df877f1d1a5b10dc01f1be3b46f2"
    )
    http_file(
      name = "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/xtend/org.eclipse.xtend.lib/2.19.0/org.eclipse.xtend.lib-2.19.0.jar"
      ],
      sha256 = "4594b99205e120d9e6a9225fcb4632fb317e3d28f881ef500ec5702762ec06f8"
    )
    http_file(
      name = "com.google.code.gson_gson_2.8.2",
      urls = ["https://repo1.maven.org/maven2/com/google/code/gson/gson/2.8.2/gson-2.8.2.jar"],
      sha256 = "b7134929f7cc7c04021ec1cc27ef63ab907e410cf0588e397b8851181eb91092"
    )
    http_file(
      name = "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/xtext/org.eclipse.xtext.xbase.lib/2.19.0/org.eclipse.xtext.xbase.lib-2.19.0.jar"
      ],
      sha256 = "d7c6d266717f693e9974b70338fca96cafb58ad63353da9ba7142ccc7b54c513"
    )
    http_file(
      name = "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0",
      urls = [
        "https://repo1.maven.org/maven2/org/eclipse/xtend/org.eclipse.xtend.lib.macro/2.19.0/org.eclipse.xtend.lib.macro-2.19.0.jar"
      ],
      sha256 = "71b79db96e5349f347f9163382a98a0b3b6ee95780240399403107c90703f296"
    )

'''
    ctx.file("jvm_deps.bzl", content, executable = False)
    build_content = '''
load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")

genrule(
  name = "com.google.guava_guava_29.0-jre_extension",
  srcs = ["@com.google.guava_guava_29.0-jre//file"],
  outs = ["@maven//:com.google.guava/guava-29.0-jre.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_guava_29.0-jre",
  jars = ["@maven//:com.google.guava/guava-29.0-jre.jar"],
  deps = [
    "com.google.guava_failureaccess_1.0.1",
    "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.11.1",
    "com.google.errorprone_error_prone_annotations_2.3.4", "com.google.j2objc_j2objc-annotations_1.3"
  ],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.guava_failureaccess_1.0.1_extension",
  srcs = ["@com.google.guava_failureaccess_1.0.1//file"],
  outs = ["@maven//:com.google.guava/failureaccess-1.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_failureaccess_1.0.1",
  jars = ["@maven//:com.google.guava/failureaccess-1.0.1.jar"],
  deps = [],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava_extension",
  srcs = ["@com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava//file"],
  outs = ["@maven//:com.google.guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
  jars = ["@maven//:com.google.guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"],
  deps = [],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.code.findbugs_jsr305_3.0.2_extension",
  srcs = ["@com.google.code.findbugs_jsr305_3.0.2//file"],
  outs = ["@maven//:com.google.code.findbugs/jsr305-3.0.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.code.findbugs_jsr305_3.0.2",
  jars = ["@maven//:com.google.code.findbugs/jsr305-3.0.2.jar"],
  deps = [],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.checkerframework_checker-qual_2.11.1_extension",
  srcs = ["@org.checkerframework_checker-qual_2.11.1//file"],
  outs = ["@maven//:org.checkerframework/checker-qual-2.11.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.checkerframework_checker-qual_2.11.1",
  jars = ["@maven//:org.checkerframework/checker-qual-2.11.1.jar"],
  deps = [],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.errorprone_error_prone_annotations_2.3.4_extension",
  srcs = ["@com.google.errorprone_error_prone_annotations_2.3.4//file"],
  outs = ["@maven//:com.google.errorprone/error_prone_annotations-2.3.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.errorprone_error_prone_annotations_2.3.4",
  jars = ["@maven//:com.google.errorprone/error_prone_annotations-2.3.4.jar"],
  deps = [],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.j2objc_j2objc-annotations_1.3_extension",
  srcs = ["@com.google.j2objc_j2objc-annotations_1.3//file"],
  outs = ["@maven//:com.google.j2objc/j2objc-annotations-1.3.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.j2objc_j2objc-annotations_1.3",
  jars = ["@maven//:com.google.j2objc/j2objc-annotations-1.3.jar"],
  deps = [],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j_0.9.0_extension",
  srcs = ["@org.eclipse.lsp4j_org.eclipse.lsp4j_0.9.0//file"],
  outs = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j-0.9.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j_0.9.0",
  jars = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j-0.9.0.jar"],
  deps = ["org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0", "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0_extension",
  srcs = ["@org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0//file"],
  outs = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j.generator-0.9.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0",
  jars = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j.generator-0.9.0.jar"],
  deps = ["org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0", "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0_extension",
  srcs = ["@org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0//file"],
  outs = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j.jsonrpc-0.9.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0",
  jars = ["@maven//:org.eclipse.lsp4j/org.eclipse.lsp4j.jsonrpc-0.9.0.jar"],
  deps = ["com.google.code.gson_gson_2.8.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0_extension",
  srcs = ["@org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0//file"],
  outs = ["@maven//:org.eclipse.xtend/org.eclipse.xtend.lib-2.19.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0",
  jars = ["@maven//:org.eclipse.xtend/org.eclipse.xtend.lib-2.19.0.jar"],
  deps = [
    "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0", "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0"
  ],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.code.gson_gson_2.8.2_extension",
  srcs = ["@com.google.code.gson_gson_2.8.2//file"],
  outs = ["@maven//:com.google.code.gson/gson-2.8.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.code.gson_gson_2.8.2",
  jars = ["@maven//:com.google.code.gson/gson-2.8.2.jar"],
  deps = [],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0_extension",
  srcs = ["@org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0//file"],
  outs = ["@maven//:org.eclipse.xtext/org.eclipse.xtext.xbase.lib-2.19.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0",
  jars = ["@maven//:org.eclipse.xtext/org.eclipse.xtext.xbase.lib-2.19.0.jar"],
  deps = ["com.google.guava_guava_29.0-jre"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0_extension",
  srcs = ["@org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0//file"],
  outs = ["@maven//:org.eclipse.xtend/org.eclipse.xtend.lib.macro-2.19.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0",
  jars = ["@maven//:org.eclipse.xtend/org.eclipse.xtend.lib.macro-2.19.0.jar"],
  deps = ["org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0"],
  visibility = ["//visibility:public"]
)
'''
    ctx.file("BUILD", build_content, executable = False)

jvm_deps_rule = repository_rule(
    implementation = _jvm_deps_impl,
)
def jvm_deps():
  jvm_deps_rule(name = "maven")
