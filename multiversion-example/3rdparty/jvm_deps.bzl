# DO NOT EDIT: this file is auto-generated
def _jvm_deps_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_jvm_deps():
    http_file(name = "com.google.code.findbugs_jsr305_1.3.9", urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar"], sha256 = "905721a0eea90a81534abb7ee6ef4ea2e5e645fa1def0a5cd88402df1b46c9ed")
    http_file(name = "com.google.code.findbugs_jsr305_3.0.2", urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar"], sha256 = "766ad2a0783f2687962c8ad74ceecc38a28b9f72a2d085ee438b7813e928d0c7")
    http_file(name = "com.google.errorprone_error_prone_annotations_2.3.4", urls = ["https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.3.4/error_prone_annotations-2.3.4.jar"], sha256 = "baf7d6ea97ce606c53e11b6854ba5f2ce7ef5c24dddf0afa18d1260bd25b002c")
    http_file(name = "com.google.guava_failureaccess_1.0.1", urls = ["https://repo1.maven.org/maven2/com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar"], sha256 = "a171ee4c734dd2da837e4b16be9df4661afab72a41adaf31eb84dfdaf936ca26")
    http_file(name = "com.google.guava_guava_24.1.1-jre", urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/24.1.1-jre/guava-24.1.1-jre.jar"], sha256 = "490c16878c7a2c22e136728ad473c4190b21b82b46e261ba84ad2e4a5c28fbcf")
    http_file(name = "com.google.guava_guava_29.0-jre", urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/29.0-jre/guava-29.0-jre.jar"], sha256 = "b22c5fb66d61e7b9522531d04b2f915b5158e80aa0b40ee7282c8bfb07b0da25")
    http_file(name = "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava", urls = ["https://repo1.maven.org/maven2/com/google/guava/listenablefuture/9999.0-empty-to-avoid-conflict-with-guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"], sha256 = "b372a037d4230aa57fbeffdef30fd6123f9c0c2db85d0aced00c91b974f33f99")
    http_file(name = "com.google.j2objc_j2objc-annotations_1.3", urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/1.3/j2objc-annotations-1.3.jar"], sha256 = "21af30c92267bd6122c0e0b4d20cccb6641a37eaf956c6540ec471d584e64a7b")
    http_file(name = "com.google.protobuf_protobuf-java_3.13.0", urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.13.0/protobuf-java-3.13.0.jar"], sha256 = "97d5b2758408690c0dc276238707492a0b6a4d71206311b6c442cdc26c5973ff")
    http_file(name = "com.lihaoyi_fansi_2.12_0.2.5", urls = ["https://repo1.maven.org/maven2/com/lihaoyi/fansi_2.12/0.2.5/fansi_2.12-0.2.5.jar"], sha256 = "7d752240ec724e7370903c25b69088922fa3fb6831365db845cd72498f826eca")
    http_file(name = "com.lihaoyi_fastparse-utils_2.12_1.0.0", urls = ["https://repo1.maven.org/maven2/com/lihaoyi/fastparse-utils_2.12/1.0.0/fastparse-utils_2.12-1.0.0.jar"], sha256 = "fb6cd6484e21459e11fcd45f22f07ad75e3cb29eca0650b39aa388d13c8e7d0a")
    http_file(name = "com.lihaoyi_fastparse_2.12_1.0.0", urls = ["https://repo1.maven.org/maven2/com/lihaoyi/fastparse_2.12/1.0.0/fastparse_2.12-1.0.0.jar"], sha256 = "1227a00a26a4ad76ddcfa6eae2416687df7f3c039553d586324b32ba0a528fcc")
    http_file(name = "com.lihaoyi_pprint_2.12_0.5.3", urls = ["https://repo1.maven.org/maven2/com/lihaoyi/pprint_2.12/0.5.3/pprint_2.12-0.5.3.jar"], sha256 = "2e18aa0884870537bf5c562255fc759d4ebe360882b5cb2141b30eda4034c71d")
    http_file(name = "com.lihaoyi_sourcecode_2.12_0.1.4", urls = ["https://repo1.maven.org/maven2/com/lihaoyi/sourcecode_2.12/0.1.4/sourcecode_2.12-0.1.4.jar"], sha256 = "9a3134484e596205d0acdcccd260e0854346f266cb4d24e1b8a31be54fbaf6d9")
    http_file(name = "com.thesamet.scalapb_lenses_2.12_0.8.0-RC1", urls = ["https://repo1.maven.org/maven2/com/thesamet/scalapb/lenses_2.12/0.8.0-RC1/lenses_2.12-0.8.0-RC1.jar"], sha256 = "6e061e15fa9f37662d89d1d0a3f4da64c73e3129108b672c792b36bf490ae8e2")
    http_file(name = "com.thesamet.scalapb_scalapb-runtime_2.12_0.8.0-RC1", urls = ["https://repo1.maven.org/maven2/com/thesamet/scalapb/scalapb-runtime_2.12/0.8.0-RC1/scalapb-runtime_2.12-0.8.0-RC1.jar"], sha256 = "d922c788c8997e2524a39b1f43bac3c859516fc0ae580eab82c0db7e40aef944")
    http_file(name = "commons-codec_commons-codec_1.9", urls = ["https://repo1.maven.org/maven2/commons-codec/commons-codec/1.9/commons-codec-1.9.jar"], sha256 = "ad19d2601c3abf0b946b5c3a4113e226a8c1e3305e395b90013b78dd94a723ce")
    http_file(name = "commons-logging_commons-logging_1.2", urls = ["https://repo1.maven.org/maven2/commons-logging/commons-logging/1.2/commons-logging-1.2.jar"], sha256 = "daddea1ea0be0f56978ab3006b8ac92834afeefbd9b7e4e6316fca57df0fa636")
    http_file(name = "org.apache.httpcomponents_httpclient_4.4.1", urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.4.1/httpclient-4.4.1.jar"], sha256 = "b2958ffb74f691e108abe69af0002ccff90ba326420596b1aab5bb0f63c31ef9")
    http_file(name = "org.apache.httpcomponents_httpcore_4.4.1", urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore/4.4.1/httpcore-4.4.1.jar"], sha256 = "dd1390c17d40f760f7e51bb20523a8d63deb69e94babeaf567eb76ecd2cad422")
    http_file(name = "org.apache.thrift_libthrift_0.10.0", urls = ["https://repo1.maven.org/maven2/org/apache/thrift/libthrift/0.10.0/libthrift-0.10.0.jar"], sha256 = "8591718c1884ac8001b4c5ca80f349c0a6deec691de0af720c5e3bc3a581dada")
    http_file(name = "org.checkerframework_checker-compat-qual_2.0.0", urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-compat-qual/2.0.0/checker-compat-qual-2.0.0.jar"], sha256 = "a40b2ce6d8551e5b90b1bf637064303f32944d61b52ab2014e38699df573941b")
    http_file(name = "org.checkerframework_checker-qual_2.11.1", urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-qual/2.11.1/checker-qual-2.11.1.jar"], sha256 = "015224a4b1dc6de6da053273d4da7d39cfea20e63038169fc45ac0d1dc9c5938")
    http_file(name = "org.codehaus.mojo_animal-sniffer-annotations_1.14", urls = ["https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.14/animal-sniffer-annotations-1.14.jar"], sha256 = "2068320bd6bad744c3673ab048f67e30bef8f518996fa380033556600669905d")
    http_file(name = "org.scala-lang_scala-library_2.12.12", urls = ["https://repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.12/scala-library-2.12.12.jar"], sha256 = "1673ffe8792021f704caddfe92067ed1ec75229907f84380ad68fe621358c925")
    http_file(name = "org.scalameta_common_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/common_2.12/4.0.0/common_2.12-4.0.0.jar"], sha256 = "57f0cfa2e5c95cbf350cd089cb6799933e6fdc19b177ac8194e0d2f7bd564a7c")
    http_file(name = "org.scalameta_dialects_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/dialects_2.12/4.0.0/dialects_2.12-4.0.0.jar"], sha256 = "d1486a19a438316454ff395bd6f904d2b2becc457706cd762cb50fa6306c5980")
    http_file(name = "org.scalameta_inputs_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/inputs_2.12/4.0.0/inputs_2.12-4.0.0.jar"], sha256 = "0ccc21c1dbc23cc680f704b49d82ee7840d3ee2f0fd790d03ed330fda6897ef8")
    http_file(name = "org.scalameta_io_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/io_2.12/4.0.0/io_2.12-4.0.0.jar"], sha256 = "8dd7baff1123c49370f44ffffeaefd86bc71d3aa919c48fba64e0b16ced8036f")
    http_file(name = "org.scalameta_parsers_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/parsers_2.12/4.0.0/parsers_2.12-4.0.0.jar"], sha256 = "8315d79b922e3978e92d5e948a468e00b0c5e2de6e4381315e3b0425c006ca72")
    http_file(name = "org.scalameta_quasiquotes_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/quasiquotes_2.12/4.0.0/quasiquotes_2.12-4.0.0.jar"], sha256 = "19bd420811488f6e07be39b8dc6ac8d1850476b820e460de90d3759bfba99bba")
    http_file(name = "org.scalameta_scalameta_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/scalameta_2.12/4.0.0/scalameta_2.12-4.0.0.jar"], sha256 = "e59f985f29ef19e5ffb0a26ef6a52cceb7a43968de21b0f2c208f773ac696a1e")
    http_file(name = "org.scalameta_semanticdb_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/semanticdb_2.12/4.0.0/semanticdb_2.12-4.0.0.jar"], sha256 = "77157079fd64a73938401fd9c41f2214ebe25182f98c2c5f6fbb56904f972746")
    http_file(name = "org.scalameta_tokenizers_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/tokenizers_2.12/4.0.0/tokenizers_2.12-4.0.0.jar"], sha256 = "231a4aa5b6c716e0972e23935bcbb7a5e78f5b164a45e1e8bd8fbcdf613839db")
    http_file(name = "org.scalameta_tokens_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/tokens_2.12/4.0.0/tokens_2.12-4.0.0.jar"], sha256 = "e703b9f64e072113ebff82af55962b1bdbed1541d99ffce329e30b682cc0d013")
    http_file(name = "org.scalameta_transversers_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/transversers_2.12/4.0.0/transversers_2.12-4.0.0.jar"], sha256 = "bd3913fe90783459e38cf43a9059498cfc638c18eda633bd02ba3cdda2455611")
    http_file(name = "org.scalameta_trees_2.12_4.0.0", urls = ["https://repo1.maven.org/maven2/org/scalameta/trees_2.12/4.0.0/trees_2.12-4.0.0.jar"], sha256 = "e2573c57f3d582be2ca891a45928dd9e3b07ffdb915058b803f9da1ab6797f92")
    http_file(name = "org.slf4j_slf4j-api_1.7.12", urls = ["https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.12/slf4j-api-1.7.12.jar"], sha256 = "0aee9a77a4940d72932b0d0d9557793f872e66a03f598e473f45e7efecdccf99")

'''
    ctx.file("jvm_deps.bzl", content, executable = False)
    build_content = '''
load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")

genrule(name = "genrules/com.google.code.findbugs_jsr305_1.3.9", srcs = ["@com.google.code.findbugs_jsr305_1.3.9//file"], outs = ["@maven//:com.google.code.findbugs/jsr305-1.3.9.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.code.findbugs_jsr305_1.3.9", jars = ["@maven//:com.google.code.findbugs/jsr305-1.3.9.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.code.findbugs:jsr305", "jvm_version=1.3.9"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.code.findbugs_jsr305_3.0.2", srcs = ["@com.google.code.findbugs_jsr305_3.0.2//file"], outs = ["@maven//:com.google.code.findbugs/jsr305-3.0.2.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.code.findbugs_jsr305_3.0.2", jars = ["@maven//:com.google.code.findbugs/jsr305-3.0.2.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.code.findbugs:jsr305", "jvm_version=3.0.2"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.errorprone_error_prone_annotations_2.3.4", srcs = ["@com.google.errorprone_error_prone_annotations_2.3.4//file"], outs = ["@maven//:com.google.errorprone/error_prone_annotations-2.3.4.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.errorprone_error_prone_annotations_2.3.4", jars = ["@maven//:com.google.errorprone/error_prone_annotations-2.3.4.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.3.4"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.guava_failureaccess_1.0.1", srcs = ["@com.google.guava_failureaccess_1.0.1//file"], outs = ["@maven//:com.google.guava/failureaccess-1.0.1.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.guava_failureaccess_1.0.1", jars = ["@maven//:com.google.guava/failureaccess-1.0.1.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.guava:failureaccess", "jvm_version=1.0.1"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.guava_guava_24.1.1-jre", srcs = ["@com.google.guava_guava_24.1.1-jre//file"], outs = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.guava_guava_24.1.1-jre", jars = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"], deps = ["com.google.code.findbugs_jsr305_1.3.9", "org.checkerframework_checker-compat-qual_2.0.0", "com.google.errorprone_error_prone_annotations_2.3.4", "com.google.j2objc_j2objc-annotations_1.3", "org.codehaus.mojo_animal-sniffer-annotations_1.14"], exports = ["com.google.code.findbugs_jsr305_1.3.9", "org.checkerframework_checker-compat-qual_2.0.0", "com.google.errorprone_error_prone_annotations_2.3.4", "com.google.j2objc_j2objc-annotations_1.3", "org.codehaus.mojo_animal-sniffer-annotations_1.14"], tags = ["jvm_module=com.google.guava:guava", "jvm_version=24.1.1-jre"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.guava_guava_29.0-jre", srcs = ["@com.google.guava_guava_29.0-jre//file"], outs = ["@maven//:com.google.guava/guava-29.0-jre.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.guava_guava_29.0-jre", jars = ["@maven//:com.google.guava/guava-29.0-jre.jar"], deps = ["com.google.guava_failureaccess_1.0.1", "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava", "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.11.1", "com.google.errorprone_error_prone_annotations_2.3.4", "com.google.j2objc_j2objc-annotations_1.3"], exports = ["com.google.guava_failureaccess_1.0.1", "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava", "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.11.1", "com.google.errorprone_error_prone_annotations_2.3.4", "com.google.j2objc_j2objc-annotations_1.3"], tags = ["jvm_module=com.google.guava:guava", "jvm_version=29.0-jre"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava", srcs = ["@com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava//file"], outs = ["@maven//:com.google.guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava", jars = ["@maven//:com.google.guava/listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.guava:listenablefuture", "jvm_version=9999.0-empty-to-avoid-conflict-with-guava"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.j2objc_j2objc-annotations_1.3", srcs = ["@com.google.j2objc_j2objc-annotations_1.3//file"], outs = ["@maven//:com.google.j2objc/j2objc-annotations-1.3.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.j2objc_j2objc-annotations_1.3", jars = ["@maven//:com.google.j2objc/j2objc-annotations-1.3.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.j2objc:j2objc-annotations", "jvm_version=1.3"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.google.protobuf_protobuf-java_3.13.0", srcs = ["@com.google.protobuf_protobuf-java_3.13.0//file"], outs = ["@maven//:com.google.protobuf/protobuf-java-3.13.0.jar"], cmd = "cp $< $@")
scala_import(name = "com.google.protobuf_protobuf-java_3.13.0", jars = ["@maven//:com.google.protobuf/protobuf-java-3.13.0.jar"], deps = [], exports = [], tags = ["jvm_module=com.google.protobuf:protobuf-java", "jvm_version=3.13.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.lihaoyi_fansi_2.12_0.2.5", srcs = ["@com.lihaoyi_fansi_2.12_0.2.5//file"], outs = ["@maven//:com.lihaoyi/fansi_2.12-0.2.5.jar"], cmd = "cp $< $@")
scala_import(name = "com.lihaoyi_fansi_2.12_0.2.5", jars = ["@maven//:com.lihaoyi/fansi_2.12-0.2.5.jar"], deps = [], exports = [], tags = ["jvm_module=com.lihaoyi:fansi_2.12", "jvm_version=0.2.5"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.lihaoyi_fastparse-utils_2.12_1.0.0", srcs = ["@com.lihaoyi_fastparse-utils_2.12_1.0.0//file"], outs = ["@maven//:com.lihaoyi/fastparse-utils_2.12-1.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "com.lihaoyi_fastparse-utils_2.12_1.0.0", jars = ["@maven//:com.lihaoyi/fastparse-utils_2.12-1.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=com.lihaoyi:fastparse-utils_2.12", "jvm_version=1.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.lihaoyi_fastparse_2.12_1.0.0", srcs = ["@com.lihaoyi_fastparse_2.12_1.0.0//file"], outs = ["@maven//:com.lihaoyi/fastparse_2.12-1.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "com.lihaoyi_fastparse_2.12_1.0.0", jars = ["@maven//:com.lihaoyi/fastparse_2.12-1.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=com.lihaoyi:fastparse_2.12", "jvm_version=1.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.lihaoyi_pprint_2.12_0.5.3", srcs = ["@com.lihaoyi_pprint_2.12_0.5.3//file"], outs = ["@maven//:com.lihaoyi/pprint_2.12-0.5.3.jar"], cmd = "cp $< $@")
scala_import(name = "com.lihaoyi_pprint_2.12_0.5.3", jars = ["@maven//:com.lihaoyi/pprint_2.12-0.5.3.jar"], deps = [], exports = [], tags = ["jvm_module=com.lihaoyi:pprint_2.12", "jvm_version=0.5.3"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.lihaoyi_sourcecode_2.12_0.1.4", srcs = ["@com.lihaoyi_sourcecode_2.12_0.1.4//file"], outs = ["@maven//:com.lihaoyi/sourcecode_2.12-0.1.4.jar"], cmd = "cp $< $@")
scala_import(name = "com.lihaoyi_sourcecode_2.12_0.1.4", jars = ["@maven//:com.lihaoyi/sourcecode_2.12-0.1.4.jar"], deps = [], exports = [], tags = ["jvm_module=com.lihaoyi:sourcecode_2.12", "jvm_version=0.1.4"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.thesamet.scalapb_lenses_2.12_0.8.0-RC1", srcs = ["@com.thesamet.scalapb_lenses_2.12_0.8.0-RC1//file"], outs = ["@maven//:com.thesamet.scalapb/lenses_2.12-0.8.0-RC1.jar"], cmd = "cp $< $@")
scala_import(name = "com.thesamet.scalapb_lenses_2.12_0.8.0-RC1", jars = ["@maven//:com.thesamet.scalapb/lenses_2.12-0.8.0-RC1.jar"], deps = [], exports = [], tags = ["jvm_module=com.thesamet.scalapb:lenses_2.12", "jvm_version=0.8.0-RC1"], visibility = ["//visibility:public"])

genrule(name = "genrules/com.thesamet.scalapb_scalapb-runtime_2.12_0.8.0-RC1", srcs = ["@com.thesamet.scalapb_scalapb-runtime_2.12_0.8.0-RC1//file"], outs = ["@maven//:com.thesamet.scalapb/scalapb-runtime_2.12-0.8.0-RC1.jar"], cmd = "cp $< $@")
scala_import(name = "com.thesamet.scalapb_scalapb-runtime_2.12_0.8.0-RC1", jars = ["@maven//:com.thesamet.scalapb/scalapb-runtime_2.12-0.8.0-RC1.jar"], deps = [], exports = [], tags = ["jvm_module=com.thesamet.scalapb:scalapb-runtime_2.12", "jvm_version=0.8.0-RC1"], visibility = ["//visibility:public"])

genrule(name = "genrules/commons-codec_commons-codec_1.9", srcs = ["@commons-codec_commons-codec_1.9//file"], outs = ["@maven//:commons-codec/commons-codec-1.9.jar"], cmd = "cp $< $@")
scala_import(name = "commons-codec_commons-codec_1.9", jars = ["@maven//:commons-codec/commons-codec-1.9.jar"], deps = [], exports = [], tags = ["jvm_module=commons-codec:commons-codec", "jvm_version=1.9"], visibility = ["//visibility:public"])

genrule(name = "genrules/commons-logging_commons-logging_1.2", srcs = ["@commons-logging_commons-logging_1.2//file"], outs = ["@maven//:commons-logging/commons-logging-1.2.jar"], cmd = "cp $< $@")
scala_import(name = "commons-logging_commons-logging_1.2", jars = ["@maven//:commons-logging/commons-logging-1.2.jar"], deps = [], exports = [], tags = ["jvm_module=commons-logging:commons-logging", "jvm_version=1.2"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.apache.httpcomponents_httpclient_4.4.1", srcs = ["@org.apache.httpcomponents_httpclient_4.4.1//file"], outs = ["@maven//:org.apache.httpcomponents/httpclient-4.4.1.jar"], cmd = "cp $< $@")
scala_import(name = "org.apache.httpcomponents_httpclient_4.4.1", jars = ["@maven//:org.apache.httpcomponents/httpclient-4.4.1.jar"], deps = [], exports = [], tags = ["jvm_module=org.apache.httpcomponents:httpclient", "jvm_version=4.4.1"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.apache.httpcomponents_httpcore_4.4.1", srcs = ["@org.apache.httpcomponents_httpcore_4.4.1//file"], outs = ["@maven//:org.apache.httpcomponents/httpcore-4.4.1.jar"], cmd = "cp $< $@")
scala_import(name = "org.apache.httpcomponents_httpcore_4.4.1", jars = ["@maven//:org.apache.httpcomponents/httpcore-4.4.1.jar"], deps = [], exports = [], tags = ["jvm_module=org.apache.httpcomponents:httpcore", "jvm_version=4.4.1"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.apache.thrift_libthrift_0.10.0", srcs = ["@org.apache.thrift_libthrift_0.10.0//file"], outs = ["@maven//:org.apache.thrift/libthrift-0.10.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.apache.thrift_libthrift_0.10.0", jars = ["@maven//:org.apache.thrift/libthrift-0.10.0.jar"], deps = ["org.slf4j_slf4j-api_1.7.12", "org.apache.httpcomponents_httpclient_4.4.1", "org.apache.httpcomponents_httpcore_4.4.1", "commons-logging_commons-logging_1.2", "commons-codec_commons-codec_1.9"], exports = ["org.slf4j_slf4j-api_1.7.12", "org.apache.httpcomponents_httpclient_4.4.1", "org.apache.httpcomponents_httpcore_4.4.1", "commons-logging_commons-logging_1.2", "commons-codec_commons-codec_1.9"], tags = ["jvm_module=org.apache.thrift:libthrift", "jvm_version=0.10.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.checkerframework_checker-compat-qual_2.0.0", srcs = ["@org.checkerframework_checker-compat-qual_2.0.0//file"], outs = ["@maven//:org.checkerframework/checker-compat-qual-2.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.checkerframework_checker-compat-qual_2.0.0", jars = ["@maven//:org.checkerframework/checker-compat-qual-2.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.checkerframework:checker-compat-qual", "jvm_version=2.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.checkerframework_checker-qual_2.11.1", srcs = ["@org.checkerframework_checker-qual_2.11.1//file"], outs = ["@maven//:org.checkerframework/checker-qual-2.11.1.jar"], cmd = "cp $< $@")
scala_import(name = "org.checkerframework_checker-qual_2.11.1", jars = ["@maven//:org.checkerframework/checker-qual-2.11.1.jar"], deps = [], exports = [], tags = ["jvm_module=org.checkerframework:checker-qual", "jvm_version=2.11.1"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.codehaus.mojo_animal-sniffer-annotations_1.14", srcs = ["@org.codehaus.mojo_animal-sniffer-annotations_1.14//file"], outs = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.14.jar"], cmd = "cp $< $@")
scala_import(name = "org.codehaus.mojo_animal-sniffer-annotations_1.14", jars = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.14.jar"], deps = [], exports = [], tags = ["jvm_module=org.codehaus.mojo:animal-sniffer-annotations", "jvm_version=1.14"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scala-lang_scala-library_2.12.12", srcs = ["@org.scala-lang_scala-library_2.12.12//file"], outs = ["@maven//:org.scala-lang/scala-library-2.12.12.jar"], cmd = "cp $< $@")
scala_import(name = "org.scala-lang_scala-library_2.12.12", jars = ["@maven//:org.scala-lang/scala-library-2.12.12.jar"], deps = [], exports = [], tags = ["jvm_module=org.scala-lang:scala-library", "jvm_version=2.12.12"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_common_2.12_4.0.0", srcs = ["@org.scalameta_common_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/common_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_common_2.12_4.0.0", jars = ["@maven//:org.scalameta/common_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:common_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_dialects_2.12_4.0.0", srcs = ["@org.scalameta_dialects_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/dialects_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_dialects_2.12_4.0.0", jars = ["@maven//:org.scalameta/dialects_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:dialects_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_inputs_2.12_4.0.0", srcs = ["@org.scalameta_inputs_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/inputs_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_inputs_2.12_4.0.0", jars = ["@maven//:org.scalameta/inputs_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:inputs_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_io_2.12_4.0.0", srcs = ["@org.scalameta_io_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/io_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_io_2.12_4.0.0", jars = ["@maven//:org.scalameta/io_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:io_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_parsers_2.12_4.0.0", srcs = ["@org.scalameta_parsers_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/parsers_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_parsers_2.12_4.0.0", jars = ["@maven//:org.scalameta/parsers_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:parsers_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_quasiquotes_2.12_4.0.0", srcs = ["@org.scalameta_quasiquotes_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/quasiquotes_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_quasiquotes_2.12_4.0.0", jars = ["@maven//:org.scalameta/quasiquotes_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:quasiquotes_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_scalameta_2.12_4.0.0", srcs = ["@org.scalameta_scalameta_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/scalameta_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_scalameta_2.12_4.0.0", jars = ["@maven//:org.scalameta/scalameta_2.12-4.0.0.jar"], deps = ["org.scala-lang_scala-library_2.12.12", "org.scalameta_common_2.12_4.0.0", "org.scalameta_dialects_2.12_4.0.0", "org.scalameta_parsers_2.12_4.0.0", "org.scalameta_quasiquotes_2.12_4.0.0", "org.scalameta_tokenizers_2.12_4.0.0", "org.scalameta_transversers_2.12_4.0.0", "org.scalameta_trees_2.12_4.0.0", "org.scalameta_inputs_2.12_4.0.0", "org.scalameta_io_2.12_4.0.0", "com.lihaoyi_pprint_2.12_0.5.3", "org.scalameta_semanticdb_2.12_4.0.0", "com.lihaoyi_sourcecode_2.12_0.1.4", "org.scalameta_tokens_2.12_4.0.0", "com.lihaoyi_fastparse_2.12_1.0.0", "com.lihaoyi_fansi_2.12_0.2.5", "com.thesamet.scalapb_scalapb-runtime_2.12_0.8.0-RC1", "com.lihaoyi_fastparse-utils_2.12_1.0.0", "com.thesamet.scalapb_lenses_2.12_0.8.0-RC1", "com.google.protobuf_protobuf-java_3.13.0"], exports = ["org.scala-lang_scala-library_2.12.12", "org.scalameta_common_2.12_4.0.0", "org.scalameta_dialects_2.12_4.0.0", "org.scalameta_parsers_2.12_4.0.0", "org.scalameta_quasiquotes_2.12_4.0.0", "org.scalameta_tokenizers_2.12_4.0.0", "org.scalameta_transversers_2.12_4.0.0", "org.scalameta_trees_2.12_4.0.0", "org.scalameta_inputs_2.12_4.0.0", "org.scalameta_io_2.12_4.0.0", "com.lihaoyi_pprint_2.12_0.5.3", "org.scalameta_semanticdb_2.12_4.0.0", "com.lihaoyi_sourcecode_2.12_0.1.4", "org.scalameta_tokens_2.12_4.0.0", "com.lihaoyi_fastparse_2.12_1.0.0", "com.lihaoyi_fansi_2.12_0.2.5", "com.thesamet.scalapb_scalapb-runtime_2.12_0.8.0-RC1", "com.lihaoyi_fastparse-utils_2.12_1.0.0", "com.thesamet.scalapb_lenses_2.12_0.8.0-RC1", "com.google.protobuf_protobuf-java_3.13.0"], tags = ["jvm_module=org.scalameta:scalameta_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_semanticdb_2.12_4.0.0", srcs = ["@org.scalameta_semanticdb_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/semanticdb_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_semanticdb_2.12_4.0.0", jars = ["@maven//:org.scalameta/semanticdb_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:semanticdb_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_tokenizers_2.12_4.0.0", srcs = ["@org.scalameta_tokenizers_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/tokenizers_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_tokenizers_2.12_4.0.0", jars = ["@maven//:org.scalameta/tokenizers_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:tokenizers_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_tokens_2.12_4.0.0", srcs = ["@org.scalameta_tokens_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/tokens_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_tokens_2.12_4.0.0", jars = ["@maven//:org.scalameta/tokens_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:tokens_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_transversers_2.12_4.0.0", srcs = ["@org.scalameta_transversers_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/transversers_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_transversers_2.12_4.0.0", jars = ["@maven//:org.scalameta/transversers_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:transversers_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.scalameta_trees_2.12_4.0.0", srcs = ["@org.scalameta_trees_2.12_4.0.0//file"], outs = ["@maven//:org.scalameta/trees_2.12-4.0.0.jar"], cmd = "cp $< $@")
scala_import(name = "org.scalameta_trees_2.12_4.0.0", jars = ["@maven//:org.scalameta/trees_2.12-4.0.0.jar"], deps = [], exports = [], tags = ["jvm_module=org.scalameta:trees_2.12", "jvm_version=4.0.0"], visibility = ["//visibility:public"])

genrule(name = "genrules/org.slf4j_slf4j-api_1.7.12", srcs = ["@org.slf4j_slf4j-api_1.7.12//file"], outs = ["@maven//:org.slf4j/slf4j-api-1.7.12.jar"], cmd = "cp $< $@")
scala_import(name = "org.slf4j_slf4j-api_1.7.12", jars = ["@maven//:org.slf4j/slf4j-api-1.7.12.jar"], deps = [], exports = [], tags = ["jvm_module=org.slf4j:slf4j-api", "jvm_version=1.7.12"], visibility = ["//visibility:public"])

scala_import(name = "com.google.errorprone_error_prone_annotations_2.1.3", jars = [], deps = ["com.google.errorprone_error_prone_annotations_2.3.4"], exports = ["com.google.errorprone_error_prone_annotations_2.3.4"], tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.1.3", "evicted=True"], visibility = ["//visibility:public"])

scala_import(name = "com.google.j2objc_j2objc-annotations_1.1", jars = [], deps = ["com.google.j2objc_j2objc-annotations_1.3"], exports = ["com.google.j2objc_j2objc-annotations_1.3"], tags = ["jvm_module=com.google.j2objc:j2objc-annotations", "jvm_version=1.1", "evicted=True"], visibility = ["//visibility:public"])

scala_import(name = "com.google.protobuf_protobuf-java_3.5.1", jars = [], deps = ["com.google.protobuf_protobuf-java_3.13.0"], exports = ["com.google.protobuf_protobuf-java_3.13.0"], tags = ["jvm_module=com.google.protobuf:protobuf-java", "jvm_version=3.5.1", "evicted=True"], visibility = ["//visibility:public"])

scala_import(name = "org.scala-lang_scala-library_2.12.6", jars = [], deps = ["org.scala-lang_scala-library_2.12.12"], exports = ["org.scala-lang_scala-library_2.12.12"], tags = ["jvm_module=org.scala-lang:scala-library", "jvm_version=2.12.6", "evicted=True"], visibility = ["//visibility:public"])
'''
    ctx.file("BUILD", build_content, executable = False)

jvm_deps_rule = repository_rule(
    implementation = _jvm_deps_impl,
)
def jvm_deps():
  jvm_deps_rule(name = "maven")
