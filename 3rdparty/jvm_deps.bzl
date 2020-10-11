# DO NOT EDIT: this file is auto-generated
def _jvm_deps_impl(ctx):
    content = '''
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_file")

def load_jvm_deps():
    http_file(
      name = "com.google.guava_guava_24.1.1-jre",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/24.1.1-jre/guava-24.1.1-jre.jar"],
      sha256 = "490c16878c7a2c22e136728ad473c4190b21b82b46e261ba84ad2e4a5c28fbcf"
    )
    http_file(
      name = "com.google.code.findbugs_jsr305_1.3.9",
      urls = ["https://repo1.maven.org/maven2/com/google/code/findbugs/jsr305/1.3.9/jsr305-1.3.9.jar"],
      sha256 = "905721a0eea90a81534abb7ee6ef4ea2e5e645fa1def0a5cd88402df1b46c9ed"
    )
    http_file(
      name = "org.checkerframework_checker-compat-qual_2.0.0",
      urls = [
        "https://repo1.maven.org/maven2/org/checkerframework/checker-compat-qual/2.0.0/checker-compat-qual-2.0.0.jar"
      ],
      sha256 = "a40b2ce6d8551e5b90b1bf637064303f32944d61b52ab2014e38699df573941b"
    )
    http_file(
      name = "com.google.errorprone_error_prone_annotations_2.1.3",
      urls = [
        "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.1.3/error_prone_annotations-2.1.3.jar"
      ],
      sha256 = "03d0329547c13da9e17c634d1049ea2ead093925e290567e1a364fd6b1fc7ff8"
    )
    http_file(
      name = "com.google.j2objc_j2objc-annotations_1.1",
      urls = ["https://repo1.maven.org/maven2/com/google/j2objc/j2objc-annotations/1.1/j2objc-annotations-1.1.jar"],
      sha256 = "2994a7eb78f2710bd3d3bfb639b2c94e219cedac0d4d084d516e78c16dddecf6"
    )
    http_file(
      name = "org.codehaus.mojo_animal-sniffer-annotations_1.14",
      urls = [
        "https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.14/animal-sniffer-annotations-1.14.jar"
      ],
      sha256 = "2068320bd6bad744c3673ab048f67e30bef8f518996fa380033556600669905d"
    )
    http_file(
      name = "com.google.guava_guava_27.1-jre",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/27.1-jre/guava-27.1-jre.jar"],
      sha256 = "4a5aa70cc968a4d137e599ad37553e5cfeed2265e8c193476d7119036c536fe7"
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
      name = "org.checkerframework_checker-qual_2.5.2",
      urls = ["https://repo1.maven.org/maven2/org/checkerframework/checker-qual/2.5.2/checker-qual-2.5.2.jar"],
      sha256 = "64b02691c8b9d4e7700f8ee2e742dce7ea2c6e81e662b7522c9ee3bf568c040a"
    )
    http_file(
      name = "com.google.errorprone_error_prone_annotations_2.2.0",
      urls = [
        "https://repo1.maven.org/maven2/com/google/errorprone/error_prone_annotations/2.2.0/error_prone_annotations-2.2.0.jar"
      ],
      sha256 = "6ebd22ca1b9d8ec06d41de8d64e0596981d9607b42035f9ed374f9de271a481a"
    )
    http_file(
      name = "org.codehaus.mojo_animal-sniffer-annotations_1.17",
      urls = [
        "https://repo1.maven.org/maven2/org/codehaus/mojo/animal-sniffer-annotations/1.17/animal-sniffer-annotations-1.17.jar"
      ],
      sha256 = "92654f493ecfec52082e76354f0ebf87648dc3d5cec2e3c3cdb947c016747a53"
    )
    http_file(
      name = "com.google.guava_guava_29.0-jre",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/29.0-jre/guava-29.0-jre.jar"],
      sha256 = "b22c5fb66d61e7b9522531d04b2f915b5158e80aa0b40ee7282c8bfb07b0da25"
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
      name = "com.google.guava_guava_16.0.1",
      urls = ["https://repo1.maven.org/maven2/com/google/guava/guava/16.0.1/guava-16.0.1.jar"],
      sha256 = "a896857d07845d38c7dc5bbc0457b6d9b0f62ecffda010e5e9ec12d561f676d3"
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
    http_file(
      name = "com.google.protobuf_protobuf-java_3.13.0",
      urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/3.13.0/protobuf-java-3.13.0.jar"],
      sha256 = "97d5b2758408690c0dc276238707492a0b6a4d71206311b6c442cdc26c5973ff"
    )
    http_file(
      name = "com.google.protobuf_protobuf-java_2.6.1",
      urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/2.6.1/protobuf-java-2.6.1.jar"],
      sha256 = "55aa554843983f431df5616112cf688d38aa17c132357afd1c109435bfdac4e6"
    )
    http_file(
      name = "com.google.protobuf_protobuf-java_2.5.0",
      urls = ["https://repo1.maven.org/maven2/com/google/protobuf/protobuf-java/2.5.0/protobuf-java-2.5.0.jar"],
      sha256 = "e0c1c64575c005601725e7c6a02cebf9e1285e888f756b2a1d73ffa8d725cc74"
    )
    http_file(
      name = "org.apache.spark_spark-core_2.12_3.0.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/spark/spark-core_2.12/3.0.1/spark-core_2.12-3.0.1.jar"],
      sha256 = "f1dc35c5f3d28674a839cd780c55f326b3ed32ac80f9dd3481558f9f27a5a0b2"
    )
    http_file(
      name = "com.thoughtworks.paranamer_paranamer_2.8",
      urls = ["https://repo1.maven.org/maven2/com/thoughtworks/paranamer/paranamer/2.8/paranamer-2.8.jar"],
      sha256 = "688cb118a6021d819138e855208c956031688be4b47a24bb615becc63acedf07"
    )
    http_file(
      name = "org.apache.avro_avro_1.8.2",
      urls = ["https://repo1.maven.org/maven2/org/apache/avro/avro/1.8.2/avro-1.8.2.jar"],
      sha256 = "f754a0830ce67a5a9fa67a54ec15d103ef15e1c850d7b26faf7b647eeddc82d3"
    )
    http_file(
      name = "org.apache.avro_avro-mapred_1.8.2",
      urls = ["https://repo1.maven.org/maven2/org/apache/avro/avro-mapred/1.8.2/avro-mapred-1.8.2-hadoop2.jar"],
      sha256 = "67bfe847cec4890eb7e150841e99cf362733aea18c03af0486b84b7365126508"
    )
    http_file(
      name = "com.twitter_chill_2.12_0.9.5",
      urls = ["https://repo1.maven.org/maven2/com/twitter/chill_2.12/0.9.5/chill_2.12-0.9.5.jar"],
      sha256 = "c0ab62e002ce1fbf6764ca24720b0bf6192226b8d90e45a609439717a6664d0e"
    )
    http_file(
      name = "com.twitter_chill-java_0.9.5",
      urls = ["https://repo1.maven.org/maven2/com/twitter/chill-java/0.9.5/chill-java-0.9.5.jar"],
      sha256 = "2ae1a66eff7ead2d9926fa5f6be9a24f5646ada191d493594d2640030a24a35b"
    )
    http_file(
      name = "org.apache.xbean_xbean-asm7-shaded_4.15",
      urls = ["https://repo1.maven.org/maven2/org/apache/xbean/xbean-asm7-shaded/4.15/xbean-asm7-shaded-4.15.jar"],
      sha256 = "48894539ba9ad5e3adde68c589c3ea6f3e72cf480cc5ded00447e0a9dc3cbc37"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-client_2.7.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-client/2.7.4/hadoop-client-2.7.4.jar"],
      sha256 = "41835fea974c48c6aa26ee4db3615ef085462159301e187449d3b320aa4b5e5a"
    )
    http_file(
      name = "org.apache.spark_spark-launcher_2.12_3.0.1",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/spark/spark-launcher_2.12/3.0.1/spark-launcher_2.12-3.0.1.jar"
      ],
      sha256 = "c2cf3ec90f2ca971aad4b646594b27f0c7a00b96e95a0ac95aed256882e823bb"
    )
    http_file(
      name = "org.apache.spark_spark-kvstore_2.12_3.0.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/spark/spark-kvstore_2.12/3.0.1/spark-kvstore_2.12-3.0.1.jar"],
      sha256 = "8f67227258499896a2a8c513441668a53992d82061722c216064e429b07fb8be"
    )
    http_file(
      name = "org.apache.spark_spark-network-common_2.12_3.0.1",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/spark/spark-network-common_2.12/3.0.1/spark-network-common_2.12-3.0.1.jar"
      ],
      sha256 = "58b6243980ea264c9b5a63d7cd36be6f943aa1805b3f757e14849a0b37c37355"
    )
    http_file(
      name = "org.apache.spark_spark-network-shuffle_2.12_3.0.1",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/spark/spark-network-shuffle_2.12/3.0.1/spark-network-shuffle_2.12-3.0.1.jar"
      ],
      sha256 = "04a39682ffca5bdaa27a6794a6947f16e7f231b93ffc643c7fa413a4e4f69c39"
    )
    http_file(
      name = "org.apache.spark_spark-unsafe_2.12_3.0.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/spark/spark-unsafe_2.12/3.0.1/spark-unsafe_2.12-3.0.1.jar"],
      sha256 = "c90783631caa146ad531a343690897dec8b989998b6e33c2e901516c62f7a722"
    )
    http_file(
      name = "javax.activation_activation_1.1.1",
      urls = ["https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar"],
      sha256 = "ae475120e9fcd99b4b00b38329bd61cdc5eb754eee03fe66c01f50e137724f99"
    )
    http_file(
      name = "org.apache.curator_curator-recipes_2.7.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/curator/curator-recipes/2.7.1/curator-recipes-2.7.1.jar"],
      sha256 = "ce122f137e36268e30082bf1565c51d874ca926801be3ca73b3c0d522b0dfe2c"
    )
    http_file(
      name = "org.apache.zookeeper_zookeeper_3.4.14",
      urls = ["https://repo1.maven.org/maven2/org/apache/zookeeper/zookeeper/3.4.14/zookeeper-3.4.14.pom"],
      sha256 = "293a16fa3ba43f6ec3fad0bee0a4aaa7a96fa93de9b818f8705f5bd927536c6d"
    )
    http_file(
      name = "javax.servlet_javax.servlet-api_3.1.0",
      urls = ["https://repo1.maven.org/maven2/javax/servlet/javax.servlet-api/3.1.0/javax.servlet-api-3.1.0.jar"],
      sha256 = "af456b2dd41c4e82cf54f3e743bc678973d9fe35bd4d3071fa05c7e5333b8482"
    )
    http_file(
      name = "org.apache.commons_commons-lang3_3.9",
      urls = ["https://repo1.maven.org/maven2/org/apache/commons/commons-lang3/3.9/commons-lang3-3.9.jar"],
      sha256 = "de2e1dcdcf3ef917a8ce858661a06726a9a944f28e33ad7f9e08bea44dc3c230"
    )
    http_file(
      name = "org.apache.commons_commons-math3_3.4.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/commons/commons-math3/3.4.1/commons-math3-3.4.1.jar"],
      sha256 = "d1075b14a71087038b0bfd198f0f7dd8e49b5b3529d8e2eba99e7d9eb8565e4b"
    )
    http_file(
      name = "org.apache.commons_commons-text_1.6",
      urls = ["https://repo1.maven.org/maven2/org/apache/commons/commons-text/1.6/commons-text-1.6.jar"],
      sha256 = "df45e56549b63e0fe716953c9d43cc158f8bf008baf60498e7c17f3faa00a70b"
    )
    http_file(
      name = "org.slf4j_slf4j-api_1.7.30",
      urls = ["https://repo1.maven.org/maven2/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar"],
      sha256 = "cdba07964d1bb40a0761485c6b1e8c2f8fd9eb1d19c53928ac0d7f9510105c57"
    )
    http_file(
      name = "org.slf4j_jul-to-slf4j_1.7.30",
      urls = ["https://repo1.maven.org/maven2/org/slf4j/jul-to-slf4j/1.7.30/jul-to-slf4j-1.7.30.jar"],
      sha256 = "bbcbfdaa72572255c4f85207a9bfdb24358dc993e41252331bd4d0913e4988b9"
    )
    http_file(
      name = "org.slf4j_jcl-over-slf4j_1.7.30",
      urls = ["https://repo1.maven.org/maven2/org/slf4j/jcl-over-slf4j/1.7.30/jcl-over-slf4j-1.7.30.jar"],
      sha256 = "71e9ee37b9e4eb7802a2acc5f41728a4cf3915e7483d798db3b4ff2ec8847c50"
    )
    http_file(
      name = "log4j_log4j_1.2.17",
      urls = ["https://repo1.maven.org/maven2/log4j/log4j/1.2.17/log4j-1.2.17.jar"],
      sha256 = "1d31696445697720527091754369082a6651bd49781b6005deb94e56753406f9"
    )
    http_file(
      name = "org.slf4j_slf4j-log4j12_1.7.30",
      urls = ["https://repo1.maven.org/maven2/org/slf4j/slf4j-log4j12/1.7.30/slf4j-log4j12-1.7.30.jar"],
      sha256 = "4d41e01c40caf8a6c74add2b073055d8a4ce1c30e58154177b13f12d78abbe7b"
    )
    http_file(
      name = "com.ning_compress-lzf_1.0.3",
      urls = ["https://repo1.maven.org/maven2/com/ning/compress-lzf/1.0.3/compress-lzf-1.0.3.jar"],
      sha256 = "6cf93bda1c2caf618652f97d2f36c883a5a9774345384c05d3593b173731bccd"
    )
    http_file(
      name = "org.xerial.snappy_snappy-java_1.1.7.5",
      urls = ["https://repo1.maven.org/maven2/org/xerial/snappy/snappy-java/1.1.7.5/snappy-java-1.1.7.5.jar"],
      sha256 = "5be9642ebb9851b8ce6a272bace492b5c1da2fac53605f172aafc39a33df3862"
    )
    http_file(
      name = "org.lz4_lz4-java_1.7.1",
      urls = ["https://repo1.maven.org/maven2/org/lz4/lz4-java/1.7.1/lz4-java-1.7.1.jar"],
      sha256 = "f1167a45d4b8002053670ef6991ca66d1bab9dcc03e4ef00183674d2f3fb9cac"
    )
    http_file(
      name = "com.github.luben_zstd-jni_1.4.4-3",
      urls = ["https://repo1.maven.org/maven2/com/github/luben/zstd-jni/1.4.4-3/zstd-jni-1.4.4-3.jar"],
      sha256 = "bb7569eaef22592e8573fe8e5f28943d4ddfca0ff83140c6db6dc032206966c6"
    )
    http_file(
      name = "org.roaringbitmap_RoaringBitmap_0.7.45",
      urls = ["https://repo1.maven.org/maven2/org/roaringbitmap/RoaringBitmap/0.7.45/RoaringBitmap-0.7.45.jar"],
      sha256 = "5db5c2bb8e5cd5368bd0784f427a55666507d7158c316afef4e1346b7246177e"
    )
    http_file(
      name = "commons-net_commons-net_3.1",
      urls = ["https://repo1.maven.org/maven2/commons-net/commons-net/3.1/commons-net-3.1.jar"],
      sha256 = "34a58d6d80a50748307e674ec27b4411e6536fd12e78bec428eb2ee49a123007"
    )
    http_file(
      name = "org.scala-lang.modules_scala-xml_2.12_1.2.0",
      urls = ["https://repo1.maven.org/maven2/org/scala-lang/modules/scala-xml_2.12/1.2.0/scala-xml_2.12-1.2.0.jar"],
      sha256 = "1b48dc206f527b7604ef32492ada8e71706c63a65d999e0cabdafdc5793b4d63"
    )
    http_file(
      name = "org.scala-lang_scala-library_2.12.10",
      urls = ["https://repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.10/scala-library-2.12.10.jar"],
      sha256 = "0a57044d10895f8d3dd66ad4286891f607169d948845ac51e17b4c1cf0ab569d"
    )
    http_file(
      name = "org.scala-lang_scala-reflect_2.12.10",
      urls = ["https://repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.12.10/scala-reflect-2.12.10.jar"],
      sha256 = "56b609e1bab9144fb51525bfa01ccd72028154fc40a58685a1e9adcbe7835730"
    )
    http_file(
      name = "org.json4s_json4s-jackson_2.12_3.6.6",
      urls = ["https://repo1.maven.org/maven2/org/json4s/json4s-jackson_2.12/3.6.6/json4s-jackson_2.12-3.6.6.jar"],
      sha256 = "32679d19d17d9ddd234ed7a474f2f37a8bf8e455e90dc63d45dcd37311aab06f"
    )
    http_file(
      name = "org.glassfish.jersey.core_jersey-client_2.30",
      urls = ["https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-client/2.30/jersey-client-2.30.jar"],
      sha256 = "d79d34352474ae3df2004e17741d111edfc6a87785a1720a744a30f70d641557"
    )
    http_file(
      name = "org.glassfish.jersey.core_jersey-common_2.30",
      urls = ["https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-common/2.30/jersey-common-2.30.jar"],
      sha256 = "273cbee4d1997907a758aaaf1bd61b50de9bcaf2eb9e0aa4753c7828de111a1b"
    )
    http_file(
      name = "org.glassfish.jersey.core_jersey-server_2.30",
      urls = ["https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-server/2.30/jersey-server-2.30.jar"],
      sha256 = "c781ea17ff158d5aa1cf4fcbc81ea37ffa2df5c73acfd23409104f87352f7860"
    )
    http_file(
      name = "org.glassfish.jersey.containers_jersey-container-servlet_2.30",
      urls = [
        "https://repo1.maven.org/maven2/org/glassfish/jersey/containers/jersey-container-servlet/2.30/jersey-container-servlet-2.30.jar"
      ],
      sha256 = "7da2bf3b978285baf5f2875adc822dcc5382dcbe5cc66d50bc345b3b6ee1af25"
    )
    http_file(
      name = "org.glassfish.jersey.containers_jersey-container-servlet-core_2.30",
      urls = [
        "https://repo1.maven.org/maven2/org/glassfish/jersey/containers/jersey-container-servlet-core/2.30/jersey-container-servlet-core-2.30.jar"
      ],
      sha256 = "6f38c5b4865b79d00102d24b0c8ff8ba4cd4f15c82c220894e81af1cbdb10b40"
    )
    http_file(
      name = "org.glassfish.jersey.inject_jersey-hk2_2.30",
      urls = ["https://repo1.maven.org/maven2/org/glassfish/jersey/inject/jersey-hk2/2.30/jersey-hk2-2.30.jar"],
      sha256 = "6b144c5e8d5a7fd3d46f217be57655b3035e49b66b6c3642bf5dd78c2f09d8d2"
    )
    http_file(
      name = "io.netty_netty-all_4.1.47.Final",
      urls = ["https://repo1.maven.org/maven2/io/netty/netty-all/4.1.47.Final/netty-all-4.1.47.Final.jar"],
      sha256 = "ab96faf902c8a24057a00c75d43643b995cd48fac2811d37c0129d0d21d5c014"
    )
    http_file(
      name = "com.clearspring.analytics_stream_2.9.6",
      urls = ["https://repo1.maven.org/maven2/com/clearspring/analytics/stream/2.9.6/stream-2.9.6.jar"],
      sha256 = "d61aebbea8a08148c3aca6b03464495a4bbf9d362205d54ea5f6b443af73afdf"
    )
    http_file(
      name = "io.dropwizard.metrics_metrics-core_4.1.1",
      urls = ["https://repo1.maven.org/maven2/io/dropwizard/metrics/metrics-core/4.1.1/metrics-core-4.1.1.jar"],
      sha256 = "7dedc4d3e17e78de4e2377890f69c884d004c42aff6d9b9543e9284c6646662b"
    )
    http_file(
      name = "io.dropwizard.metrics_metrics-jvm_4.1.1",
      urls = ["https://repo1.maven.org/maven2/io/dropwizard/metrics/metrics-jvm/4.1.1/metrics-jvm-4.1.1.jar"],
      sha256 = "52c80ff14e043c9ef38a28a8f76c07348993c488d39f476c24e9ab610730627b"
    )
    http_file(
      name = "io.dropwizard.metrics_metrics-json_4.1.1",
      urls = ["https://repo1.maven.org/maven2/io/dropwizard/metrics/metrics-json/4.1.1/metrics-json-4.1.1.jar"],
      sha256 = "85f917ac53692b5a7d1b757acc85a141ad9808286d4e63bb991f0181cec3a958"
    )
    http_file(
      name = "io.dropwizard.metrics_metrics-graphite_4.1.1",
      urls = ["https://repo1.maven.org/maven2/io/dropwizard/metrics/metrics-graphite/4.1.1/metrics-graphite-4.1.1.jar"],
      sha256 = "718f12e53e9b039498f85d68de0f12fb7537f62fbbd611091bc40d47f0fedc86"
    )
    http_file(
      name = "io.dropwizard.metrics_metrics-jmx_4.1.1",
      urls = ["https://repo1.maven.org/maven2/io/dropwizard/metrics/metrics-jmx/4.1.1/metrics-jmx-4.1.1.jar"],
      sha256 = "d05927471b69e988ea43fd8140b181cdbbb21e1f378d39ce232932c67fb6972c"
    )
    http_file(
      name = "com.fasterxml.jackson.core_jackson-databind_2.10.0",
      urls = [
        "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.10.0/jackson-databind-2.10.0.jar"
      ],
      sha256 = "8e6c566c67fc61a96c5dfc4a71d430f2565765778ec9a6ef216c5460a9911b60"
    )
    http_file(
      name = "com.fasterxml.jackson.module_jackson-module-scala_2.12_2.10.0",
      urls = [
        "https://repo1.maven.org/maven2/com/fasterxml/jackson/module/jackson-module-scala_2.12/2.10.0/jackson-module-scala_2.12-2.10.0.jar"
      ],
      sha256 = "81fca89ded8e791bdfe41e68cb868d360983a8ee494556ff47d7bf0afdfe22e4"
    )
    http_file(
      name = "org.apache.ivy_ivy_2.4.0",
      urls = ["https://repo1.maven.org/maven2/org/apache/ivy/ivy/2.4.0/ivy-2.4.0.jar"],
      sha256 = "ce81cb234406b093b5b8de9f6f5b2a50ed0824d6a235891353e8d3e941a53970"
    )
    http_file(
      name = "oro_oro_2.0.8",
      urls = ["https://repo1.maven.org/maven2/oro/oro/2.0.8/oro-2.0.8.jar"],
      sha256 = "e00ccdad5df7eb43fdee44232ef64602bf63807c2d133a7be83ba09fd49af26e"
    )
    http_file(
      name = "net.razorvine_pyrolite_4.30",
      urls = ["https://repo1.maven.org/maven2/net/razorvine/pyrolite/4.30/pyrolite-4.30.jar"],
      sha256 = "76c8108b0741f1816694d53a250dd69060094905c02739297f218046c0bf67c9"
    )
    http_file(
      name = "net.sf.py4j_py4j_0.10.9",
      urls = ["https://repo1.maven.org/maven2/net/sf/py4j/py4j/0.10.9/py4j-0.10.9.jar"],
      sha256 = "ffe9442ef059483d9cdcd9fb5e836caf3491a82e366cb94c880daa180d737ce6"
    )
    http_file(
      name = "org.apache.spark_spark-tags_2.12_3.0.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/spark/spark-tags_2.12/3.0.1/spark-tags_2.12-3.0.1.jar"],
      sha256 = "2f0318cad79d8d47467d750b2632c894e0a59667ed6ed7ce3181ab18d9a7b428"
    )
    http_file(
      name = "org.apache.commons_commons-crypto_1.0.0",
      urls = ["https://repo1.maven.org/maven2/org/apache/commons/commons-crypto/1.0.0/commons-crypto-1.0.0.jar"],
      sha256 = "0043d8d74d8df632c57f938828e6f6efd555e293a9079dcdf59eab8e40107491"
    )
    http_file(
      name = "org.spark-project.spark_unused_1.0.0",
      urls = ["https://repo1.maven.org/maven2/org/spark-project/spark/unused/1.0.0/unused-1.0.0.jar"],
      sha256 = "00fd27fc9bde701581e7dcf5b95981d9e749a1c176bb8bfcd49f675768ff6bf0"
    )
    http_file(
      name = "org.codehaus.jackson_jackson-core-asl_1.9.13",
      urls = [
        "https://repo1.maven.org/maven2/org/codehaus/jackson/jackson-core-asl/1.9.13/jackson-core-asl-1.9.13.jar"
      ],
      sha256 = "440a9cb5ca95b215f953d3a20a6b1a10da1f09b529a9ddea5f8a4905ddab4f5a"
    )
    http_file(
      name = "org.codehaus.jackson_jackson-mapper-asl_1.9.13",
      urls = [
        "https://repo1.maven.org/maven2/org/codehaus/jackson/jackson-mapper-asl/1.9.13/jackson-mapper-asl-1.9.13.jar"
      ],
      sha256 = "74e7a07a76f2edbade29312a5a2ebccfa019128bc021ece3856d76197e9be0c2"
    )
    http_file(
      name = "org.apache.commons_commons-compress_1.8.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/commons/commons-compress/1.8.1/commons-compress-1.8.1.jar"],
      sha256 = "5fca136503f86ecc6cb61fbd17b137d59e56b45c7a5494e6b8fd3cabd4697fbd"
    )
    http_file(
      name = "org.tukaani_xz_1.5",
      urls = ["https://repo1.maven.org/maven2/org/tukaani/xz/1.5/xz-1.5.jar"],
      sha256 = "86f30fa8775fa3a62cdb39d1ed78a6019164c1058864048d42cbee244e26e840"
    )
    http_file(
      name = "org.apache.avro_avro-ipc_1.8.2",
      urls = ["https://repo1.maven.org/maven2/org/apache/avro/avro-ipc/1.8.2/avro-ipc-1.8.2.jar"],
      sha256 = "cef78adb0908473e3b589b95be56dad6923f2ce52d484d32f2dd306c2279d5f1"
    )
    http_file(
      name = "commons-codec_commons-codec_1.9",
      urls = ["https://repo1.maven.org/maven2/commons-codec/commons-codec/1.9/commons-codec-1.9.jar"],
      sha256 = "ad19d2601c3abf0b946b5c3a4113e226a8c1e3305e395b90013b78dd94a723ce"
    )
    http_file(
      name = "com.esotericsoftware_kryo-shaded_4.0.2",
      urls = ["https://repo1.maven.org/maven2/com/esotericsoftware/kryo-shaded/4.0.2/kryo-shaded-4.0.2.jar"],
      sha256 = "a4899f57fef456b9ec66f730e7b493ecb3dc494cc5758721ed9c18416fd2d3b6"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-common_2.7.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-common/2.7.4/hadoop-common-2.7.4.jar"],
      sha256 = "9c5cd1d114cba1547ab17b7e8dc8bb5759b35448c354e0589398e27fb7f4a978"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-hdfs_2.7.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-hdfs/2.7.4/hadoop-hdfs-2.7.4.jar"],
      sha256 = "1f3c14c446cf1692b085952b5e186ee817d7aa3011440a38da86140fe1e3d815"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-mapreduce-client-app_2.7.4",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-mapreduce-client-app/2.7.4/hadoop-mapreduce-client-app-2.7.4.jar"
      ],
      sha256 = "b2c226a746a8f65e45541c1bbe3c4e299ed050819d6e8b058d694eeee3f52220"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-yarn-api_2.7.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-yarn-api/2.7.4/hadoop-yarn-api-2.7.4.jar"],
      sha256 = "e6c52b8681a16e9fca678cafc12c217fc97c3e6b3903b1b747549f491c8b1beb"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-mapreduce-client-core_2.7.4",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-mapreduce-client-core/2.7.4/hadoop-mapreduce-client-core-2.7.4.jar"
      ],
      sha256 = "0594de6f97ea0105fd977ab4e86398199a514141380ca254999d49210e125cf0"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-mapreduce-client-jobclient_2.7.4",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-mapreduce-client-jobclient/2.7.4/hadoop-mapreduce-client-jobclient-2.7.4.jar"
      ],
      sha256 = "043f72cef74f6466d4348f94c404a4a6946a98ad5da652a6db548035f12f7717"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-annotations_2.7.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-annotations/2.7.4/hadoop-annotations-2.7.4.jar"],
      sha256 = "195e4f2444d8f3245d4c6e4ba89fa7fe4a2a4c793f7c55d6edee27d430ec6a03"
    )
    http_file(
      name = "org.fusesource.leveldbjni_leveldbjni-all_1.8",
      urls = ["https://repo1.maven.org/maven2/org/fusesource/leveldbjni/leveldbjni-all/1.8/leveldbjni-all-1.8.jar"],
      sha256 = "c297213b0e6f9392305952753f3099a4c02e70b3656266fe01867e7b6c160ffe"
    )
    http_file(
      name = "com.fasterxml.jackson.core_jackson-core_2.10.0",
      urls = ["https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.10.0/jackson-core-2.10.0.jar"],
      sha256 = "69e7695b1e40834fa1242fc328a4010607911ced3706ab79abc769d451197513"
    )
    http_file(
      name = "com.fasterxml.jackson.core_jackson-annotations_2.10.0",
      urls = [
        "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.10.0/jackson-annotations-2.10.0.jar"
      ],
      sha256 = "77a0846219774a8269bc1b38cc00294908d34e77f8a9da34d97673982f75853d"
    )
    http_file(
      name = "org.apache.curator_curator-framework_2.7.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/curator/curator-framework/2.7.1/curator-framework-2.7.1.jar"],
      sha256 = "a65e3f515b022d84d86c553c99216e384bc82d1de51b5a32b10f33314ad81ceb"
    )
    http_file(
      name = "org.apache.yetus_audience-annotations_0.5.0",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/yetus/audience-annotations/0.5.0/audience-annotations-0.5.0.jar"
      ],
      sha256 = "c82631f06c75d46bf6524d95f0d6c2e3aef1b3eb4a7b584ca296624ef0d474be"
    )
    http_file(
      name = "org.roaringbitmap_shims_0.7.45",
      urls = ["https://repo1.maven.org/maven2/org/roaringbitmap/shims/0.7.45/shims-0.7.45.jar"],
      sha256 = "8eab000cdb5d24a51007a853734f361469327ed7bade44ba8180fd3e4fb0fd5d"
    )
    http_file(
      name = "org.json4s_json4s-core_2.12_3.6.6",
      urls = ["https://repo1.maven.org/maven2/org/json4s/json4s-core_2.12/3.6.6/json4s-core_2.12-3.6.6.jar"],
      sha256 = "dc25523493b3fa9b1f300cc0d26978e2b2443721e280df581b14524c4b61332c"
    )
    http_file(
      name = "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6",
      urls = ["https://repo1.maven.org/maven2/jakarta/ws/rs/jakarta.ws.rs-api/2.1.6/jakarta.ws.rs-api-2.1.6.jar"],
      sha256 = "4cea299c846c8a6e6470cbfc2f7c391bc29b9caa2f9264ac1064ba91691f4adf"
    )
    http_file(
      name = "org.glassfish.hk2.external_jakarta.inject_2.6.1",
      urls = [
        "https://repo1.maven.org/maven2/org/glassfish/hk2/external/jakarta.inject/2.6.1/jakarta.inject-2.6.1.jar"
      ],
      sha256 = "5e88c123b3e41bca788b2683118867d9b6dec714247ea91c588aed46a36ee24f"
    )
    http_file(
      name = "jakarta.annotation_jakarta.annotation-api_1.3.5",
      urls = [
        "https://repo1.maven.org/maven2/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar"
      ],
      sha256 = "85fb03fc054cdf4efca8efd9b6712bbb418e1ab98241c4539c8585bbc23e1b8a"
    )
    http_file(
      name = "org.glassfish.hk2_osgi-resource-locator_1.0.3",
      urls = [
        "https://repo1.maven.org/maven2/org/glassfish/hk2/osgi-resource-locator/1.0.3/osgi-resource-locator-1.0.3.jar"
      ],
      sha256 = "aab5d7849f7cfcda2cc7c541ba1bd365151d42276f151c825387245dfde3dd74"
    )
    http_file(
      name = "org.glassfish.jersey.media_jersey-media-jaxb_2.30",
      urls = [
        "https://repo1.maven.org/maven2/org/glassfish/jersey/media/jersey-media-jaxb/2.30/jersey-media-jaxb-2.30.jar"
      ],
      sha256 = "1e9a233ea6b6093cc8ee54a4809578c8c948c08e52d056567b0b140ed8b80157"
    )
    http_file(
      name = "jakarta.validation_jakarta.validation-api_2.0.2",
      urls = [
        "https://repo1.maven.org/maven2/jakarta/validation/jakarta.validation-api/2.0.2/jakarta.validation-api-2.0.2.jar"
      ],
      sha256 = "b42d42428f3d922c892a909fa043287d577c0c5b165ad9b7d568cebf87fc9ea4"
    )
    http_file(
      name = "org.glassfish.hk2_hk2-locator_2.6.1",
      urls = ["https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-locator/2.6.1/hk2-locator-2.6.1.jar"],
      sha256 = "febc668deb9f2000c76bd4918d8086c0a4c74d07bd0c60486b72c6bd38b62874"
    )
    http_file(
      name = "org.javassist_javassist_3.25.0-GA",
      urls = ["https://repo1.maven.org/maven2/org/javassist/javassist/3.25.0-GA/javassist-3.25.0-GA.jar"],
      sha256 = "5d49abd02997134f80041645e9668e1ff97afd69d2c2c55ae9fbd40dc073f97b"
    )
    http_file(
      name = "com.fasterxml.jackson.module_jackson-module-paranamer_2.10.0",
      urls = [
        "https://repo1.maven.org/maven2/com/fasterxml/jackson/module/jackson-module-paranamer/2.10.0/jackson-module-paranamer-2.10.0.jar"
      ],
      sha256 = "dc9eee4074dd07ee3b1659bd1ace067cc67a0329ddee426b8499ec40226d881f"
    )
    http_file(
      name = "com.esotericsoftware_minlog_1.3.0",
      urls = ["https://repo1.maven.org/maven2/com/esotericsoftware/minlog/1.3.0/minlog-1.3.0.jar"],
      sha256 = "f7b399d3a5478a4f3e0d98bd1c9f47766119c66414bc33aa0f6cde0066f24cc2"
    )
    http_file(
      name = "org.objenesis_objenesis_2.5.1",
      urls = ["https://repo1.maven.org/maven2/org/objenesis/objenesis/2.5.1/objenesis-2.5.1.jar"],
      sha256 = "b043f03e466752f7f03e2326a3b13a49b7c649f8f2a2dc87715827e24f73d9c6"
    )
    http_file(
      name = "commons-cli_commons-cli_1.2",
      urls = ["https://repo1.maven.org/maven2/commons-cli/commons-cli/1.2/commons-cli-1.2.jar"],
      sha256 = "e7cd8951956d349b568b7ccfd4f5b2529a8c113e67c32b028f52ffda371259d9"
    )
    http_file(
      name = "xmlenc_xmlenc_0.52",
      urls = ["https://repo1.maven.org/maven2/xmlenc/xmlenc/0.52/xmlenc-0.52.jar"],
      sha256 = "282ae185fc2ff27da7714af9962897c09cfefafb88072219c4a2f9c73616c026"
    )
    http_file(
      name = "commons-httpclient_commons-httpclient_3.1",
      urls = ["https://repo1.maven.org/maven2/commons-httpclient/commons-httpclient/3.1/commons-httpclient-3.1.jar"],
      sha256 = "dbd4953d013e10e7c1cc3701a3e6ccd8c950c892f08d804fabfac21705930443"
    )
    http_file(
      name = "commons-io_commons-io_2.4",
      urls = ["https://repo1.maven.org/maven2/commons-io/commons-io/2.4/commons-io-2.4.jar"],
      sha256 = "cc6a41dc3eaacc9e440a6bd0d2890b20d36b4ee408fe2d67122f328bb6e01581"
    )
    http_file(
      name = "commons-collections_commons-collections_3.2.2",
      urls = [
        "https://repo1.maven.org/maven2/commons-collections/commons-collections/3.2.2/commons-collections-3.2.2.jar"
      ],
      sha256 = "eeeae917917144a68a741d4c0dff66aa5c5c5fd85593ff217bced3fc8ca783b8"
    )
    http_file(
      name = "org.mortbay.jetty_jetty-sslengine_6.1.26",
      urls = ["https://repo1.maven.org/maven2/org/mortbay/jetty/jetty-sslengine/6.1.26/jetty-sslengine-6.1.26.jar"],
      sha256 = "9c5f6bb168ba01b95d250b57f061c8094e1ce9c89ae4e773492bacb17192ea87"
    )
    http_file(
      name = "javax.servlet.jsp_jsp-api_2.1",
      urls = ["https://repo1.maven.org/maven2/javax/servlet/jsp/jsp-api/2.1/jsp-api-2.1.jar"],
      sha256 = "545f4e7dc678ffb4cf8bd0fd40b4a4470a409a787c0ea7d0ad2f08d56112987b"
    )
    http_file(
      name = "commons-lang_commons-lang_2.6",
      urls = ["https://repo1.maven.org/maven2/commons-lang/commons-lang/2.6/commons-lang-2.6.jar"],
      sha256 = "50f11b09f877c294d56f24463f47d28f929cf5044f648661c0f0cfbae9a2f49c"
    )
    http_file(
      name = "commons-configuration_commons-configuration_1.6",
      urls = [
        "https://repo1.maven.org/maven2/commons-configuration/commons-configuration/1.6/commons-configuration-1.6.jar"
      ],
      sha256 = "46b71b9656154f6a16ea4b1dc84026b52a9305f8eff046a2b4655fa1738e5eee"
    )
    http_file(
      name = "com.google.code.gson_gson_2.2.4",
      urls = ["https://repo1.maven.org/maven2/com/google/code/gson/gson/2.2.4/gson-2.2.4.jar"],
      sha256 = "c0328cd07ca9e363a5acd00c1cf4afe8cf554bd6d373834981ba05cebec687fb"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-auth_2.7.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-auth/2.7.4/hadoop-auth-2.7.4.jar"],
      sha256 = "fc925a97e34de51527912cced4fef3397dc8987d027e9ffedefeaf8b5056a239"
    )
    http_file(
      name = "org.apache.curator_curator-client_2.7.1",
      urls = ["https://repo1.maven.org/maven2/org/apache/curator/curator-client/2.7.1/curator-client-2.7.1.jar"],
      sha256 = "949ac95323bb13b4d9cde33ab1ca73f07a87e6e43cf76629e89fdd74d5b378e4"
    )
    http_file(
      name = "org.apache.htrace_htrace-core_3.1.0-incubating",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/htrace/htrace-core/3.1.0-incubating/htrace-core-3.1.0-incubating.jar"
      ],
      sha256 = "d96c869afaf65315ece8ca09673b187557e9dbaad31df24467a5aa759812188d"
    )
    http_file(
      name = "org.mortbay.jetty_jetty-util_6.1.26",
      urls = ["https://repo1.maven.org/maven2/org/mortbay/jetty/jetty-util/6.1.26/jetty-util-6.1.26.jar"],
      sha256 = "9b974ce2b99f48254b76126337dc45b21226f383aaed616f59780adaf167c047"
    )
    http_file(
      name = "xerces_xercesImpl_2.9.1",
      urls = ["https://repo1.maven.org/maven2/xerces/xercesImpl/2.9.1/xercesImpl-2.9.1.jar"],
      sha256 = "6ae540a7c85c814ac64bea48016b3a6f45c95d4765f547fcc0053dc36c94ed5c"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-mapreduce-client-common/2.7.4/hadoop-mapreduce-client-common-2.7.4.jar"
      ],
      sha256 = "bf222538301b993e8b96b3b0f63e4b57c5001c44da7cd5e4be523d58970d81c7"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-mapreduce-client-shuffle_2.7.4",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-mapreduce-client-shuffle/2.7.4/hadoop-mapreduce-client-shuffle-2.7.4.jar"
      ],
      sha256 = "332b1326465cafd2039c25b027e1ae41ec69fea8fabd97610db7f7fb02811659"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-yarn-common_2.7.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-yarn-common/2.7.4/hadoop-yarn-common-2.7.4.jar"],
      sha256 = "234ec53aad3881438ef04d6a573fd14edda3b7dc5d73219d1504d317f1b200e7"
    )
    http_file(
      name = "com.github.spotbugs_spotbugs-annotations_3.1.9",
      urls = [
        "https://repo1.maven.org/maven2/com/github/spotbugs/spotbugs-annotations/3.1.9/spotbugs-annotations-3.1.9.jar"
      ],
      sha256 = "68c7c46b4299e94837e236ae742f399901a950fe910fe3ca710026753b5dd2e1"
    )
    http_file(
      name = "io.netty_netty_3.10.6.Final",
      urls = ["https://repo1.maven.org/maven2/io/netty/netty/3.10.6.Final/netty-3.10.6.Final.jar"],
      sha256 = "8768a50fbe3d93a88d8e6000ea5d68e30f50dc915b3764c3c5870f70c4fb3b49"
    )
    http_file(
      name = "org.json4s_json4s-ast_2.12_3.6.6",
      urls = ["https://repo1.maven.org/maven2/org/json4s/json4s-ast_2.12/3.6.6/json4s-ast_2.12-3.6.6.jar"],
      sha256 = "09cf329ed45ac516bf3d5aebcd284838d5d4c529dc878e01f9ecc2bd9952bf27"
    )
    http_file(
      name = "org.json4s_json4s-scalap_2.12_3.6.6",
      urls = ["https://repo1.maven.org/maven2/org/json4s/json4s-scalap_2.12/3.6.6/json4s-scalap_2.12-3.6.6.jar"],
      sha256 = "7f2ddd81b9aa9447576e9b848f05c9626419a41edb659fea243afa4627bd0e60"
    )
    http_file(
      name = "org.glassfish.hk2.external_aopalliance-repackaged_2.6.1",
      urls = [
        "https://repo1.maven.org/maven2/org/glassfish/hk2/external/aopalliance-repackaged/2.6.1/aopalliance-repackaged-2.6.1.jar"
      ],
      sha256 = "bad77f9278d753406360af9e4747bd9b3161554ea9cd3d62411a0ae1f2c141fd"
    )
    http_file(
      name = "org.glassfish.hk2_hk2-api_2.6.1",
      urls = ["https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-api/2.6.1/hk2-api-2.6.1.jar"],
      sha256 = "c2cb80a01e58440ae57d5ee59af4d4d94e5180e04aff112b0cb611c07d61e773"
    )
    http_file(
      name = "org.glassfish.hk2_hk2-utils_2.6.1",
      urls = ["https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-utils/2.6.1/hk2-utils-2.6.1.jar"],
      sha256 = "30727f79086452fdefdab08451d982c2082aa239d9f75cdeb1ba271e3c887036"
    )
    http_file(
      name = "commons-digester_commons-digester_1.8",
      urls = ["https://repo1.maven.org/maven2/commons-digester/commons-digester/1.8/commons-digester-1.8.jar"],
      sha256 = "05662373044f3dff112567b7bb5dfa1174e91e074c0c727b4412788013f49d56"
    )
    http_file(
      name = "org.apache.httpcomponents_httpclient_4.2.5",
      urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpclient/4.2.5/httpclient-4.2.5.jar"],
      sha256 = "c7fb05b793a586633d2d801a889e0846f3f21c7f8dc3dab776421383f49ebb8c"
    )
    http_file(
      name = "org.apache.directory.server_apacheds-kerberos-codec_2.0.0-M15",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/directory/server/apacheds-kerberos-codec/2.0.0-M15/apacheds-kerberos-codec-2.0.0-M15.jar"
      ],
      sha256 = "4996f5b72497e94dd86d64a370158c4fb0049eea9b17ff8b27a4671d6c136ded"
    )
    http_file(
      name = "xml-apis_xml-apis_1.3.04",
      urls = ["https://repo1.maven.org/maven2/xml-apis/xml-apis/1.3.04/xml-apis-1.3.04.jar"],
      sha256 = "d404aa881eb9c5f7a4fb546e84ea11506cd417a72b5972e88eff17f43f9f8a64"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-yarn-client_2.7.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-yarn-client/2.7.4/hadoop-yarn-client-2.7.4.jar"],
      sha256 = "0b77382b23fc3534f402af465c371522ffbaf065081f8cbc5e4a4e61e7f21ee9"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-yarn-server-common_2.7.4",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-yarn-server-common/2.7.4/hadoop-yarn-server-common-2.7.4.jar"
      ],
      sha256 = "74a567219692eb245842e4cdcd65e9ed0001b0f2bd1ea94fb16781d3894b5495"
    )
    http_file(
      name = "javax.xml.bind_jaxb-api_2.2.2",
      urls = ["https://repo1.maven.org/maven2/javax/xml/bind/jaxb-api/2.2.2/jaxb-api-2.2.2.jar"],
      sha256 = "30233df6215fb982d8784de91d307596748cea98d6d502293c7c3e85c1697137"
    )
    http_file(
      name = "org.codehaus.jackson_jackson-jaxrs_1.9.13",
      urls = ["https://repo1.maven.org/maven2/org/codehaus/jackson/jackson-jaxrs/1.9.13/jackson-jaxrs-1.9.13.jar"],
      sha256 = "1770570a6ba5c87a4795c0aeb40ee7c5fe5e31df64ef1d4795a0d427796b84bb"
    )
    http_file(
      name = "org.codehaus.jackson_jackson-xc_1.9.13",
      urls = ["https://repo1.maven.org/maven2/org/codehaus/jackson/jackson-xc/1.9.13/jackson-xc-1.9.13.jar"],
      sha256 = "2d2905fcec7d1c55b775995617685dbb03672350704d9e40b492eab5b54d0be7"
    )
    http_file(
      name = "org.apache.hadoop_hadoop-yarn-server-nodemanager_2.7.4",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-yarn-server-nodemanager/2.7.4/hadoop-yarn-server-nodemanager-2.7.4.jar"
      ],
      sha256 = "aa8a8900392aaf42d8f5a7883813e8c0d4dd067073dd29133313753a02a676a6"
    )
    http_file(
      name = "commons-beanutils_commons-beanutils_1.7.0",
      urls = ["https://repo1.maven.org/maven2/commons-beanutils/commons-beanutils/1.7.0/commons-beanutils-1.7.0.jar"],
      sha256 = "24bcaa20ccbdc7c856ce0c0aea144566943403e2e9f27bd9779cda1d76823ef4"
    )
    http_file(
      name = "org.apache.httpcomponents_httpcore_4.2.4",
      urls = ["https://repo1.maven.org/maven2/org/apache/httpcomponents/httpcore/4.2.4/httpcore-4.2.4.jar"],
      sha256 = "bda2b9e0464f7a0e122d5e9bff7b384f3bc3a91af18ad51e029deaaa599e5db3"
    )
    http_file(
      name = "org.apache.directory.server_apacheds-i18n_2.0.0-M15",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/directory/server/apacheds-i18n/2.0.0-M15/apacheds-i18n-2.0.0-M15.jar"
      ],
      sha256 = "bd3b7cece7fc6364cbce32b9edd0e9628a3e889c6a93cdeff1b5e2131e2a007c"
    )
    http_file(
      name = "org.apache.directory.api_api-asn1-api_1.0.0-M20",
      urls = [
        "https://repo1.maven.org/maven2/org/apache/directory/api/api-asn1-api/1.0.0-M20/api-asn1-api-1.0.0-M20.jar"
      ],
      sha256 = "484aaf4b888b0eb699d95bea265c2d5b6ebec951d70e5c5f7691cd52dd4c8298"
    )
    http_file(
      name = "org.apache.directory.api_api-util_1.0.0-M20",
      urls = ["https://repo1.maven.org/maven2/org/apache/directory/api/api-util/1.0.0-M20/api-util-1.0.0-M20.jar"],
      sha256 = "fd32fd047ccf143c58d093b58811aa81e539f8cf83c1187809f1a241a1df12d1"
    )
    http_file(
      name = "jline_jline_0.9.94",
      urls = ["https://repo1.maven.org/maven2/jline/jline/0.9.94/jline-0.9.94.jar"],
      sha256 = "d8df0ffb12d87ca876271cda4d59b3feb94123882c1be1763b7faf2e0a0b0cbb"
    )
    http_file(
      name = "com.google.inject_guice_3.0",
      urls = ["https://repo1.maven.org/maven2/com/google/inject/guice/3.0/guice-3.0.jar"],
      sha256 = "1a59d0421ffd355cc0b70b42df1c2e9af744c8a2d0c92da379f5fca2f07f1d22"
    )
    http_file(
      name = "javax.xml.stream_stax-api_1.0-2",
      urls = ["https://repo1.maven.org/maven2/javax/xml/stream/stax-api/1.0-2/stax-api-1.0-2.jar"],
      sha256 = "e8c70ebd76f982c9582a82ef82cf6ce14a7d58a4a4dca5cb7b7fc988c80089b7"
    )
    http_file(
      name = "org.codehaus.jettison_jettison_1.1",
      urls = ["https://repo1.maven.org/maven2/org/codehaus/jettison/jettison/1.1/jettison-1.1.jar"],
      sha256 = "377940288b0643c48780137f6f68578937e1ea5ca2b73830a820c50a7b7ed801"
    )
    http_file(
      name = "javax.inject_javax.inject_1",
      urls = ["https://repo1.maven.org/maven2/javax/inject/javax.inject/1/javax.inject-1.jar"],
      sha256 = "91c77044a50c481636c32d916fd89c9118a72195390452c81065080f957de7ff"
    )
    http_file(
      name = "aopalliance_aopalliance_1.0",
      urls = ["https://repo1.maven.org/maven2/aopalliance/aopalliance/1.0/aopalliance-1.0.jar"],
      sha256 = "0addec670fedcd3f113c5c8091d783280d23f75e3acb841b61a9cdb079376a08"
    )
    http_file(
      name = "org.scalameta_scalameta_2.12_4.3.23",
      urls = ["https://repo1.maven.org/maven2/org/scalameta/scalameta_2.12/4.3.23/scalameta_2.12-4.3.23.jar"],
      sha256 = "8850861be717cf679035d39aee2d84c85cc85f621eec2ca33f78bc82afe7f948"
    )
    http_file(
      name = "org.scala-lang_scala-library_2.12.12",
      urls = ["https://repo1.maven.org/maven2/org/scala-lang/scala-library/2.12.12/scala-library-2.12.12.jar"],
      sha256 = "1673ffe8792021f704caddfe92067ed1ec75229907f84380ad68fe621358c925"
    )
    http_file(
      name = "org.scalameta_parsers_2.12_4.3.23",
      urls = ["https://repo1.maven.org/maven2/org/scalameta/parsers_2.12/4.3.23/parsers_2.12-4.3.23.jar"],
      sha256 = "224f113db0b492963432d27e9c29b28acffe3e88c0100f612930ebac96e0fbf6"
    )
    http_file(
      name = "org.scala-lang_scalap_2.12.12",
      urls = ["https://repo1.maven.org/maven2/org/scala-lang/scalap/2.12.12/scalap-2.12.12.jar"],
      sha256 = "777721c8839689ba833e059f112faa674ebdd5360e08ba2a07533e3e240ffffc"
    )
    http_file(
      name = "org.scalameta_trees_2.12_4.3.23",
      urls = ["https://repo1.maven.org/maven2/org/scalameta/trees_2.12/4.3.23/trees_2.12-4.3.23.jar"],
      sha256 = "51678784b5e949d10327afeeaf6f8b73272c72842f6dd141c70968ed969979fb"
    )
    http_file(
      name = "org.scala-lang_scala-compiler_2.12.12",
      urls = ["https://repo1.maven.org/maven2/org/scala-lang/scala-compiler/2.12.12/scala-compiler-2.12.12.jar"],
      sha256 = "9dfa682ad7c2859cdcf6a31b9734c8f1ee38e7e391aeafaef91967b6ce819b6b"
    )
    http_file(
      name = "org.scalameta_common_2.12_4.3.23",
      urls = ["https://repo1.maven.org/maven2/org/scalameta/common_2.12/4.3.23/common_2.12-4.3.23.jar"],
      sha256 = "d4d7f99383118c7c073dee80ba43ee4048f7ec594398f9b4947fb01b08bf9882"
    )
    http_file(
      name = "com.thesamet.scalapb_scalapb-runtime_2.12_0.10.8",
      urls = [
        "https://repo1.maven.org/maven2/com/thesamet/scalapb/scalapb-runtime_2.12/0.10.8/scalapb-runtime_2.12-0.10.8.jar"
      ],
      sha256 = "b22165fb01cb99a754d0f3abf1f49e1acff8571f019c3f7bbd131d32325a5215"
    )
    http_file(
      name = "org.scalameta_fastparse_2.12_1.0.1",
      urls = ["https://repo1.maven.org/maven2/org/scalameta/fastparse_2.12/1.0.1/fastparse_2.12-1.0.1.jar"],
      sha256 = "387ced762e93915c5f87fed59d8453e404273f49f812d413405696ce20273aa5"
    )
    http_file(
      name = "org.scala-lang_scala-reflect_2.12.12",
      urls = ["https://repo1.maven.org/maven2/org/scala-lang/scala-reflect/2.12.12/scala-reflect-2.12.12.jar"],
      sha256 = "3c502791757c0c8208f00033d8c4d778ed446efa6f49a6f89b59c6f92b347774"
    )
    http_file(
      name = "org.scala-lang.modules_scala-xml_2.12_1.0.6",
      urls = ["https://repo1.maven.org/maven2/org/scala-lang/modules/scala-xml_2.12/1.0.6/scala-xml_2.12-1.0.6.jar"],
      sha256 = "7cc3b6ceb56e879cb977e8e043f4bfe2e062f78795efd7efa09f85003cb3230a"
    )
    http_file(
      name = "com.lihaoyi_sourcecode_2.12_0.2.1",
      urls = ["https://repo1.maven.org/maven2/com/lihaoyi/sourcecode_2.12/0.2.1/sourcecode_2.12-0.2.1.jar"],
      sha256 = "a2cf70d2f0fa980bcda122cd51f1efdfb9af57d4c7d982d7f9f4fbe6a84b0a11"
    )
    http_file(
      name = "com.thesamet.scalapb_lenses_2.12_0.10.8",
      urls = ["https://repo1.maven.org/maven2/com/thesamet/scalapb/lenses_2.12/0.10.8/lenses_2.12-0.10.8.jar"],
      sha256 = "87bd00f40862fd931a838af868d2ec181f3267afeedd0da8dd72a8663fa4047d"
    )
    http_file(
      name = "org.scala-lang.modules_scala-collection-compat_2.12_2.1.6",
      urls = [
        "https://repo1.maven.org/maven2/org/scala-lang/modules/scala-collection-compat_2.12/2.1.6/scala-collection-compat_2.12-2.1.6.jar"
      ],
      sha256 = "0abad435516d3ab62eff012fc1169e7a4df3614f36ac130b61c1bae4283c3548"
    )
    http_file(
      name = "com.lihaoyi_fastparse_2.12_2.3.0",
      urls = ["https://repo1.maven.org/maven2/com/lihaoyi/fastparse_2.12/2.3.0/fastparse_2.12-2.3.0.jar"],
      sha256 = "780c95d0b477fb3adc6f38f43321ea569167bff4ef56481e0c7c423f156369b4"
    )
    http_file(
      name = "org.scalameta_fastparse-utils_2.12_1.0.1",
      urls = ["https://repo1.maven.org/maven2/org/scalameta/fastparse-utils_2.12/1.0.1/fastparse-utils_2.12-1.0.1.jar"],
      sha256 = "9d8ad97778ef9aedef5d4190879ed0ec54969e2fc951576fe18746ae6ce6cfcf"
    )
    http_file(
      name = "com.lihaoyi_geny_2.12_0.6.0",
      urls = ["https://repo1.maven.org/maven2/com/lihaoyi/geny_2.12/0.6.0/geny_2.12-0.6.0.jar"],
      sha256 = "cc4d4ad6d5239919247aaf4c189f44a571c1f6ecffdd063623ec16917449a80e"
    )

'''
    ctx.file("jvm_deps.bzl", content, executable = False)
    build_content = '''
load("@io_bazel_rules_scala//scala:scala_import.bzl", "scala_import")

genrule(
  name = "com.google.guava_guava_24.1.1-jre_extension",
  srcs = ["@com.google.guava_guava_24.1.1-jre//file"],
  outs = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_guava_24.1.1-jre",
  jars = ["@maven//:com.google.guava/guava-24.1.1-jre.jar"],
  deps = [
    "com.google.code.findbugs_jsr305_1.3.9", "org.checkerframework_checker-compat-qual_2.0.0",
    "com.google.errorprone_error_prone_annotations_2.1.3", "com.google.j2objc_j2objc-annotations_1.1",
    "org.codehaus.mojo_animal-sniffer-annotations_1.14"
  ],
  exports = [
    "com.google.code.findbugs_jsr305_1.3.9", "org.checkerframework_checker-compat-qual_2.0.0",
    "com.google.errorprone_error_prone_annotations_2.1.3", "com.google.j2objc_j2objc-annotations_1.1",
    "org.codehaus.mojo_animal-sniffer-annotations_1.14"
  ],
  tags = ["jvm_module=com.google.guava:guava", "jvm_version=24.1.1-jre"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.code.findbugs_jsr305_1.3.9_extension",
  srcs = ["@com.google.code.findbugs_jsr305_1.3.9//file"],
  outs = ["@maven//:com.google.code.findbugs/jsr305-1.3.9.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.code.findbugs_jsr305_1.3.9",
  jars = ["@maven//:com.google.code.findbugs/jsr305-1.3.9.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.code.findbugs:jsr305", "jvm_version=1.3.9"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.checkerframework_checker-compat-qual_2.0.0_extension",
  srcs = ["@org.checkerframework_checker-compat-qual_2.0.0//file"],
  outs = ["@maven//:org.checkerframework/checker-compat-qual-2.0.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.checkerframework_checker-compat-qual_2.0.0",
  jars = ["@maven//:org.checkerframework/checker-compat-qual-2.0.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.checkerframework:checker-compat-qual", "jvm_version=2.0.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.errorprone_error_prone_annotations_2.1.3_extension",
  srcs = ["@com.google.errorprone_error_prone_annotations_2.1.3//file"],
  outs = ["@maven//:com.google.errorprone/error_prone_annotations-2.1.3.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.errorprone_error_prone_annotations_2.1.3",
  jars = ["@maven//:com.google.errorprone/error_prone_annotations-2.1.3.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.1.3"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.j2objc_j2objc-annotations_1.1_extension",
  srcs = ["@com.google.j2objc_j2objc-annotations_1.1//file"],
  outs = ["@maven//:com.google.j2objc/j2objc-annotations-1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.j2objc_j2objc-annotations_1.1",
  jars = ["@maven//:com.google.j2objc/j2objc-annotations-1.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.j2objc:j2objc-annotations", "jvm_version=1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.mojo_animal-sniffer-annotations_1.14_extension",
  srcs = ["@org.codehaus.mojo_animal-sniffer-annotations_1.14//file"],
  outs = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.14.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.mojo_animal-sniffer-annotations_1.14",
  jars = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.14.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.codehaus.mojo:animal-sniffer-annotations", "jvm_version=1.14"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.guava_guava_27.1-jre_extension",
  srcs = ["@com.google.guava_guava_27.1-jre//file"],
  outs = ["@maven//:com.google.guava/guava-27.1-jre.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_guava_27.1-jre",
  jars = ["@maven//:com.google.guava/guava-27.1-jre.jar"],
  deps = [
    "com.google.guava_failureaccess_1.0.1",
    "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.5.2",
    "com.google.errorprone_error_prone_annotations_2.2.0", "com.google.j2objc_j2objc-annotations_1.1",
    "org.codehaus.mojo_animal-sniffer-annotations_1.17"
  ],
  exports = [
    "com.google.guava_failureaccess_1.0.1",
    "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.5.2",
    "com.google.errorprone_error_prone_annotations_2.2.0", "com.google.j2objc_j2objc-annotations_1.1",
    "org.codehaus.mojo_animal-sniffer-annotations_1.17"
  ],
  tags = ["jvm_module=com.google.guava:guava", "jvm_version=27.1-jre"],
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
  exports = [],
  tags = ["jvm_module=com.google.guava:failureaccess", "jvm_version=1.0.1"],
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
  exports = [],
  tags = ["jvm_module=com.google.guava:listenablefuture", "jvm_version=9999.0-empty-to-avoid-conflict-with-guava"],
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
  exports = [],
  tags = ["jvm_module=com.google.code.findbugs:jsr305", "jvm_version=3.0.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.checkerframework_checker-qual_2.5.2_extension",
  srcs = ["@org.checkerframework_checker-qual_2.5.2//file"],
  outs = ["@maven//:org.checkerframework/checker-qual-2.5.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.checkerframework_checker-qual_2.5.2",
  jars = ["@maven//:org.checkerframework/checker-qual-2.5.2.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.checkerframework:checker-qual", "jvm_version=2.5.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.errorprone_error_prone_annotations_2.2.0_extension",
  srcs = ["@com.google.errorprone_error_prone_annotations_2.2.0//file"],
  outs = ["@maven//:com.google.errorprone/error_prone_annotations-2.2.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.errorprone_error_prone_annotations_2.2.0",
  jars = ["@maven//:com.google.errorprone/error_prone_annotations-2.2.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.2.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.mojo_animal-sniffer-annotations_1.17_extension",
  srcs = ["@org.codehaus.mojo_animal-sniffer-annotations_1.17//file"],
  outs = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.17.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.mojo_animal-sniffer-annotations_1.17",
  jars = ["@maven//:org.codehaus.mojo/animal-sniffer-annotations-1.17.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.codehaus.mojo:animal-sniffer-annotations", "jvm_version=1.17"],
  visibility = ["//visibility:public"]
)

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
  exports = [
    "com.google.guava_failureaccess_1.0.1",
    "com.google.guava_listenablefuture_9999.0-empty-to-avoid-conflict-with-guava",
    "com.google.code.findbugs_jsr305_3.0.2", "org.checkerframework_checker-qual_2.11.1",
    "com.google.errorprone_error_prone_annotations_2.3.4", "com.google.j2objc_j2objc-annotations_1.3"
  ],
  tags = ["jvm_module=com.google.guava:guava", "jvm_version=29.0-jre"],
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
  exports = [],
  tags = ["jvm_module=org.checkerframework:checker-qual", "jvm_version=2.11.1"],
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
  exports = [],
  tags = ["jvm_module=com.google.errorprone:error_prone_annotations", "jvm_version=2.3.4"],
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
  exports = [],
  tags = ["jvm_module=com.google.j2objc:j2objc-annotations", "jvm_version=1.3"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.guava_guava_16.0.1_extension",
  srcs = ["@com.google.guava_guava_16.0.1//file"],
  outs = ["@maven//:com.google.guava/guava-16.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.guava_guava_16.0.1",
  jars = ["@maven//:com.google.guava/guava-16.0.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.guava:guava", "jvm_version=16.0.1"],
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
  exports = [
    "org.eclipse.lsp4j_org.eclipse.lsp4j.generator_0.9.0", "org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0"
  ],
  tags = ["jvm_module=org.eclipse.lsp4j:org.eclipse.lsp4j", "jvm_version=0.9.0"],
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
  exports = ["org.eclipse.lsp4j_org.eclipse.lsp4j.jsonrpc_0.9.0", "org.eclipse.xtend_org.eclipse.xtend.lib_2.19.0"],
  tags = ["jvm_module=org.eclipse.lsp4j:org.eclipse.lsp4j.generator", "jvm_version=0.9.0"],
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
  exports = ["com.google.code.gson_gson_2.8.2"],
  tags = ["jvm_module=org.eclipse.lsp4j:org.eclipse.lsp4j.jsonrpc", "jvm_version=0.9.0"],
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
  exports = [
    "org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0", "org.eclipse.xtend_org.eclipse.xtend.lib.macro_2.19.0"
  ],
  tags = ["jvm_module=org.eclipse.xtend:org.eclipse.xtend.lib", "jvm_version=2.19.0"],
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
  exports = [],
  tags = ["jvm_module=com.google.code.gson:gson", "jvm_version=2.8.2"],
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
  deps = ["com.google.guava_guava_27.1-jre"],
  exports = ["com.google.guava_guava_27.1-jre"],
  tags = ["jvm_module=org.eclipse.xtext:org.eclipse.xtext.xbase.lib", "jvm_version=2.19.0"],
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
  exports = ["org.eclipse.xtext_org.eclipse.xtext.xbase.lib_2.19.0"],
  tags = ["jvm_module=org.eclipse.xtend:org.eclipse.xtend.lib.macro", "jvm_version=2.19.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.protobuf_protobuf-java_3.13.0_extension",
  srcs = ["@com.google.protobuf_protobuf-java_3.13.0//file"],
  outs = ["@maven//:com.google.protobuf/protobuf-java-3.13.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.protobuf_protobuf-java_3.13.0",
  jars = ["@maven//:com.google.protobuf/protobuf-java-3.13.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.protobuf:protobuf-java", "jvm_version=3.13.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.protobuf_protobuf-java_2.6.1_extension",
  srcs = ["@com.google.protobuf_protobuf-java_2.6.1//file"],
  outs = ["@maven//:com.google.protobuf/protobuf-java-2.6.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.protobuf_protobuf-java_2.6.1",
  jars = ["@maven//:com.google.protobuf/protobuf-java-2.6.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.protobuf:protobuf-java", "jvm_version=2.6.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.protobuf_protobuf-java_2.5.0_extension",
  srcs = ["@com.google.protobuf_protobuf-java_2.5.0//file"],
  outs = ["@maven//:com.google.protobuf/protobuf-java-2.5.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.protobuf_protobuf-java_2.5.0",
  jars = ["@maven//:com.google.protobuf/protobuf-java-2.5.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.protobuf:protobuf-java", "jvm_version=2.5.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.spark_spark-core_2.12_3.0.1_extension",
  srcs = ["@org.apache.spark_spark-core_2.12_3.0.1//file"],
  outs = ["@maven//:org.apache.spark/spark-core_2.12-3.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.spark_spark-core_2.12_3.0.1",
  jars = ["@maven//:org.apache.spark/spark-core_2.12-3.0.1.jar"],
  deps = [
    "com.thoughtworks.paranamer_paranamer_2.8", "org.apache.avro_avro_1.8.2", "org.apache.avro_avro-mapred_1.8.2",
    "com.twitter_chill_2.12_0.9.5", "com.twitter_chill-java_0.9.5", "org.apache.xbean_xbean-asm7-shaded_4.15",
    "org.apache.hadoop_hadoop-client_2.7.4", "org.apache.spark_spark-launcher_2.12_3.0.1",
    "org.apache.spark_spark-kvstore_2.12_3.0.1", "org.apache.spark_spark-network-common_2.12_3.0.1",
    "org.apache.spark_spark-network-shuffle_2.12_3.0.1", "org.apache.spark_spark-unsafe_2.12_3.0.1",
    "javax.activation_activation_1.1.1", "org.apache.curator_curator-recipes_2.7.1",
    "org.apache.zookeeper_zookeeper_3.4.14", "javax.servlet_javax.servlet-api_3.1.0",
    "org.apache.commons_commons-lang3_3.9", "org.apache.commons_commons-math3_3.4.1",
    "org.apache.commons_commons-text_1.6", "com.google.code.findbugs_jsr305_3.0.2", "org.slf4j_slf4j-api_1.7.30",
    "org.slf4j_jul-to-slf4j_1.7.30", "org.slf4j_jcl-over-slf4j_1.7.30", "log4j_log4j_1.2.17",
    "org.slf4j_slf4j-log4j12_1.7.30", "com.ning_compress-lzf_1.0.3", "org.xerial.snappy_snappy-java_1.1.7.5",
    "org.lz4_lz4-java_1.7.1", "com.github.luben_zstd-jni_1.4.4-3", "org.roaringbitmap_RoaringBitmap_0.7.45",
    "commons-net_commons-net_3.1", "org.scala-lang.modules_scala-xml_2.12_1.2.0",
    "org.scala-lang_scala-library_2.12.10", "org.scala-lang_scala-reflect_2.12.10",
    "org.json4s_json4s-jackson_2.12_3.6.6", "org.glassfish.jersey.core_jersey-client_2.30",
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.jersey.core_jersey-server_2.30",
    "org.glassfish.jersey.containers_jersey-container-servlet_2.30",
    "org.glassfish.jersey.containers_jersey-container-servlet-core_2.30", "org.glassfish.jersey.inject_jersey-hk2_2.30",
    "io.netty_netty-all_4.1.47.Final", "com.clearspring.analytics_stream_2.9.6",
    "io.dropwizard.metrics_metrics-core_4.1.1", "io.dropwizard.metrics_metrics-jvm_4.1.1",
    "io.dropwizard.metrics_metrics-json_4.1.1", "io.dropwizard.metrics_metrics-graphite_4.1.1",
    "io.dropwizard.metrics_metrics-jmx_4.1.1", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "com.fasterxml.jackson.module_jackson-module-scala_2.12_2.10.0", "org.apache.ivy_ivy_2.4.0", "oro_oro_2.0.8",
    "net.razorvine_pyrolite_4.30", "net.sf.py4j_py4j_0.10.9", "org.apache.spark_spark-tags_2.12_3.0.1",
    "org.apache.commons_commons-crypto_1.0.0", "org.spark-project.spark_unused_1.0.0"
  ],
  exports = [
    "com.thoughtworks.paranamer_paranamer_2.8", "org.apache.avro_avro_1.8.2", "org.apache.avro_avro-mapred_1.8.2",
    "com.twitter_chill_2.12_0.9.5", "com.twitter_chill-java_0.9.5", "org.apache.xbean_xbean-asm7-shaded_4.15",
    "org.apache.hadoop_hadoop-client_2.7.4", "org.apache.spark_spark-launcher_2.12_3.0.1",
    "org.apache.spark_spark-kvstore_2.12_3.0.1", "org.apache.spark_spark-network-common_2.12_3.0.1",
    "org.apache.spark_spark-network-shuffle_2.12_3.0.1", "org.apache.spark_spark-unsafe_2.12_3.0.1",
    "javax.activation_activation_1.1.1", "org.apache.curator_curator-recipes_2.7.1",
    "org.apache.zookeeper_zookeeper_3.4.14", "javax.servlet_javax.servlet-api_3.1.0",
    "org.apache.commons_commons-lang3_3.9", "org.apache.commons_commons-math3_3.4.1",
    "org.apache.commons_commons-text_1.6", "com.google.code.findbugs_jsr305_3.0.2", "org.slf4j_slf4j-api_1.7.30",
    "org.slf4j_jul-to-slf4j_1.7.30", "org.slf4j_jcl-over-slf4j_1.7.30", "log4j_log4j_1.2.17",
    "org.slf4j_slf4j-log4j12_1.7.30", "com.ning_compress-lzf_1.0.3", "org.xerial.snappy_snappy-java_1.1.7.5",
    "org.lz4_lz4-java_1.7.1", "com.github.luben_zstd-jni_1.4.4-3", "org.roaringbitmap_RoaringBitmap_0.7.45",
    "commons-net_commons-net_3.1", "org.scala-lang.modules_scala-xml_2.12_1.2.0",
    "org.scala-lang_scala-library_2.12.10", "org.scala-lang_scala-reflect_2.12.10",
    "org.json4s_json4s-jackson_2.12_3.6.6", "org.glassfish.jersey.core_jersey-client_2.30",
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.jersey.core_jersey-server_2.30",
    "org.glassfish.jersey.containers_jersey-container-servlet_2.30",
    "org.glassfish.jersey.containers_jersey-container-servlet-core_2.30", "org.glassfish.jersey.inject_jersey-hk2_2.30",
    "io.netty_netty-all_4.1.47.Final", "com.clearspring.analytics_stream_2.9.6",
    "io.dropwizard.metrics_metrics-core_4.1.1", "io.dropwizard.metrics_metrics-jvm_4.1.1",
    "io.dropwizard.metrics_metrics-json_4.1.1", "io.dropwizard.metrics_metrics-graphite_4.1.1",
    "io.dropwizard.metrics_metrics-jmx_4.1.1", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "com.fasterxml.jackson.module_jackson-module-scala_2.12_2.10.0", "org.apache.ivy_ivy_2.4.0", "oro_oro_2.0.8",
    "net.razorvine_pyrolite_4.30", "net.sf.py4j_py4j_0.10.9", "org.apache.spark_spark-tags_2.12_3.0.1",
    "org.apache.commons_commons-crypto_1.0.0", "org.spark-project.spark_unused_1.0.0"
  ],
  tags = ["jvm_module=org.apache.spark:spark-core_2.12", "jvm_version=3.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.thoughtworks.paranamer_paranamer_2.8_extension",
  srcs = ["@com.thoughtworks.paranamer_paranamer_2.8//file"],
  outs = ["@maven//:com.thoughtworks.paranamer/paranamer-2.8.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.thoughtworks.paranamer_paranamer_2.8",
  jars = ["@maven//:com.thoughtworks.paranamer/paranamer-2.8.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.thoughtworks.paranamer:paranamer", "jvm_version=2.8"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.avro_avro_1.8.2_extension",
  srcs = ["@org.apache.avro_avro_1.8.2//file"],
  outs = ["@maven//:org.apache.avro/avro-1.8.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.avro_avro_1.8.2",
  jars = ["@maven//:org.apache.avro/avro-1.8.2.jar"],
  deps = [
    "org.codehaus.jackson_jackson-core-asl_1.9.13", "org.codehaus.jackson_jackson-mapper-asl_1.9.13",
    "com.thoughtworks.paranamer_paranamer_2.8", "org.xerial.snappy_snappy-java_1.1.7.5",
    "org.apache.commons_commons-compress_1.8.1", "org.tukaani_xz_1.5", "org.slf4j_slf4j-api_1.7.30"
  ],
  exports = [
    "org.codehaus.jackson_jackson-core-asl_1.9.13", "org.codehaus.jackson_jackson-mapper-asl_1.9.13",
    "com.thoughtworks.paranamer_paranamer_2.8", "org.xerial.snappy_snappy-java_1.1.7.5",
    "org.apache.commons_commons-compress_1.8.1", "org.tukaani_xz_1.5", "org.slf4j_slf4j-api_1.7.30"
  ],
  tags = ["jvm_module=org.apache.avro:avro", "jvm_version=1.8.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.avro_avro-mapred_1.8.2_extension",
  srcs = ["@org.apache.avro_avro-mapred_1.8.2//file"],
  outs = ["@maven//:org.apache.avro/avro-mapred-1.8.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.avro_avro-mapred_1.8.2",
  jars = ["@maven//:org.apache.avro/avro-mapred-1.8.2.jar"],
  deps = [
    "org.apache.avro_avro-ipc_1.8.2", "org.codehaus.jackson_jackson-core-asl_1.9.13",
    "org.codehaus.jackson_jackson-mapper-asl_1.9.13", "commons-codec_commons-codec_1.9", "org.slf4j_slf4j-api_1.7.30"
  ],
  exports = [
    "org.apache.avro_avro-ipc_1.8.2", "org.codehaus.jackson_jackson-core-asl_1.9.13",
    "org.codehaus.jackson_jackson-mapper-asl_1.9.13", "commons-codec_commons-codec_1.9", "org.slf4j_slf4j-api_1.7.30"
  ],
  tags = ["jvm_module=org.apache.avro:avro-mapred", "jvm_version=1.8.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.twitter_chill_2.12_0.9.5_extension",
  srcs = ["@com.twitter_chill_2.12_0.9.5//file"],
  outs = ["@maven//:com.twitter/chill_2.12-0.9.5.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.twitter_chill_2.12_0.9.5",
  jars = ["@maven//:com.twitter/chill_2.12-0.9.5.jar"],
  deps = [
    "org.scala-lang_scala-library_2.12.10", "com.twitter_chill-java_0.9.5", "com.esotericsoftware_kryo-shaded_4.0.2",
    "org.apache.xbean_xbean-asm7-shaded_4.15"
  ],
  exports = [
    "org.scala-lang_scala-library_2.12.10", "com.twitter_chill-java_0.9.5", "com.esotericsoftware_kryo-shaded_4.0.2",
    "org.apache.xbean_xbean-asm7-shaded_4.15"
  ],
  tags = ["jvm_module=com.twitter:chill_2.12", "jvm_version=0.9.5"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.twitter_chill-java_0.9.5_extension",
  srcs = ["@com.twitter_chill-java_0.9.5//file"],
  outs = ["@maven//:com.twitter/chill-java-0.9.5.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.twitter_chill-java_0.9.5",
  jars = ["@maven//:com.twitter/chill-java-0.9.5.jar"],
  deps = ["com.esotericsoftware_kryo-shaded_4.0.2"],
  exports = ["com.esotericsoftware_kryo-shaded_4.0.2"],
  tags = ["jvm_module=com.twitter:chill-java", "jvm_version=0.9.5"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.xbean_xbean-asm7-shaded_4.15_extension",
  srcs = ["@org.apache.xbean_xbean-asm7-shaded_4.15//file"],
  outs = ["@maven//:org.apache.xbean/xbean-asm7-shaded-4.15.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.xbean_xbean-asm7-shaded_4.15",
  jars = ["@maven//:org.apache.xbean/xbean-asm7-shaded-4.15.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.xbean:xbean-asm7-shaded", "jvm_version=4.15"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-client_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-client_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-client-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-client_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-client-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-common_2.7.4", "org.apache.hadoop_hadoop-hdfs_2.7.4",
    "org.apache.hadoop_hadoop-mapreduce-client-app_2.7.4", "org.apache.hadoop_hadoop-yarn-api_2.7.4",
    "org.apache.hadoop_hadoop-mapreduce-client-core_2.7.4", "org.apache.hadoop_hadoop-mapreduce-client-jobclient_2.7.4",
    "org.apache.hadoop_hadoop-annotations_2.7.4"
  ],
  exports = [
    "org.apache.hadoop_hadoop-common_2.7.4", "org.apache.hadoop_hadoop-hdfs_2.7.4",
    "org.apache.hadoop_hadoop-mapreduce-client-app_2.7.4", "org.apache.hadoop_hadoop-yarn-api_2.7.4",
    "org.apache.hadoop_hadoop-mapreduce-client-core_2.7.4", "org.apache.hadoop_hadoop-mapreduce-client-jobclient_2.7.4",
    "org.apache.hadoop_hadoop-annotations_2.7.4"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-client", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.spark_spark-launcher_2.12_3.0.1_extension",
  srcs = ["@org.apache.spark_spark-launcher_2.12_3.0.1//file"],
  outs = ["@maven//:org.apache.spark/spark-launcher_2.12-3.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.spark_spark-launcher_2.12_3.0.1",
  jars = ["@maven//:org.apache.spark/spark-launcher_2.12-3.0.1.jar"],
  deps = ["org.apache.spark_spark-tags_2.12_3.0.1", "org.spark-project.spark_unused_1.0.0"],
  exports = ["org.apache.spark_spark-tags_2.12_3.0.1", "org.spark-project.spark_unused_1.0.0"],
  tags = ["jvm_module=org.apache.spark:spark-launcher_2.12", "jvm_version=3.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.spark_spark-kvstore_2.12_3.0.1_extension",
  srcs = ["@org.apache.spark_spark-kvstore_2.12_3.0.1//file"],
  outs = ["@maven//:org.apache.spark/spark-kvstore_2.12-3.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.spark_spark-kvstore_2.12_3.0.1",
  jars = ["@maven//:org.apache.spark/spark-kvstore_2.12-3.0.1.jar"],
  deps = [
    "org.apache.spark_spark-tags_2.12_3.0.1", "org.fusesource.leveldbjni_leveldbjni-all_1.8",
    "com.fasterxml.jackson.core_jackson-core_2.10.0", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "com.fasterxml.jackson.core_jackson-annotations_2.10.0", "org.spark-project.spark_unused_1.0.0"
  ],
  exports = [
    "org.apache.spark_spark-tags_2.12_3.0.1", "org.fusesource.leveldbjni_leveldbjni-all_1.8",
    "com.fasterxml.jackson.core_jackson-core_2.10.0", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "com.fasterxml.jackson.core_jackson-annotations_2.10.0", "org.spark-project.spark_unused_1.0.0"
  ],
  tags = ["jvm_module=org.apache.spark:spark-kvstore_2.12", "jvm_version=3.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.spark_spark-network-common_2.12_3.0.1_extension",
  srcs = ["@org.apache.spark_spark-network-common_2.12_3.0.1//file"],
  outs = ["@maven//:org.apache.spark/spark-network-common_2.12-3.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.spark_spark-network-common_2.12_3.0.1",
  jars = ["@maven//:org.apache.spark/spark-network-common_2.12-3.0.1.jar"],
  deps = [
    "org.scala-lang_scala-library_2.12.10", "io.netty_netty-all_4.1.47.Final", "org.apache.commons_commons-lang3_3.9",
    "org.fusesource.leveldbjni_leveldbjni-all_1.8", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "com.fasterxml.jackson.core_jackson-annotations_2.10.0", "io.dropwizard.metrics_metrics-core_4.1.1",
    "com.google.code.findbugs_jsr305_3.0.2", "org.apache.commons_commons-crypto_1.0.0",
    "org.spark-project.spark_unused_1.0.0"
  ],
  exports = [
    "org.scala-lang_scala-library_2.12.10", "io.netty_netty-all_4.1.47.Final", "org.apache.commons_commons-lang3_3.9",
    "org.fusesource.leveldbjni_leveldbjni-all_1.8", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "com.fasterxml.jackson.core_jackson-annotations_2.10.0", "io.dropwizard.metrics_metrics-core_4.1.1",
    "com.google.code.findbugs_jsr305_3.0.2", "org.apache.commons_commons-crypto_1.0.0",
    "org.spark-project.spark_unused_1.0.0"
  ],
  tags = ["jvm_module=org.apache.spark:spark-network-common_2.12", "jvm_version=3.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.spark_spark-network-shuffle_2.12_3.0.1_extension",
  srcs = ["@org.apache.spark_spark-network-shuffle_2.12_3.0.1//file"],
  outs = ["@maven//:org.apache.spark/spark-network-shuffle_2.12-3.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.spark_spark-network-shuffle_2.12_3.0.1",
  jars = ["@maven//:org.apache.spark/spark-network-shuffle_2.12-3.0.1.jar"],
  deps = [
    "org.apache.spark_spark-network-common_2.12_3.0.1", "io.dropwizard.metrics_metrics-core_4.1.1",
    "org.spark-project.spark_unused_1.0.0"
  ],
  exports = [
    "org.apache.spark_spark-network-common_2.12_3.0.1", "io.dropwizard.metrics_metrics-core_4.1.1",
    "org.spark-project.spark_unused_1.0.0"
  ],
  tags = ["jvm_module=org.apache.spark:spark-network-shuffle_2.12", "jvm_version=3.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.spark_spark-unsafe_2.12_3.0.1_extension",
  srcs = ["@org.apache.spark_spark-unsafe_2.12_3.0.1//file"],
  outs = ["@maven//:org.apache.spark/spark-unsafe_2.12-3.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.spark_spark-unsafe_2.12_3.0.1",
  jars = ["@maven//:org.apache.spark/spark-unsafe_2.12-3.0.1.jar"],
  deps = [
    "org.apache.spark_spark-tags_2.12_3.0.1", "com.twitter_chill_2.12_0.9.5", "com.google.code.findbugs_jsr305_3.0.2",
    "org.spark-project.spark_unused_1.0.0"
  ],
  exports = [
    "org.apache.spark_spark-tags_2.12_3.0.1", "com.twitter_chill_2.12_0.9.5", "com.google.code.findbugs_jsr305_3.0.2",
    "org.spark-project.spark_unused_1.0.0"
  ],
  tags = ["jvm_module=org.apache.spark:spark-unsafe_2.12", "jvm_version=3.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "javax.activation_activation_1.1.1_extension",
  srcs = ["@javax.activation_activation_1.1.1//file"],
  outs = ["@maven//:javax.activation/activation-1.1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "javax.activation_activation_1.1.1",
  jars = ["@maven//:javax.activation/activation-1.1.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=javax.activation:activation", "jvm_version=1.1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.curator_curator-recipes_2.7.1_extension",
  srcs = ["@org.apache.curator_curator-recipes_2.7.1//file"],
  outs = ["@maven//:org.apache.curator/curator-recipes-2.7.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.curator_curator-recipes_2.7.1",
  jars = ["@maven//:org.apache.curator/curator-recipes-2.7.1.jar"],
  deps = [
    "org.apache.curator_curator-framework_2.7.1", "org.apache.zookeeper_zookeeper_3.4.14",
    "com.google.guava_guava_16.0.1"
  ],
  exports = [
    "org.apache.curator_curator-framework_2.7.1", "org.apache.zookeeper_zookeeper_3.4.14",
    "com.google.guava_guava_16.0.1"
  ],
  tags = ["jvm_module=org.apache.curator:curator-recipes", "jvm_version=2.7.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.zookeeper_zookeeper_3.4.14_extension",
  srcs = ["@org.apache.zookeeper_zookeeper_3.4.14//file"],
  outs = ["@maven//:org.apache.zookeeper/zookeeper-3.4.14.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.zookeeper_zookeeper_3.4.14",
  jars = ["@maven//:org.apache.zookeeper/zookeeper-3.4.14.jar"],
  deps = [
    "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30", "com.github.spotbugs_spotbugs-annotations_3.1.9",
    "log4j_log4j_1.2.17", "org.apache.yetus_audience-annotations_0.5.0"
  ],
  exports = [
    "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30", "com.github.spotbugs_spotbugs-annotations_3.1.9",
    "log4j_log4j_1.2.17", "org.apache.yetus_audience-annotations_0.5.0"
  ],
  tags = ["jvm_module=org.apache.zookeeper:zookeeper", "jvm_version=3.4.14"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "javax.servlet_javax.servlet-api_3.1.0_extension",
  srcs = ["@javax.servlet_javax.servlet-api_3.1.0//file"],
  outs = ["@maven//:javax.servlet/javax.servlet-api-3.1.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "javax.servlet_javax.servlet-api_3.1.0",
  jars = ["@maven//:javax.servlet/javax.servlet-api-3.1.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=javax.servlet:javax.servlet-api", "jvm_version=3.1.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.commons_commons-lang3_3.9_extension",
  srcs = ["@org.apache.commons_commons-lang3_3.9//file"],
  outs = ["@maven//:org.apache.commons/commons-lang3-3.9.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.commons_commons-lang3_3.9",
  jars = ["@maven//:org.apache.commons/commons-lang3-3.9.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.commons:commons-lang3", "jvm_version=3.9"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.commons_commons-math3_3.4.1_extension",
  srcs = ["@org.apache.commons_commons-math3_3.4.1//file"],
  outs = ["@maven//:org.apache.commons/commons-math3-3.4.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.commons_commons-math3_3.4.1",
  jars = ["@maven//:org.apache.commons/commons-math3-3.4.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.commons:commons-math3", "jvm_version=3.4.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.commons_commons-text_1.6_extension",
  srcs = ["@org.apache.commons_commons-text_1.6//file"],
  outs = ["@maven//:org.apache.commons/commons-text-1.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.commons_commons-text_1.6",
  jars = ["@maven//:org.apache.commons/commons-text-1.6.jar"],
  deps = ["org.apache.commons_commons-lang3_3.9"],
  exports = ["org.apache.commons_commons-lang3_3.9"],
  tags = ["jvm_module=org.apache.commons:commons-text", "jvm_version=1.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.slf4j_slf4j-api_1.7.30_extension",
  srcs = ["@org.slf4j_slf4j-api_1.7.30//file"],
  outs = ["@maven//:org.slf4j/slf4j-api-1.7.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.slf4j_slf4j-api_1.7.30",
  jars = ["@maven//:org.slf4j/slf4j-api-1.7.30.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.slf4j:slf4j-api", "jvm_version=1.7.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.slf4j_jul-to-slf4j_1.7.30_extension",
  srcs = ["@org.slf4j_jul-to-slf4j_1.7.30//file"],
  outs = ["@maven//:org.slf4j/jul-to-slf4j-1.7.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.slf4j_jul-to-slf4j_1.7.30",
  jars = ["@maven//:org.slf4j/jul-to-slf4j-1.7.30.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30"],
  exports = ["org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=org.slf4j:jul-to-slf4j", "jvm_version=1.7.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.slf4j_jcl-over-slf4j_1.7.30_extension",
  srcs = ["@org.slf4j_jcl-over-slf4j_1.7.30//file"],
  outs = ["@maven//:org.slf4j/jcl-over-slf4j-1.7.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.slf4j_jcl-over-slf4j_1.7.30",
  jars = ["@maven//:org.slf4j/jcl-over-slf4j-1.7.30.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30"],
  exports = ["org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=org.slf4j:jcl-over-slf4j", "jvm_version=1.7.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "log4j_log4j_1.2.17_extension",
  srcs = ["@log4j_log4j_1.2.17//file"],
  outs = ["@maven//:log4j/log4j-1.2.17.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "log4j_log4j_1.2.17",
  jars = ["@maven//:log4j/log4j-1.2.17.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=log4j:log4j", "jvm_version=1.2.17"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.slf4j_slf4j-log4j12_1.7.30_extension",
  srcs = ["@org.slf4j_slf4j-log4j12_1.7.30//file"],
  outs = ["@maven//:org.slf4j/slf4j-log4j12-1.7.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.slf4j_slf4j-log4j12_1.7.30",
  jars = ["@maven//:org.slf4j/slf4j-log4j12-1.7.30.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30", "log4j_log4j_1.2.17"],
  exports = ["org.slf4j_slf4j-api_1.7.30", "log4j_log4j_1.2.17"],
  tags = ["jvm_module=org.slf4j:slf4j-log4j12", "jvm_version=1.7.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.ning_compress-lzf_1.0.3_extension",
  srcs = ["@com.ning_compress-lzf_1.0.3//file"],
  outs = ["@maven//:com.ning/compress-lzf-1.0.3.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.ning_compress-lzf_1.0.3",
  jars = ["@maven//:com.ning/compress-lzf-1.0.3.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.ning:compress-lzf", "jvm_version=1.0.3"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.xerial.snappy_snappy-java_1.1.7.5_extension",
  srcs = ["@org.xerial.snappy_snappy-java_1.1.7.5//file"],
  outs = ["@maven//:org.xerial.snappy/snappy-java-1.1.7.5.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.xerial.snappy_snappy-java_1.1.7.5",
  jars = ["@maven//:org.xerial.snappy/snappy-java-1.1.7.5.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.xerial.snappy:snappy-java", "jvm_version=1.1.7.5"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.lz4_lz4-java_1.7.1_extension",
  srcs = ["@org.lz4_lz4-java_1.7.1//file"],
  outs = ["@maven//:org.lz4/lz4-java-1.7.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.lz4_lz4-java_1.7.1",
  jars = ["@maven//:org.lz4/lz4-java-1.7.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.lz4:lz4-java", "jvm_version=1.7.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.github.luben_zstd-jni_1.4.4-3_extension",
  srcs = ["@com.github.luben_zstd-jni_1.4.4-3//file"],
  outs = ["@maven//:com.github.luben/zstd-jni-1.4.4-3.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.github.luben_zstd-jni_1.4.4-3",
  jars = ["@maven//:com.github.luben/zstd-jni-1.4.4-3.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.github.luben:zstd-jni", "jvm_version=1.4.4-3"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.roaringbitmap_RoaringBitmap_0.7.45_extension",
  srcs = ["@org.roaringbitmap_RoaringBitmap_0.7.45//file"],
  outs = ["@maven//:org.roaringbitmap/RoaringBitmap-0.7.45.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.roaringbitmap_RoaringBitmap_0.7.45",
  jars = ["@maven//:org.roaringbitmap/RoaringBitmap-0.7.45.jar"],
  deps = ["org.roaringbitmap_shims_0.7.45"],
  exports = ["org.roaringbitmap_shims_0.7.45"],
  tags = ["jvm_module=org.roaringbitmap:RoaringBitmap", "jvm_version=0.7.45"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-net_commons-net_3.1_extension",
  srcs = ["@commons-net_commons-net_3.1//file"],
  outs = ["@maven//:commons-net/commons-net-3.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-net_commons-net_3.1",
  jars = ["@maven//:commons-net/commons-net-3.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=commons-net:commons-net", "jvm_version=3.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang.modules_scala-xml_2.12_1.2.0_extension",
  srcs = ["@org.scala-lang.modules_scala-xml_2.12_1.2.0//file"],
  outs = ["@maven//:org.scala-lang.modules/scala-xml_2.12-1.2.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang.modules_scala-xml_2.12_1.2.0",
  jars = ["@maven//:org.scala-lang.modules/scala-xml_2.12-1.2.0.jar"],
  deps = ["org.scala-lang_scala-library_2.12.10"],
  exports = ["org.scala-lang_scala-library_2.12.10"],
  tags = ["jvm_module=org.scala-lang.modules:scala-xml_2.12", "jvm_version=1.2.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang_scala-library_2.12.10_extension",
  srcs = ["@org.scala-lang_scala-library_2.12.10//file"],
  outs = ["@maven//:org.scala-lang/scala-library-2.12.10.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang_scala-library_2.12.10",
  jars = ["@maven//:org.scala-lang/scala-library-2.12.10.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.scala-lang:scala-library", "jvm_version=2.12.10"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang_scala-reflect_2.12.10_extension",
  srcs = ["@org.scala-lang_scala-reflect_2.12.10//file"],
  outs = ["@maven//:org.scala-lang/scala-reflect-2.12.10.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang_scala-reflect_2.12.10",
  jars = ["@maven//:org.scala-lang/scala-reflect-2.12.10.jar"],
  deps = ["org.scala-lang_scala-library_2.12.10"],
  exports = ["org.scala-lang_scala-library_2.12.10"],
  tags = ["jvm_module=org.scala-lang:scala-reflect", "jvm_version=2.12.10"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.json4s_json4s-jackson_2.12_3.6.6_extension",
  srcs = ["@org.json4s_json4s-jackson_2.12_3.6.6//file"],
  outs = ["@maven//:org.json4s/json4s-jackson_2.12-3.6.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.json4s_json4s-jackson_2.12_3.6.6",
  jars = ["@maven//:org.json4s/json4s-jackson_2.12-3.6.6.jar"],
  deps = ["org.scala-lang_scala-library_2.12.10", "org.json4s_json4s-core_2.12_3.6.6"],
  exports = ["org.scala-lang_scala-library_2.12.10", "org.json4s_json4s-core_2.12_3.6.6"],
  tags = ["jvm_module=org.json4s:json4s-jackson_2.12", "jvm_version=3.6.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.jersey.core_jersey-client_2.30_extension",
  srcs = ["@org.glassfish.jersey.core_jersey-client_2.30//file"],
  outs = ["@maven//:org.glassfish.jersey.core/jersey-client-2.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.jersey.core_jersey-client_2.30",
  jars = ["@maven//:org.glassfish.jersey.core/jersey-client-2.30.jar"],
  deps = [
    "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6", "org.glassfish.jersey.core_jersey-common_2.30",
    "org.glassfish.hk2.external_jakarta.inject_2.6.1"
  ],
  exports = [
    "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6", "org.glassfish.jersey.core_jersey-common_2.30",
    "org.glassfish.hk2.external_jakarta.inject_2.6.1"
  ],
  tags = ["jvm_module=org.glassfish.jersey.core:jersey-client", "jvm_version=2.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.jersey.core_jersey-common_2.30_extension",
  srcs = ["@org.glassfish.jersey.core_jersey-common_2.30//file"],
  outs = ["@maven//:org.glassfish.jersey.core/jersey-common-2.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.jersey.core_jersey-common_2.30",
  jars = ["@maven//:org.glassfish.jersey.core/jersey-common-2.30.jar"],
  deps = [
    "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6", "jakarta.annotation_jakarta.annotation-api_1.3.5",
    "org.glassfish.hk2.external_jakarta.inject_2.6.1", "org.glassfish.hk2_osgi-resource-locator_1.0.3"
  ],
  exports = [
    "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6", "jakarta.annotation_jakarta.annotation-api_1.3.5",
    "org.glassfish.hk2.external_jakarta.inject_2.6.1", "org.glassfish.hk2_osgi-resource-locator_1.0.3"
  ],
  tags = ["jvm_module=org.glassfish.jersey.core:jersey-common", "jvm_version=2.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.jersey.core_jersey-server_2.30_extension",
  srcs = ["@org.glassfish.jersey.core_jersey-server_2.30//file"],
  outs = ["@maven//:org.glassfish.jersey.core/jersey-server-2.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.jersey.core_jersey-server_2.30",
  jars = ["@maven//:org.glassfish.jersey.core/jersey-server-2.30.jar"],
  deps = [
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.jersey.core_jersey-client_2.30",
    "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6", "org.glassfish.jersey.media_jersey-media-jaxb_2.30",
    "jakarta.annotation_jakarta.annotation-api_1.3.5", "org.glassfish.hk2.external_jakarta.inject_2.6.1",
    "jakarta.validation_jakarta.validation-api_2.0.2"
  ],
  exports = [
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.jersey.core_jersey-client_2.30",
    "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6", "org.glassfish.jersey.media_jersey-media-jaxb_2.30",
    "jakarta.annotation_jakarta.annotation-api_1.3.5", "org.glassfish.hk2.external_jakarta.inject_2.6.1",
    "jakarta.validation_jakarta.validation-api_2.0.2"
  ],
  tags = ["jvm_module=org.glassfish.jersey.core:jersey-server", "jvm_version=2.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.jersey.containers_jersey-container-servlet_2.30_extension",
  srcs = ["@org.glassfish.jersey.containers_jersey-container-servlet_2.30//file"],
  outs = ["@maven//:org.glassfish.jersey.containers/jersey-container-servlet-2.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.jersey.containers_jersey-container-servlet_2.30",
  jars = ["@maven//:org.glassfish.jersey.containers/jersey-container-servlet-2.30.jar"],
  deps = [
    "org.glassfish.jersey.containers_jersey-container-servlet-core_2.30",
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.jersey.core_jersey-server_2.30",
    "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6"
  ],
  exports = [
    "org.glassfish.jersey.containers_jersey-container-servlet-core_2.30",
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.jersey.core_jersey-server_2.30",
    "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6"
  ],
  tags = ["jvm_module=org.glassfish.jersey.containers:jersey-container-servlet", "jvm_version=2.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.jersey.containers_jersey-container-servlet-core_2.30_extension",
  srcs = ["@org.glassfish.jersey.containers_jersey-container-servlet-core_2.30//file"],
  outs = ["@maven//:org.glassfish.jersey.containers/jersey-container-servlet-core-2.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.jersey.containers_jersey-container-servlet-core_2.30",
  jars = ["@maven//:org.glassfish.jersey.containers/jersey-container-servlet-core-2.30.jar"],
  deps = [
    "org.glassfish.hk2.external_jakarta.inject_2.6.1", "org.glassfish.jersey.core_jersey-common_2.30",
    "org.glassfish.jersey.core_jersey-server_2.30", "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6"
  ],
  exports = [
    "org.glassfish.hk2.external_jakarta.inject_2.6.1", "org.glassfish.jersey.core_jersey-common_2.30",
    "org.glassfish.jersey.core_jersey-server_2.30", "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6"
  ],
  tags = ["jvm_module=org.glassfish.jersey.containers:jersey-container-servlet-core", "jvm_version=2.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.jersey.inject_jersey-hk2_2.30_extension",
  srcs = ["@org.glassfish.jersey.inject_jersey-hk2_2.30//file"],
  outs = ["@maven//:org.glassfish.jersey.inject/jersey-hk2-2.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.jersey.inject_jersey-hk2_2.30",
  jars = ["@maven//:org.glassfish.jersey.inject/jersey-hk2-2.30.jar"],
  deps = [
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.hk2_hk2-locator_2.6.1",
    "org.javassist_javassist_3.25.0-GA"
  ],
  exports = [
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.hk2_hk2-locator_2.6.1",
    "org.javassist_javassist_3.25.0-GA"
  ],
  tags = ["jvm_module=org.glassfish.jersey.inject:jersey-hk2", "jvm_version=2.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "io.netty_netty-all_4.1.47.Final_extension",
  srcs = ["@io.netty_netty-all_4.1.47.Final//file"],
  outs = ["@maven//:io.netty/netty-all-4.1.47.Final.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "io.netty_netty-all_4.1.47.Final",
  jars = ["@maven//:io.netty/netty-all-4.1.47.Final.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=io.netty:netty-all", "jvm_version=4.1.47.Final"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.clearspring.analytics_stream_2.9.6_extension",
  srcs = ["@com.clearspring.analytics_stream_2.9.6//file"],
  outs = ["@maven//:com.clearspring.analytics/stream-2.9.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.clearspring.analytics_stream_2.9.6",
  jars = ["@maven//:com.clearspring.analytics/stream-2.9.6.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30"],
  exports = ["org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=com.clearspring.analytics:stream", "jvm_version=2.9.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "io.dropwizard.metrics_metrics-core_4.1.1_extension",
  srcs = ["@io.dropwizard.metrics_metrics-core_4.1.1//file"],
  outs = ["@maven//:io.dropwizard.metrics/metrics-core-4.1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "io.dropwizard.metrics_metrics-core_4.1.1",
  jars = ["@maven//:io.dropwizard.metrics/metrics-core-4.1.1.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30"],
  exports = ["org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=io.dropwizard.metrics:metrics-core", "jvm_version=4.1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "io.dropwizard.metrics_metrics-jvm_4.1.1_extension",
  srcs = ["@io.dropwizard.metrics_metrics-jvm_4.1.1//file"],
  outs = ["@maven//:io.dropwizard.metrics/metrics-jvm-4.1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "io.dropwizard.metrics_metrics-jvm_4.1.1",
  jars = ["@maven//:io.dropwizard.metrics/metrics-jvm-4.1.1.jar"],
  deps = ["io.dropwizard.metrics_metrics-core_4.1.1", "org.slf4j_slf4j-api_1.7.30"],
  exports = ["io.dropwizard.metrics_metrics-core_4.1.1", "org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=io.dropwizard.metrics:metrics-jvm", "jvm_version=4.1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "io.dropwizard.metrics_metrics-json_4.1.1_extension",
  srcs = ["@io.dropwizard.metrics_metrics-json_4.1.1//file"],
  outs = ["@maven//:io.dropwizard.metrics/metrics-json-4.1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "io.dropwizard.metrics_metrics-json_4.1.1",
  jars = ["@maven//:io.dropwizard.metrics/metrics-json-4.1.1.jar"],
  deps = [
    "io.dropwizard.metrics_metrics-core_4.1.1", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "org.slf4j_slf4j-api_1.7.30"
  ],
  exports = [
    "io.dropwizard.metrics_metrics-core_4.1.1", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "org.slf4j_slf4j-api_1.7.30"
  ],
  tags = ["jvm_module=io.dropwizard.metrics:metrics-json", "jvm_version=4.1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "io.dropwizard.metrics_metrics-graphite_4.1.1_extension",
  srcs = ["@io.dropwizard.metrics_metrics-graphite_4.1.1//file"],
  outs = ["@maven//:io.dropwizard.metrics/metrics-graphite-4.1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "io.dropwizard.metrics_metrics-graphite_4.1.1",
  jars = ["@maven//:io.dropwizard.metrics/metrics-graphite-4.1.1.jar"],
  deps = ["io.dropwizard.metrics_metrics-core_4.1.1", "org.slf4j_slf4j-api_1.7.30"],
  exports = ["io.dropwizard.metrics_metrics-core_4.1.1", "org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=io.dropwizard.metrics:metrics-graphite", "jvm_version=4.1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "io.dropwizard.metrics_metrics-jmx_4.1.1_extension",
  srcs = ["@io.dropwizard.metrics_metrics-jmx_4.1.1//file"],
  outs = ["@maven//:io.dropwizard.metrics/metrics-jmx-4.1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "io.dropwizard.metrics_metrics-jmx_4.1.1",
  jars = ["@maven//:io.dropwizard.metrics/metrics-jmx-4.1.1.jar"],
  deps = ["io.dropwizard.metrics_metrics-core_4.1.1", "org.slf4j_slf4j-api_1.7.30"],
  exports = ["io.dropwizard.metrics_metrics-core_4.1.1", "org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=io.dropwizard.metrics:metrics-jmx", "jvm_version=4.1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.fasterxml.jackson.core_jackson-databind_2.10.0_extension",
  srcs = ["@com.fasterxml.jackson.core_jackson-databind_2.10.0//file"],
  outs = ["@maven//:com.fasterxml.jackson.core/jackson-databind-2.10.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.fasterxml.jackson.core_jackson-databind_2.10.0",
  jars = ["@maven//:com.fasterxml.jackson.core/jackson-databind-2.10.0.jar"],
  deps = ["com.fasterxml.jackson.core_jackson-annotations_2.10.0", "com.fasterxml.jackson.core_jackson-core_2.10.0"],
  exports = ["com.fasterxml.jackson.core_jackson-annotations_2.10.0", "com.fasterxml.jackson.core_jackson-core_2.10.0"],
  tags = ["jvm_module=com.fasterxml.jackson.core:jackson-databind", "jvm_version=2.10.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.fasterxml.jackson.module_jackson-module-scala_2.12_2.10.0_extension",
  srcs = ["@com.fasterxml.jackson.module_jackson-module-scala_2.12_2.10.0//file"],
  outs = ["@maven//:com.fasterxml.jackson.module/jackson-module-scala_2.12-2.10.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.fasterxml.jackson.module_jackson-module-scala_2.12_2.10.0",
  jars = ["@maven//:com.fasterxml.jackson.module/jackson-module-scala_2.12-2.10.0.jar"],
  deps = [
    "org.scala-lang_scala-library_2.12.10", "com.fasterxml.jackson.core_jackson-core_2.10.0",
    "com.fasterxml.jackson.core_jackson-annotations_2.10.0", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "com.fasterxml.jackson.module_jackson-module-paranamer_2.10.0"
  ],
  exports = [
    "org.scala-lang_scala-library_2.12.10", "com.fasterxml.jackson.core_jackson-core_2.10.0",
    "com.fasterxml.jackson.core_jackson-annotations_2.10.0", "com.fasterxml.jackson.core_jackson-databind_2.10.0",
    "com.fasterxml.jackson.module_jackson-module-paranamer_2.10.0"
  ],
  tags = ["jvm_module=com.fasterxml.jackson.module:jackson-module-scala_2.12", "jvm_version=2.10.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.ivy_ivy_2.4.0_extension",
  srcs = ["@org.apache.ivy_ivy_2.4.0//file"],
  outs = ["@maven//:org.apache.ivy/ivy-2.4.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.ivy_ivy_2.4.0",
  jars = ["@maven//:org.apache.ivy/ivy-2.4.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.ivy:ivy", "jvm_version=2.4.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "oro_oro_2.0.8_extension",
  srcs = ["@oro_oro_2.0.8//file"],
  outs = ["@maven//:oro/oro-2.0.8.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "oro_oro_2.0.8",
  jars = ["@maven//:oro/oro-2.0.8.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=oro:oro", "jvm_version=2.0.8"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "net.razorvine_pyrolite_4.30_extension",
  srcs = ["@net.razorvine_pyrolite_4.30//file"],
  outs = ["@maven//:net.razorvine/pyrolite-4.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "net.razorvine_pyrolite_4.30",
  jars = ["@maven//:net.razorvine/pyrolite-4.30.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=net.razorvine:pyrolite", "jvm_version=4.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "net.sf.py4j_py4j_0.10.9_extension",
  srcs = ["@net.sf.py4j_py4j_0.10.9//file"],
  outs = ["@maven//:net.sf.py4j/py4j-0.10.9.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "net.sf.py4j_py4j_0.10.9",
  jars = ["@maven//:net.sf.py4j/py4j-0.10.9.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=net.sf.py4j:py4j", "jvm_version=0.10.9"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.spark_spark-tags_2.12_3.0.1_extension",
  srcs = ["@org.apache.spark_spark-tags_2.12_3.0.1//file"],
  outs = ["@maven//:org.apache.spark/spark-tags_2.12-3.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.spark_spark-tags_2.12_3.0.1",
  jars = ["@maven//:org.apache.spark/spark-tags_2.12-3.0.1.jar"],
  deps = ["org.scala-lang_scala-library_2.12.10", "org.spark-project.spark_unused_1.0.0"],
  exports = ["org.scala-lang_scala-library_2.12.10", "org.spark-project.spark_unused_1.0.0"],
  tags = ["jvm_module=org.apache.spark:spark-tags_2.12", "jvm_version=3.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.commons_commons-crypto_1.0.0_extension",
  srcs = ["@org.apache.commons_commons-crypto_1.0.0//file"],
  outs = ["@maven//:org.apache.commons/commons-crypto-1.0.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.commons_commons-crypto_1.0.0",
  jars = ["@maven//:org.apache.commons/commons-crypto-1.0.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.commons:commons-crypto", "jvm_version=1.0.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.spark-project.spark_unused_1.0.0_extension",
  srcs = ["@org.spark-project.spark_unused_1.0.0//file"],
  outs = ["@maven//:org.spark-project.spark/unused-1.0.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.spark-project.spark_unused_1.0.0",
  jars = ["@maven//:org.spark-project.spark/unused-1.0.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.spark-project.spark:unused", "jvm_version=1.0.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.jackson_jackson-core-asl_1.9.13_extension",
  srcs = ["@org.codehaus.jackson_jackson-core-asl_1.9.13//file"],
  outs = ["@maven//:org.codehaus.jackson/jackson-core-asl-1.9.13.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.jackson_jackson-core-asl_1.9.13",
  jars = ["@maven//:org.codehaus.jackson/jackson-core-asl-1.9.13.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.codehaus.jackson:jackson-core-asl", "jvm_version=1.9.13"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.jackson_jackson-mapper-asl_1.9.13_extension",
  srcs = ["@org.codehaus.jackson_jackson-mapper-asl_1.9.13//file"],
  outs = ["@maven//:org.codehaus.jackson/jackson-mapper-asl-1.9.13.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.jackson_jackson-mapper-asl_1.9.13",
  jars = ["@maven//:org.codehaus.jackson/jackson-mapper-asl-1.9.13.jar"],
  deps = ["org.codehaus.jackson_jackson-core-asl_1.9.13"],
  exports = ["org.codehaus.jackson_jackson-core-asl_1.9.13"],
  tags = ["jvm_module=org.codehaus.jackson:jackson-mapper-asl", "jvm_version=1.9.13"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.commons_commons-compress_1.8.1_extension",
  srcs = ["@org.apache.commons_commons-compress_1.8.1//file"],
  outs = ["@maven//:org.apache.commons/commons-compress-1.8.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.commons_commons-compress_1.8.1",
  jars = ["@maven//:org.apache.commons/commons-compress-1.8.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.commons:commons-compress", "jvm_version=1.8.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.tukaani_xz_1.5_extension",
  srcs = ["@org.tukaani_xz_1.5//file"],
  outs = ["@maven//:org.tukaani/xz-1.5.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.tukaani_xz_1.5",
  jars = ["@maven//:org.tukaani/xz-1.5.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.tukaani:xz", "jvm_version=1.5"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.avro_avro-ipc_1.8.2_extension",
  srcs = ["@org.apache.avro_avro-ipc_1.8.2//file"],
  outs = ["@maven//:org.apache.avro/avro-ipc-1.8.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.avro_avro-ipc_1.8.2",
  jars = ["@maven//:org.apache.avro/avro-ipc-1.8.2.jar"],
  deps = [
    "org.apache.avro_avro_1.8.2", "org.codehaus.jackson_jackson-core-asl_1.9.13",
    "org.codehaus.jackson_jackson-mapper-asl_1.9.13", "org.slf4j_slf4j-api_1.7.30"
  ],
  exports = [
    "org.apache.avro_avro_1.8.2", "org.codehaus.jackson_jackson-core-asl_1.9.13",
    "org.codehaus.jackson_jackson-mapper-asl_1.9.13", "org.slf4j_slf4j-api_1.7.30"
  ],
  tags = ["jvm_module=org.apache.avro:avro-ipc", "jvm_version=1.8.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-codec_commons-codec_1.9_extension",
  srcs = ["@commons-codec_commons-codec_1.9//file"],
  outs = ["@maven//:commons-codec/commons-codec-1.9.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-codec_commons-codec_1.9",
  jars = ["@maven//:commons-codec/commons-codec-1.9.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=commons-codec:commons-codec", "jvm_version=1.9"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.esotericsoftware_kryo-shaded_4.0.2_extension",
  srcs = ["@com.esotericsoftware_kryo-shaded_4.0.2//file"],
  outs = ["@maven//:com.esotericsoftware/kryo-shaded-4.0.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.esotericsoftware_kryo-shaded_4.0.2",
  jars = ["@maven//:com.esotericsoftware/kryo-shaded-4.0.2.jar"],
  deps = ["com.esotericsoftware_minlog_1.3.0", "org.objenesis_objenesis_2.5.1"],
  exports = ["com.esotericsoftware_minlog_1.3.0", "org.objenesis_objenesis_2.5.1"],
  tags = ["jvm_module=com.esotericsoftware:kryo-shaded", "jvm_version=4.0.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-common_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-common_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-common-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-common_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-common-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-annotations_2.7.4", "com.google.guava_guava_16.0.1", "commons-cli_commons-cli_1.2",
    "org.apache.commons_commons-math3_3.4.1", "xmlenc_xmlenc_0.52", "commons-httpclient_commons-httpclient_3.1",
    "commons-codec_commons-codec_1.9", "commons-io_commons-io_2.4", "commons-net_commons-net_3.1",
    "commons-collections_commons-collections_3.2.2", "org.mortbay.jetty_jetty-sslengine_6.1.26",
    "javax.servlet.jsp_jsp-api_2.1", "log4j_log4j_1.2.17", "commons-lang_commons-lang_2.6",
    "commons-configuration_commons-configuration_1.6", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30",
    "org.codehaus.jackson_jackson-core-asl_1.9.13", "org.apache.avro_avro_1.8.2",
    "com.google.protobuf_protobuf-java_2.5.0", "com.google.code.gson_gson_2.2.4", "org.apache.hadoop_hadoop-auth_2.7.4",
    "org.apache.curator_curator-client_2.7.1", "org.apache.curator_curator-recipes_2.7.1",
    "com.google.code.findbugs_jsr305_3.0.2", "org.apache.htrace_htrace-core_3.1.0-incubating",
    "org.apache.zookeeper_zookeeper_3.4.14", "org.apache.commons_commons-compress_1.8.1"
  ],
  exports = [
    "org.apache.hadoop_hadoop-annotations_2.7.4", "com.google.guava_guava_16.0.1", "commons-cli_commons-cli_1.2",
    "org.apache.commons_commons-math3_3.4.1", "xmlenc_xmlenc_0.52", "commons-httpclient_commons-httpclient_3.1",
    "commons-codec_commons-codec_1.9", "commons-io_commons-io_2.4", "commons-net_commons-net_3.1",
    "commons-collections_commons-collections_3.2.2", "org.mortbay.jetty_jetty-sslengine_6.1.26",
    "javax.servlet.jsp_jsp-api_2.1", "log4j_log4j_1.2.17", "commons-lang_commons-lang_2.6",
    "commons-configuration_commons-configuration_1.6", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30",
    "org.codehaus.jackson_jackson-core-asl_1.9.13", "org.apache.avro_avro_1.8.2",
    "com.google.protobuf_protobuf-java_2.5.0", "com.google.code.gson_gson_2.2.4", "org.apache.hadoop_hadoop-auth_2.7.4",
    "org.apache.curator_curator-client_2.7.1", "org.apache.curator_curator-recipes_2.7.1",
    "com.google.code.findbugs_jsr305_3.0.2", "org.apache.htrace_htrace-core_3.1.0-incubating",
    "org.apache.zookeeper_zookeeper_3.4.14", "org.apache.commons_commons-compress_1.8.1"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-common", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-hdfs_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-hdfs_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-hdfs-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-hdfs_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-hdfs-2.7.4.jar"],
  deps = [
    "com.google.guava_guava_16.0.1", "org.mortbay.jetty_jetty-util_6.1.26", "commons-cli_commons-cli_1.2",
    "commons-codec_commons-codec_1.9", "commons-io_commons-io_2.4", "commons-lang_commons-lang_2.6",
    "log4j_log4j_1.2.17", "com.google.protobuf_protobuf-java_2.5.0", "org.codehaus.jackson_jackson-core-asl_1.9.13",
    "xmlenc_xmlenc_0.52", "io.netty_netty-all_4.1.47.Final", "xerces_xercesImpl_2.9.1",
    "org.apache.htrace_htrace-core_3.1.0-incubating"
  ],
  exports = [
    "com.google.guava_guava_16.0.1", "org.mortbay.jetty_jetty-util_6.1.26", "commons-cli_commons-cli_1.2",
    "commons-codec_commons-codec_1.9", "commons-io_commons-io_2.4", "commons-lang_commons-lang_2.6",
    "log4j_log4j_1.2.17", "com.google.protobuf_protobuf-java_2.5.0", "org.codehaus.jackson_jackson-core-asl_1.9.13",
    "xmlenc_xmlenc_0.52", "io.netty_netty-all_4.1.47.Final", "xerces_xercesImpl_2.9.1",
    "org.apache.htrace_htrace-core_3.1.0-incubating"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-hdfs", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-mapreduce-client-app_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-mapreduce-client-app_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-app-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-mapreduce-client-app_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-app-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4", "org.apache.hadoop_hadoop-mapreduce-client-shuffle_2.7.4",
    "com.google.protobuf_protobuf-java_2.5.0", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  exports = [
    "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4", "org.apache.hadoop_hadoop-mapreduce-client-shuffle_2.7.4",
    "com.google.protobuf_protobuf-java_2.5.0", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-mapreduce-client-app", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-yarn-api_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-yarn-api_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-yarn-api-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-yarn-api_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-yarn-api-2.7.4.jar"],
  deps = ["commons-lang_commons-lang_2.6", "com.google.guava_guava_16.0.1", "com.google.protobuf_protobuf-java_2.5.0"],
  exports = [
    "commons-lang_commons-lang_2.6", "com.google.guava_guava_16.0.1", "com.google.protobuf_protobuf-java_2.5.0"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-yarn-api", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-mapreduce-client-core_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-mapreduce-client-core_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-core-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-mapreduce-client-core_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-core-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-yarn-common_2.7.4", "com.google.protobuf_protobuf-java_2.5.0",
    "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  exports = [
    "org.apache.hadoop_hadoop-yarn-common_2.7.4", "com.google.protobuf_protobuf-java_2.5.0",
    "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-mapreduce-client-core", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-mapreduce-client-jobclient_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-mapreduce-client-jobclient_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-jobclient-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-mapreduce-client-jobclient_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-jobclient-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4", "org.apache.hadoop_hadoop-mapreduce-client-shuffle_2.7.4",
    "com.google.protobuf_protobuf-java_2.5.0", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  exports = [
    "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4", "org.apache.hadoop_hadoop-mapreduce-client-shuffle_2.7.4",
    "com.google.protobuf_protobuf-java_2.5.0", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-mapreduce-client-jobclient", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-annotations_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-annotations_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-annotations-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-annotations_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-annotations-2.7.4.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.hadoop:hadoop-annotations", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.fusesource.leveldbjni_leveldbjni-all_1.8_extension",
  srcs = ["@org.fusesource.leveldbjni_leveldbjni-all_1.8//file"],
  outs = ["@maven//:org.fusesource.leveldbjni/leveldbjni-all-1.8.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.fusesource.leveldbjni_leveldbjni-all_1.8",
  jars = ["@maven//:org.fusesource.leveldbjni/leveldbjni-all-1.8.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.fusesource.leveldbjni:leveldbjni-all", "jvm_version=1.8"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.fasterxml.jackson.core_jackson-core_2.10.0_extension",
  srcs = ["@com.fasterxml.jackson.core_jackson-core_2.10.0//file"],
  outs = ["@maven//:com.fasterxml.jackson.core/jackson-core-2.10.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.fasterxml.jackson.core_jackson-core_2.10.0",
  jars = ["@maven//:com.fasterxml.jackson.core/jackson-core-2.10.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.fasterxml.jackson.core:jackson-core", "jvm_version=2.10.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.fasterxml.jackson.core_jackson-annotations_2.10.0_extension",
  srcs = ["@com.fasterxml.jackson.core_jackson-annotations_2.10.0//file"],
  outs = ["@maven//:com.fasterxml.jackson.core/jackson-annotations-2.10.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.fasterxml.jackson.core_jackson-annotations_2.10.0",
  jars = ["@maven//:com.fasterxml.jackson.core/jackson-annotations-2.10.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.fasterxml.jackson.core:jackson-annotations", "jvm_version=2.10.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.curator_curator-framework_2.7.1_extension",
  srcs = ["@org.apache.curator_curator-framework_2.7.1//file"],
  outs = ["@maven//:org.apache.curator/curator-framework-2.7.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.curator_curator-framework_2.7.1",
  jars = ["@maven//:org.apache.curator/curator-framework-2.7.1.jar"],
  deps = [
    "org.apache.curator_curator-client_2.7.1", "org.apache.zookeeper_zookeeper_3.4.14", "com.google.guava_guava_16.0.1"
  ],
  exports = [
    "org.apache.curator_curator-client_2.7.1", "org.apache.zookeeper_zookeeper_3.4.14", "com.google.guava_guava_16.0.1"
  ],
  tags = ["jvm_module=org.apache.curator:curator-framework", "jvm_version=2.7.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.yetus_audience-annotations_0.5.0_extension",
  srcs = ["@org.apache.yetus_audience-annotations_0.5.0//file"],
  outs = ["@maven//:org.apache.yetus/audience-annotations-0.5.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.yetus_audience-annotations_0.5.0",
  jars = ["@maven//:org.apache.yetus/audience-annotations-0.5.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.yetus:audience-annotations", "jvm_version=0.5.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.roaringbitmap_shims_0.7.45_extension",
  srcs = ["@org.roaringbitmap_shims_0.7.45//file"],
  outs = ["@maven//:org.roaringbitmap/shims-0.7.45.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.roaringbitmap_shims_0.7.45",
  jars = ["@maven//:org.roaringbitmap/shims-0.7.45.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.roaringbitmap:shims", "jvm_version=0.7.45"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.json4s_json4s-core_2.12_3.6.6_extension",
  srcs = ["@org.json4s_json4s-core_2.12_3.6.6//file"],
  outs = ["@maven//:org.json4s/json4s-core_2.12-3.6.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.json4s_json4s-core_2.12_3.6.6",
  jars = ["@maven//:org.json4s/json4s-core_2.12-3.6.6.jar"],
  deps = [
    "org.scala-lang_scala-library_2.12.10", "org.json4s_json4s-ast_2.12_3.6.6", "org.json4s_json4s-scalap_2.12_3.6.6",
    "com.thoughtworks.paranamer_paranamer_2.8"
  ],
  exports = [
    "org.scala-lang_scala-library_2.12.10", "org.json4s_json4s-ast_2.12_3.6.6", "org.json4s_json4s-scalap_2.12_3.6.6",
    "com.thoughtworks.paranamer_paranamer_2.8"
  ],
  tags = ["jvm_module=org.json4s:json4s-core_2.12", "jvm_version=3.6.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6_extension",
  srcs = ["@jakarta.ws.rs_jakarta.ws.rs-api_2.1.6//file"],
  outs = ["@maven//:jakarta.ws.rs/jakarta.ws.rs-api-2.1.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "jakarta.ws.rs_jakarta.ws.rs-api_2.1.6",
  jars = ["@maven//:jakarta.ws.rs/jakarta.ws.rs-api-2.1.6.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=jakarta.ws.rs:jakarta.ws.rs-api", "jvm_version=2.1.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.hk2.external_jakarta.inject_2.6.1_extension",
  srcs = ["@org.glassfish.hk2.external_jakarta.inject_2.6.1//file"],
  outs = ["@maven//:org.glassfish.hk2.external/jakarta.inject-2.6.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.hk2.external_jakarta.inject_2.6.1",
  jars = ["@maven//:org.glassfish.hk2.external/jakarta.inject-2.6.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.glassfish.hk2.external:jakarta.inject", "jvm_version=2.6.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "jakarta.annotation_jakarta.annotation-api_1.3.5_extension",
  srcs = ["@jakarta.annotation_jakarta.annotation-api_1.3.5//file"],
  outs = ["@maven//:jakarta.annotation/jakarta.annotation-api-1.3.5.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "jakarta.annotation_jakarta.annotation-api_1.3.5",
  jars = ["@maven//:jakarta.annotation/jakarta.annotation-api-1.3.5.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=jakarta.annotation:jakarta.annotation-api", "jvm_version=1.3.5"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.hk2_osgi-resource-locator_1.0.3_extension",
  srcs = ["@org.glassfish.hk2_osgi-resource-locator_1.0.3//file"],
  outs = ["@maven//:org.glassfish.hk2/osgi-resource-locator-1.0.3.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.hk2_osgi-resource-locator_1.0.3",
  jars = ["@maven//:org.glassfish.hk2/osgi-resource-locator-1.0.3.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.glassfish.hk2:osgi-resource-locator", "jvm_version=1.0.3"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.jersey.media_jersey-media-jaxb_2.30_extension",
  srcs = ["@org.glassfish.jersey.media_jersey-media-jaxb_2.30//file"],
  outs = ["@maven//:org.glassfish.jersey.media/jersey-media-jaxb-2.30.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.jersey.media_jersey-media-jaxb_2.30",
  jars = ["@maven//:org.glassfish.jersey.media/jersey-media-jaxb-2.30.jar"],
  deps = [
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.hk2.external_jakarta.inject_2.6.1",
    "org.glassfish.hk2_osgi-resource-locator_1.0.3"
  ],
  exports = [
    "org.glassfish.jersey.core_jersey-common_2.30", "org.glassfish.hk2.external_jakarta.inject_2.6.1",
    "org.glassfish.hk2_osgi-resource-locator_1.0.3"
  ],
  tags = ["jvm_module=org.glassfish.jersey.media:jersey-media-jaxb", "jvm_version=2.30"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "jakarta.validation_jakarta.validation-api_2.0.2_extension",
  srcs = ["@jakarta.validation_jakarta.validation-api_2.0.2//file"],
  outs = ["@maven//:jakarta.validation/jakarta.validation-api-2.0.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "jakarta.validation_jakarta.validation-api_2.0.2",
  jars = ["@maven//:jakarta.validation/jakarta.validation-api-2.0.2.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=jakarta.validation:jakarta.validation-api", "jvm_version=2.0.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.hk2_hk2-locator_2.6.1_extension",
  srcs = ["@org.glassfish.hk2_hk2-locator_2.6.1//file"],
  outs = ["@maven//:org.glassfish.hk2/hk2-locator-2.6.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.hk2_hk2-locator_2.6.1",
  jars = ["@maven//:org.glassfish.hk2/hk2-locator-2.6.1.jar"],
  deps = [
    "org.glassfish.hk2.external_jakarta.inject_2.6.1", "org.glassfish.hk2.external_aopalliance-repackaged_2.6.1",
    "org.glassfish.hk2_hk2-api_2.6.1", "org.glassfish.hk2_hk2-utils_2.6.1"
  ],
  exports = [
    "org.glassfish.hk2.external_jakarta.inject_2.6.1", "org.glassfish.hk2.external_aopalliance-repackaged_2.6.1",
    "org.glassfish.hk2_hk2-api_2.6.1", "org.glassfish.hk2_hk2-utils_2.6.1"
  ],
  tags = ["jvm_module=org.glassfish.hk2:hk2-locator", "jvm_version=2.6.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.javassist_javassist_3.25.0-GA_extension",
  srcs = ["@org.javassist_javassist_3.25.0-GA//file"],
  outs = ["@maven//:org.javassist/javassist-3.25.0-GA.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.javassist_javassist_3.25.0-GA",
  jars = ["@maven//:org.javassist/javassist-3.25.0-GA.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.javassist:javassist", "jvm_version=3.25.0-GA"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.fasterxml.jackson.module_jackson-module-paranamer_2.10.0_extension",
  srcs = ["@com.fasterxml.jackson.module_jackson-module-paranamer_2.10.0//file"],
  outs = ["@maven//:com.fasterxml.jackson.module/jackson-module-paranamer-2.10.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.fasterxml.jackson.module_jackson-module-paranamer_2.10.0",
  jars = ["@maven//:com.fasterxml.jackson.module/jackson-module-paranamer-2.10.0.jar"],
  deps = ["com.fasterxml.jackson.core_jackson-databind_2.10.0", "com.thoughtworks.paranamer_paranamer_2.8"],
  exports = ["com.fasterxml.jackson.core_jackson-databind_2.10.0", "com.thoughtworks.paranamer_paranamer_2.8"],
  tags = ["jvm_module=com.fasterxml.jackson.module:jackson-module-paranamer", "jvm_version=2.10.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.esotericsoftware_minlog_1.3.0_extension",
  srcs = ["@com.esotericsoftware_minlog_1.3.0//file"],
  outs = ["@maven//:com.esotericsoftware/minlog-1.3.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.esotericsoftware_minlog_1.3.0",
  jars = ["@maven//:com.esotericsoftware/minlog-1.3.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.esotericsoftware:minlog", "jvm_version=1.3.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.objenesis_objenesis_2.5.1_extension",
  srcs = ["@org.objenesis_objenesis_2.5.1//file"],
  outs = ["@maven//:org.objenesis/objenesis-2.5.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.objenesis_objenesis_2.5.1",
  jars = ["@maven//:org.objenesis/objenesis-2.5.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.objenesis:objenesis", "jvm_version=2.5.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-cli_commons-cli_1.2_extension",
  srcs = ["@commons-cli_commons-cli_1.2//file"],
  outs = ["@maven//:commons-cli/commons-cli-1.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-cli_commons-cli_1.2",
  jars = ["@maven//:commons-cli/commons-cli-1.2.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=commons-cli:commons-cli", "jvm_version=1.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "xmlenc_xmlenc_0.52_extension",
  srcs = ["@xmlenc_xmlenc_0.52//file"],
  outs = ["@maven//:xmlenc/xmlenc-0.52.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "xmlenc_xmlenc_0.52",
  jars = ["@maven//:xmlenc/xmlenc-0.52.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=xmlenc:xmlenc", "jvm_version=0.52"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-httpclient_commons-httpclient_3.1_extension",
  srcs = ["@commons-httpclient_commons-httpclient_3.1//file"],
  outs = ["@maven//:commons-httpclient/commons-httpclient-3.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-httpclient_commons-httpclient_3.1",
  jars = ["@maven//:commons-httpclient/commons-httpclient-3.1.jar"],
  deps = ["commons-codec_commons-codec_1.9"],
  exports = ["commons-codec_commons-codec_1.9"],
  tags = ["jvm_module=commons-httpclient:commons-httpclient", "jvm_version=3.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-io_commons-io_2.4_extension",
  srcs = ["@commons-io_commons-io_2.4//file"],
  outs = ["@maven//:commons-io/commons-io-2.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-io_commons-io_2.4",
  jars = ["@maven//:commons-io/commons-io-2.4.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=commons-io:commons-io", "jvm_version=2.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-collections_commons-collections_3.2.2_extension",
  srcs = ["@commons-collections_commons-collections_3.2.2//file"],
  outs = ["@maven//:commons-collections/commons-collections-3.2.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-collections_commons-collections_3.2.2",
  jars = ["@maven//:commons-collections/commons-collections-3.2.2.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=commons-collections:commons-collections", "jvm_version=3.2.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.mortbay.jetty_jetty-sslengine_6.1.26_extension",
  srcs = ["@org.mortbay.jetty_jetty-sslengine_6.1.26//file"],
  outs = ["@maven//:org.mortbay.jetty/jetty-sslengine-6.1.26.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.mortbay.jetty_jetty-sslengine_6.1.26",
  jars = ["@maven//:org.mortbay.jetty/jetty-sslengine-6.1.26.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.mortbay.jetty:jetty-sslengine", "jvm_version=6.1.26"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "javax.servlet.jsp_jsp-api_2.1_extension",
  srcs = ["@javax.servlet.jsp_jsp-api_2.1//file"],
  outs = ["@maven//:javax.servlet.jsp/jsp-api-2.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "javax.servlet.jsp_jsp-api_2.1",
  jars = ["@maven//:javax.servlet.jsp/jsp-api-2.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=javax.servlet.jsp:jsp-api", "jvm_version=2.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-lang_commons-lang_2.6_extension",
  srcs = ["@commons-lang_commons-lang_2.6//file"],
  outs = ["@maven//:commons-lang/commons-lang-2.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-lang_commons-lang_2.6",
  jars = ["@maven//:commons-lang/commons-lang-2.6.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=commons-lang:commons-lang", "jvm_version=2.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-configuration_commons-configuration_1.6_extension",
  srcs = ["@commons-configuration_commons-configuration_1.6//file"],
  outs = ["@maven//:commons-configuration/commons-configuration-1.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-configuration_commons-configuration_1.6",
  jars = ["@maven//:commons-configuration/commons-configuration-1.6.jar"],
  deps = [
    "commons-collections_commons-collections_3.2.2", "commons-lang_commons-lang_2.6",
    "commons-digester_commons-digester_1.8"
  ],
  exports = [
    "commons-collections_commons-collections_3.2.2", "commons-lang_commons-lang_2.6",
    "commons-digester_commons-digester_1.8"
  ],
  tags = ["jvm_module=commons-configuration:commons-configuration", "jvm_version=1.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.code.gson_gson_2.2.4_extension",
  srcs = ["@com.google.code.gson_gson_2.2.4//file"],
  outs = ["@maven//:com.google.code.gson/gson-2.2.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.code.gson_gson_2.2.4",
  jars = ["@maven//:com.google.code.gson/gson-2.2.4.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.google.code.gson:gson", "jvm_version=2.2.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-auth_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-auth_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-auth-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-auth_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-auth-2.7.4.jar"],
  deps = [
    "org.slf4j_slf4j-api_1.7.30", "commons-codec_commons-codec_1.9", "log4j_log4j_1.2.17",
    "org.slf4j_slf4j-log4j12_1.7.30", "org.apache.httpcomponents_httpclient_4.2.5",
    "org.apache.directory.server_apacheds-kerberos-codec_2.0.0-M15", "org.apache.zookeeper_zookeeper_3.4.14",
    "org.apache.curator_curator-framework_2.7.1"
  ],
  exports = [
    "org.slf4j_slf4j-api_1.7.30", "commons-codec_commons-codec_1.9", "log4j_log4j_1.2.17",
    "org.slf4j_slf4j-log4j12_1.7.30", "org.apache.httpcomponents_httpclient_4.2.5",
    "org.apache.directory.server_apacheds-kerberos-codec_2.0.0-M15", "org.apache.zookeeper_zookeeper_3.4.14",
    "org.apache.curator_curator-framework_2.7.1"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-auth", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.curator_curator-client_2.7.1_extension",
  srcs = ["@org.apache.curator_curator-client_2.7.1//file"],
  outs = ["@maven//:org.apache.curator/curator-client-2.7.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.curator_curator-client_2.7.1",
  jars = ["@maven//:org.apache.curator/curator-client-2.7.1.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30", "org.apache.zookeeper_zookeeper_3.4.14", "com.google.guava_guava_16.0.1"],
  exports = ["org.slf4j_slf4j-api_1.7.30", "org.apache.zookeeper_zookeeper_3.4.14", "com.google.guava_guava_16.0.1"],
  tags = ["jvm_module=org.apache.curator:curator-client", "jvm_version=2.7.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.htrace_htrace-core_3.1.0-incubating_extension",
  srcs = ["@org.apache.htrace_htrace-core_3.1.0-incubating//file"],
  outs = ["@maven//:org.apache.htrace/htrace-core-3.1.0-incubating.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.htrace_htrace-core_3.1.0-incubating",
  jars = ["@maven//:org.apache.htrace/htrace-core-3.1.0-incubating.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.htrace:htrace-core", "jvm_version=3.1.0-incubating"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.mortbay.jetty_jetty-util_6.1.26_extension",
  srcs = ["@org.mortbay.jetty_jetty-util_6.1.26//file"],
  outs = ["@maven//:org.mortbay.jetty/jetty-util-6.1.26.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.mortbay.jetty_jetty-util_6.1.26",
  jars = ["@maven//:org.mortbay.jetty/jetty-util-6.1.26.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.mortbay.jetty:jetty-util", "jvm_version=6.1.26"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "xerces_xercesImpl_2.9.1_extension",
  srcs = ["@xerces_xercesImpl_2.9.1//file"],
  outs = ["@maven//:xerces/xercesImpl-2.9.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "xerces_xercesImpl_2.9.1",
  jars = ["@maven//:xerces/xercesImpl-2.9.1.jar"],
  deps = ["xml-apis_xml-apis_1.3.04"],
  exports = ["xml-apis_xml-apis_1.3.04"],
  tags = ["jvm_module=xerces:xercesImpl", "jvm_version=2.9.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-common-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-common-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-yarn-common_2.7.4", "org.apache.hadoop_hadoop-yarn-client_2.7.4",
    "org.apache.hadoop_hadoop-mapreduce-client-core_2.7.4", "org.apache.hadoop_hadoop-yarn-server-common_2.7.4",
    "com.google.protobuf_protobuf-java_2.5.0", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  exports = [
    "org.apache.hadoop_hadoop-yarn-common_2.7.4", "org.apache.hadoop_hadoop-yarn-client_2.7.4",
    "org.apache.hadoop_hadoop-mapreduce-client-core_2.7.4", "org.apache.hadoop_hadoop-yarn-server-common_2.7.4",
    "com.google.protobuf_protobuf-java_2.5.0", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-mapreduce-client-common", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-mapreduce-client-shuffle_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-mapreduce-client-shuffle_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-shuffle-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-mapreduce-client-shuffle_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-mapreduce-client-shuffle-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-yarn-server-common_2.7.4", "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4",
    "com.google.protobuf_protobuf-java_2.5.0", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  exports = [
    "org.apache.hadoop_hadoop-yarn-server-common_2.7.4", "org.apache.hadoop_hadoop-mapreduce-client-common_2.7.4",
    "com.google.protobuf_protobuf-java_2.5.0", "org.slf4j_slf4j-api_1.7.30", "org.slf4j_slf4j-log4j12_1.7.30"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-mapreduce-client-shuffle", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-yarn-common_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-yarn-common_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-yarn-common-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-yarn-common_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-yarn-common-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-yarn-api_2.7.4", "javax.xml.bind_jaxb-api_2.2.2",
    "org.apache.commons_commons-compress_1.8.1", "commons-lang_commons-lang_2.6", "commons-codec_commons-codec_1.9",
    "org.mortbay.jetty_jetty-util_6.1.26", "org.codehaus.jackson_jackson-core-asl_1.9.13",
    "org.codehaus.jackson_jackson-jaxrs_1.9.13", "org.codehaus.jackson_jackson-xc_1.9.13",
    "com.google.guava_guava_16.0.1", "commons-cli_commons-cli_1.2", "org.slf4j_slf4j-api_1.7.30",
    "com.google.protobuf_protobuf-java_2.5.0", "commons-io_commons-io_2.4", "log4j_log4j_1.2.17"
  ],
  exports = [
    "org.apache.hadoop_hadoop-yarn-api_2.7.4", "javax.xml.bind_jaxb-api_2.2.2",
    "org.apache.commons_commons-compress_1.8.1", "commons-lang_commons-lang_2.6", "commons-codec_commons-codec_1.9",
    "org.mortbay.jetty_jetty-util_6.1.26", "org.codehaus.jackson_jackson-core-asl_1.9.13",
    "org.codehaus.jackson_jackson-jaxrs_1.9.13", "org.codehaus.jackson_jackson-xc_1.9.13",
    "com.google.guava_guava_16.0.1", "commons-cli_commons-cli_1.2", "org.slf4j_slf4j-api_1.7.30",
    "com.google.protobuf_protobuf-java_2.5.0", "commons-io_commons-io_2.4", "log4j_log4j_1.2.17"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-yarn-common", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.github.spotbugs_spotbugs-annotations_3.1.9_extension",
  srcs = ["@com.github.spotbugs_spotbugs-annotations_3.1.9//file"],
  outs = ["@maven//:com.github.spotbugs/spotbugs-annotations-3.1.9.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.github.spotbugs_spotbugs-annotations_3.1.9",
  jars = ["@maven//:com.github.spotbugs/spotbugs-annotations-3.1.9.jar"],
  deps = ["com.google.code.findbugs_jsr305_3.0.2"],
  exports = ["com.google.code.findbugs_jsr305_3.0.2"],
  tags = ["jvm_module=com.github.spotbugs:spotbugs-annotations", "jvm_version=3.1.9"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "io.netty_netty_3.10.6.Final_extension",
  srcs = ["@io.netty_netty_3.10.6.Final//file"],
  outs = ["@maven//:io.netty/netty-3.10.6.Final.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "io.netty_netty_3.10.6.Final",
  jars = ["@maven//:io.netty/netty-3.10.6.Final.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=io.netty:netty", "jvm_version=3.10.6.Final"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.json4s_json4s-ast_2.12_3.6.6_extension",
  srcs = ["@org.json4s_json4s-ast_2.12_3.6.6//file"],
  outs = ["@maven//:org.json4s/json4s-ast_2.12-3.6.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.json4s_json4s-ast_2.12_3.6.6",
  jars = ["@maven//:org.json4s/json4s-ast_2.12-3.6.6.jar"],
  deps = ["org.scala-lang_scala-library_2.12.10"],
  exports = ["org.scala-lang_scala-library_2.12.10"],
  tags = ["jvm_module=org.json4s:json4s-ast_2.12", "jvm_version=3.6.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.json4s_json4s-scalap_2.12_3.6.6_extension",
  srcs = ["@org.json4s_json4s-scalap_2.12_3.6.6//file"],
  outs = ["@maven//:org.json4s/json4s-scalap_2.12-3.6.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.json4s_json4s-scalap_2.12_3.6.6",
  jars = ["@maven//:org.json4s/json4s-scalap_2.12-3.6.6.jar"],
  deps = ["org.scala-lang_scala-library_2.12.10"],
  exports = ["org.scala-lang_scala-library_2.12.10"],
  tags = ["jvm_module=org.json4s:json4s-scalap_2.12", "jvm_version=3.6.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.hk2.external_aopalliance-repackaged_2.6.1_extension",
  srcs = ["@org.glassfish.hk2.external_aopalliance-repackaged_2.6.1//file"],
  outs = ["@maven//:org.glassfish.hk2.external/aopalliance-repackaged-2.6.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.hk2.external_aopalliance-repackaged_2.6.1",
  jars = ["@maven//:org.glassfish.hk2.external/aopalliance-repackaged-2.6.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.glassfish.hk2.external:aopalliance-repackaged", "jvm_version=2.6.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.hk2_hk2-api_2.6.1_extension",
  srcs = ["@org.glassfish.hk2_hk2-api_2.6.1//file"],
  outs = ["@maven//:org.glassfish.hk2/hk2-api-2.6.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.hk2_hk2-api_2.6.1",
  jars = ["@maven//:org.glassfish.hk2/hk2-api-2.6.1.jar"],
  deps = [
    "org.glassfish.hk2.external_jakarta.inject_2.6.1", "org.glassfish.hk2_hk2-utils_2.6.1",
    "org.glassfish.hk2.external_aopalliance-repackaged_2.6.1"
  ],
  exports = [
    "org.glassfish.hk2.external_jakarta.inject_2.6.1", "org.glassfish.hk2_hk2-utils_2.6.1",
    "org.glassfish.hk2.external_aopalliance-repackaged_2.6.1"
  ],
  tags = ["jvm_module=org.glassfish.hk2:hk2-api", "jvm_version=2.6.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.glassfish.hk2_hk2-utils_2.6.1_extension",
  srcs = ["@org.glassfish.hk2_hk2-utils_2.6.1//file"],
  outs = ["@maven//:org.glassfish.hk2/hk2-utils-2.6.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.glassfish.hk2_hk2-utils_2.6.1",
  jars = ["@maven//:org.glassfish.hk2/hk2-utils-2.6.1.jar"],
  deps = ["org.glassfish.hk2.external_jakarta.inject_2.6.1"],
  exports = ["org.glassfish.hk2.external_jakarta.inject_2.6.1"],
  tags = ["jvm_module=org.glassfish.hk2:hk2-utils", "jvm_version=2.6.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-digester_commons-digester_1.8_extension",
  srcs = ["@commons-digester_commons-digester_1.8//file"],
  outs = ["@maven//:commons-digester/commons-digester-1.8.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-digester_commons-digester_1.8",
  jars = ["@maven//:commons-digester/commons-digester-1.8.jar"],
  deps = ["commons-beanutils_commons-beanutils_1.7.0"],
  exports = ["commons-beanutils_commons-beanutils_1.7.0"],
  tags = ["jvm_module=commons-digester:commons-digester", "jvm_version=1.8"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.httpcomponents_httpclient_4.2.5_extension",
  srcs = ["@org.apache.httpcomponents_httpclient_4.2.5//file"],
  outs = ["@maven//:org.apache.httpcomponents/httpclient-4.2.5.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.httpcomponents_httpclient_4.2.5",
  jars = ["@maven//:org.apache.httpcomponents/httpclient-4.2.5.jar"],
  deps = ["org.apache.httpcomponents_httpcore_4.2.4", "commons-codec_commons-codec_1.9"],
  exports = ["org.apache.httpcomponents_httpcore_4.2.4", "commons-codec_commons-codec_1.9"],
  tags = ["jvm_module=org.apache.httpcomponents:httpclient", "jvm_version=4.2.5"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.directory.server_apacheds-kerberos-codec_2.0.0-M15_extension",
  srcs = ["@org.apache.directory.server_apacheds-kerberos-codec_2.0.0-M15//file"],
  outs = ["@maven//:org.apache.directory.server/apacheds-kerberos-codec-2.0.0-M15.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.directory.server_apacheds-kerberos-codec_2.0.0-M15",
  jars = ["@maven//:org.apache.directory.server/apacheds-kerberos-codec-2.0.0-M15.jar"],
  deps = [
    "org.apache.directory.server_apacheds-i18n_2.0.0-M15", "org.apache.directory.api_api-asn1-api_1.0.0-M20",
    "org.apache.directory.api_api-util_1.0.0-M20", "org.slf4j_slf4j-api_1.7.30"
  ],
  exports = [
    "org.apache.directory.server_apacheds-i18n_2.0.0-M15", "org.apache.directory.api_api-asn1-api_1.0.0-M20",
    "org.apache.directory.api_api-util_1.0.0-M20", "org.slf4j_slf4j-api_1.7.30"
  ],
  tags = ["jvm_module=org.apache.directory.server:apacheds-kerberos-codec", "jvm_version=2.0.0-M15"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "xml-apis_xml-apis_1.3.04_extension",
  srcs = ["@xml-apis_xml-apis_1.3.04//file"],
  outs = ["@maven//:xml-apis/xml-apis-1.3.04.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "xml-apis_xml-apis_1.3.04",
  jars = ["@maven//:xml-apis/xml-apis-1.3.04.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=xml-apis:xml-apis", "jvm_version=1.3.04"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-yarn-client_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-yarn-client_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-yarn-client-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-yarn-client_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-yarn-client-2.7.4.jar"],
  deps = [
    "com.google.guava_guava_16.0.1", "commons-lang_commons-lang_2.6", "commons-cli_commons-cli_1.2",
    "log4j_log4j_1.2.17", "org.apache.hadoop_hadoop-yarn-api_2.7.4", "org.apache.hadoop_hadoop-yarn-common_2.7.4"
  ],
  exports = [
    "com.google.guava_guava_16.0.1", "commons-lang_commons-lang_2.6", "commons-cli_commons-cli_1.2",
    "log4j_log4j_1.2.17", "org.apache.hadoop_hadoop-yarn-api_2.7.4", "org.apache.hadoop_hadoop-yarn-common_2.7.4"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-yarn-client", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-yarn-server-common_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-yarn-server-common_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-yarn-server-common-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-yarn-server-common_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-yarn-server-common-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-yarn-api_2.7.4", "org.apache.hadoop_hadoop-yarn-common_2.7.4",
    "com.google.guava_guava_16.0.1", "com.google.protobuf_protobuf-java_2.5.0", "org.apache.zookeeper_zookeeper_3.4.14"
  ],
  exports = [
    "org.apache.hadoop_hadoop-yarn-api_2.7.4", "org.apache.hadoop_hadoop-yarn-common_2.7.4",
    "com.google.guava_guava_16.0.1", "com.google.protobuf_protobuf-java_2.5.0", "org.apache.zookeeper_zookeeper_3.4.14"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-yarn-server-common", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "javax.xml.bind_jaxb-api_2.2.2_extension",
  srcs = ["@javax.xml.bind_jaxb-api_2.2.2//file"],
  outs = ["@maven//:javax.xml.bind/jaxb-api-2.2.2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "javax.xml.bind_jaxb-api_2.2.2",
  jars = ["@maven//:javax.xml.bind/jaxb-api-2.2.2.jar"],
  deps = ["javax.xml.stream_stax-api_1.0-2", "javax.activation_activation_1.1.1"],
  exports = ["javax.xml.stream_stax-api_1.0-2", "javax.activation_activation_1.1.1"],
  tags = ["jvm_module=javax.xml.bind:jaxb-api", "jvm_version=2.2.2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.jackson_jackson-jaxrs_1.9.13_extension",
  srcs = ["@org.codehaus.jackson_jackson-jaxrs_1.9.13//file"],
  outs = ["@maven//:org.codehaus.jackson/jackson-jaxrs-1.9.13.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.jackson_jackson-jaxrs_1.9.13",
  jars = ["@maven//:org.codehaus.jackson/jackson-jaxrs-1.9.13.jar"],
  deps = ["org.codehaus.jackson_jackson-core-asl_1.9.13"],
  exports = ["org.codehaus.jackson_jackson-core-asl_1.9.13"],
  tags = ["jvm_module=org.codehaus.jackson:jackson-jaxrs", "jvm_version=1.9.13"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.jackson_jackson-xc_1.9.13_extension",
  srcs = ["@org.codehaus.jackson_jackson-xc_1.9.13//file"],
  outs = ["@maven//:org.codehaus.jackson/jackson-xc-1.9.13.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.jackson_jackson-xc_1.9.13",
  jars = ["@maven//:org.codehaus.jackson/jackson-xc-1.9.13.jar"],
  deps = ["org.codehaus.jackson_jackson-core-asl_1.9.13"],
  exports = ["org.codehaus.jackson_jackson-core-asl_1.9.13"],
  tags = ["jvm_module=org.codehaus.jackson:jackson-xc", "jvm_version=1.9.13"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.hadoop_hadoop-yarn-server-nodemanager_2.7.4_extension",
  srcs = ["@org.apache.hadoop_hadoop-yarn-server-nodemanager_2.7.4//file"],
  outs = ["@maven//:org.apache.hadoop/hadoop-yarn-server-nodemanager-2.7.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.hadoop_hadoop-yarn-server-nodemanager_2.7.4",
  jars = ["@maven//:org.apache.hadoop/hadoop-yarn-server-nodemanager-2.7.4.jar"],
  deps = [
    "org.apache.hadoop_hadoop-yarn-common_2.7.4", "org.apache.hadoop_hadoop-yarn-api_2.7.4",
    "javax.xml.bind_jaxb-api_2.2.2", "org.codehaus.jettison_jettison_1.1", "commons-lang_commons-lang_2.6",
    "commons-codec_commons-codec_1.9", "org.mortbay.jetty_jetty-util_6.1.26", "com.google.guava_guava_16.0.1",
    "org.slf4j_slf4j-api_1.7.30", "com.google.protobuf_protobuf-java_2.5.0", "com.google.inject_guice_3.0",
    "org.apache.hadoop_hadoop-yarn-server-common_2.7.4"
  ],
  exports = [
    "org.apache.hadoop_hadoop-yarn-common_2.7.4", "org.apache.hadoop_hadoop-yarn-api_2.7.4",
    "javax.xml.bind_jaxb-api_2.2.2", "org.codehaus.jettison_jettison_1.1", "commons-lang_commons-lang_2.6",
    "commons-codec_commons-codec_1.9", "org.mortbay.jetty_jetty-util_6.1.26", "com.google.guava_guava_16.0.1",
    "org.slf4j_slf4j-api_1.7.30", "com.google.protobuf_protobuf-java_2.5.0", "com.google.inject_guice_3.0",
    "org.apache.hadoop_hadoop-yarn-server-common_2.7.4"
  ],
  tags = ["jvm_module=org.apache.hadoop:hadoop-yarn-server-nodemanager", "jvm_version=2.7.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "commons-beanutils_commons-beanutils_1.7.0_extension",
  srcs = ["@commons-beanutils_commons-beanutils_1.7.0//file"],
  outs = ["@maven//:commons-beanutils/commons-beanutils-1.7.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "commons-beanutils_commons-beanutils_1.7.0",
  jars = ["@maven//:commons-beanutils/commons-beanutils-1.7.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=commons-beanutils:commons-beanutils", "jvm_version=1.7.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.httpcomponents_httpcore_4.2.4_extension",
  srcs = ["@org.apache.httpcomponents_httpcore_4.2.4//file"],
  outs = ["@maven//:org.apache.httpcomponents/httpcore-4.2.4.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.httpcomponents_httpcore_4.2.4",
  jars = ["@maven//:org.apache.httpcomponents/httpcore-4.2.4.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.apache.httpcomponents:httpcore", "jvm_version=4.2.4"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.directory.server_apacheds-i18n_2.0.0-M15_extension",
  srcs = ["@org.apache.directory.server_apacheds-i18n_2.0.0-M15//file"],
  outs = ["@maven//:org.apache.directory.server/apacheds-i18n-2.0.0-M15.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.directory.server_apacheds-i18n_2.0.0-M15",
  jars = ["@maven//:org.apache.directory.server/apacheds-i18n-2.0.0-M15.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30"],
  exports = ["org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=org.apache.directory.server:apacheds-i18n", "jvm_version=2.0.0-M15"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.directory.api_api-asn1-api_1.0.0-M20_extension",
  srcs = ["@org.apache.directory.api_api-asn1-api_1.0.0-M20//file"],
  outs = ["@maven//:org.apache.directory.api/api-asn1-api-1.0.0-M20.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.directory.api_api-asn1-api_1.0.0-M20",
  jars = ["@maven//:org.apache.directory.api/api-asn1-api-1.0.0-M20.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30"],
  exports = ["org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=org.apache.directory.api:api-asn1-api", "jvm_version=1.0.0-M20"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.apache.directory.api_api-util_1.0.0-M20_extension",
  srcs = ["@org.apache.directory.api_api-util_1.0.0-M20//file"],
  outs = ["@maven//:org.apache.directory.api/api-util-1.0.0-M20.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.apache.directory.api_api-util_1.0.0-M20",
  jars = ["@maven//:org.apache.directory.api/api-util-1.0.0-M20.jar"],
  deps = ["org.slf4j_slf4j-api_1.7.30"],
  exports = ["org.slf4j_slf4j-api_1.7.30"],
  tags = ["jvm_module=org.apache.directory.api:api-util", "jvm_version=1.0.0-M20"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "jline_jline_0.9.94_extension",
  srcs = ["@jline_jline_0.9.94//file"],
  outs = ["@maven//:jline/jline-0.9.94.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "jline_jline_0.9.94",
  jars = ["@maven//:jline/jline-0.9.94.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=jline:jline", "jvm_version=0.9.94"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.google.inject_guice_3.0_extension",
  srcs = ["@com.google.inject_guice_3.0//file"],
  outs = ["@maven//:com.google.inject/guice-3.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.google.inject_guice_3.0",
  jars = ["@maven//:com.google.inject/guice-3.0.jar"],
  deps = ["javax.inject_javax.inject_1", "aopalliance_aopalliance_1.0"],
  exports = ["javax.inject_javax.inject_1", "aopalliance_aopalliance_1.0"],
  tags = ["jvm_module=com.google.inject:guice", "jvm_version=3.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "javax.xml.stream_stax-api_1.0-2_extension",
  srcs = ["@javax.xml.stream_stax-api_1.0-2//file"],
  outs = ["@maven//:javax.xml.stream/stax-api-1.0-2.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "javax.xml.stream_stax-api_1.0-2",
  jars = ["@maven//:javax.xml.stream/stax-api-1.0-2.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=javax.xml.stream:stax-api", "jvm_version=1.0-2"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.codehaus.jettison_jettison_1.1_extension",
  srcs = ["@org.codehaus.jettison_jettison_1.1//file"],
  outs = ["@maven//:org.codehaus.jettison/jettison-1.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.codehaus.jettison_jettison_1.1",
  jars = ["@maven//:org.codehaus.jettison/jettison-1.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.codehaus.jettison:jettison", "jvm_version=1.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "javax.inject_javax.inject_1_extension",
  srcs = ["@javax.inject_javax.inject_1//file"],
  outs = ["@maven//:javax.inject/javax.inject-1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "javax.inject_javax.inject_1",
  jars = ["@maven//:javax.inject/javax.inject-1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=javax.inject:javax.inject", "jvm_version=1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "aopalliance_aopalliance_1.0_extension",
  srcs = ["@aopalliance_aopalliance_1.0//file"],
  outs = ["@maven//:aopalliance/aopalliance-1.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "aopalliance_aopalliance_1.0",
  jars = ["@maven//:aopalliance/aopalliance-1.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=aopalliance:aopalliance", "jvm_version=1.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scalameta_scalameta_2.12_4.3.23_extension",
  srcs = ["@org.scalameta_scalameta_2.12_4.3.23//file"],
  outs = ["@maven//:org.scalameta/scalameta_2.12-4.3.23.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scalameta_scalameta_2.12_4.3.23",
  jars = ["@maven//:org.scalameta/scalameta_2.12-4.3.23.jar"],
  deps = ["org.scala-lang_scala-library_2.12.12", "org.scalameta_parsers_2.12_4.3.23", "org.scala-lang_scalap_2.12.12"],
  exports = [
    "org.scala-lang_scala-library_2.12.12", "org.scalameta_parsers_2.12_4.3.23", "org.scala-lang_scalap_2.12.12"
  ],
  tags = ["jvm_module=org.scalameta:scalameta_2.12", "jvm_version=4.3.23"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang_scala-library_2.12.12_extension",
  srcs = ["@org.scala-lang_scala-library_2.12.12//file"],
  outs = ["@maven//:org.scala-lang/scala-library-2.12.12.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang_scala-library_2.12.12",
  jars = ["@maven//:org.scala-lang/scala-library-2.12.12.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.scala-lang:scala-library", "jvm_version=2.12.12"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scalameta_parsers_2.12_4.3.23_extension",
  srcs = ["@org.scalameta_parsers_2.12_4.3.23//file"],
  outs = ["@maven//:org.scalameta/parsers_2.12-4.3.23.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scalameta_parsers_2.12_4.3.23",
  jars = ["@maven//:org.scalameta/parsers_2.12-4.3.23.jar"],
  deps = ["org.scala-lang_scala-library_2.12.12", "org.scalameta_trees_2.12_4.3.23"],
  exports = ["org.scala-lang_scala-library_2.12.12", "org.scalameta_trees_2.12_4.3.23"],
  tags = ["jvm_module=org.scalameta:parsers_2.12", "jvm_version=4.3.23"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang_scalap_2.12.12_extension",
  srcs = ["@org.scala-lang_scalap_2.12.12//file"],
  outs = ["@maven//:org.scala-lang/scalap-2.12.12.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang_scalap_2.12.12",
  jars = ["@maven//:org.scala-lang/scalap-2.12.12.jar"],
  deps = ["org.scala-lang_scala-compiler_2.12.12"],
  exports = ["org.scala-lang_scala-compiler_2.12.12"],
  tags = ["jvm_module=org.scala-lang:scalap", "jvm_version=2.12.12"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scalameta_trees_2.12_4.3.23_extension",
  srcs = ["@org.scalameta_trees_2.12_4.3.23//file"],
  outs = ["@maven//:org.scalameta/trees_2.12-4.3.23.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scalameta_trees_2.12_4.3.23",
  jars = ["@maven//:org.scalameta/trees_2.12-4.3.23.jar"],
  deps = [
    "org.scala-lang_scala-library_2.12.12", "org.scalameta_common_2.12_4.3.23",
    "com.thesamet.scalapb_scalapb-runtime_2.12_0.10.8", "org.scalameta_fastparse_2.12_1.0.1"
  ],
  exports = [
    "org.scala-lang_scala-library_2.12.12", "org.scalameta_common_2.12_4.3.23",
    "com.thesamet.scalapb_scalapb-runtime_2.12_0.10.8", "org.scalameta_fastparse_2.12_1.0.1"
  ],
  tags = ["jvm_module=org.scalameta:trees_2.12", "jvm_version=4.3.23"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang_scala-compiler_2.12.12_extension",
  srcs = ["@org.scala-lang_scala-compiler_2.12.12//file"],
  outs = ["@maven//:org.scala-lang/scala-compiler-2.12.12.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang_scala-compiler_2.12.12",
  jars = ["@maven//:org.scala-lang/scala-compiler-2.12.12.jar"],
  deps = [
    "org.scala-lang_scala-library_2.12.12", "org.scala-lang_scala-reflect_2.12.12",
    "org.scala-lang.modules_scala-xml_2.12_1.0.6"
  ],
  exports = [
    "org.scala-lang_scala-library_2.12.12", "org.scala-lang_scala-reflect_2.12.12",
    "org.scala-lang.modules_scala-xml_2.12_1.0.6"
  ],
  tags = ["jvm_module=org.scala-lang:scala-compiler", "jvm_version=2.12.12"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scalameta_common_2.12_4.3.23_extension",
  srcs = ["@org.scalameta_common_2.12_4.3.23//file"],
  outs = ["@maven//:org.scalameta/common_2.12-4.3.23.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scalameta_common_2.12_4.3.23",
  jars = ["@maven//:org.scalameta/common_2.12-4.3.23.jar"],
  deps = ["org.scala-lang_scala-library_2.12.12", "com.lihaoyi_sourcecode_2.12_0.2.1"],
  exports = ["org.scala-lang_scala-library_2.12.12", "com.lihaoyi_sourcecode_2.12_0.2.1"],
  tags = ["jvm_module=org.scalameta:common_2.12", "jvm_version=4.3.23"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.thesamet.scalapb_scalapb-runtime_2.12_0.10.8_extension",
  srcs = ["@com.thesamet.scalapb_scalapb-runtime_2.12_0.10.8//file"],
  outs = ["@maven//:com.thesamet.scalapb/scalapb-runtime_2.12-0.10.8.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.thesamet.scalapb_scalapb-runtime_2.12_0.10.8",
  jars = ["@maven//:com.thesamet.scalapb/scalapb-runtime_2.12-0.10.8.jar"],
  deps = [
    "org.scala-lang_scala-library_2.12.12", "com.thesamet.scalapb_lenses_2.12_0.10.8",
    "com.google.protobuf_protobuf-java_3.13.0", "org.scala-lang.modules_scala-collection-compat_2.12_2.1.6",
    "com.lihaoyi_fastparse_2.12_2.3.0"
  ],
  exports = [
    "org.scala-lang_scala-library_2.12.12", "com.thesamet.scalapb_lenses_2.12_0.10.8",
    "com.google.protobuf_protobuf-java_3.13.0", "org.scala-lang.modules_scala-collection-compat_2.12_2.1.6",
    "com.lihaoyi_fastparse_2.12_2.3.0"
  ],
  tags = ["jvm_module=com.thesamet.scalapb:scalapb-runtime_2.12", "jvm_version=0.10.8"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scalameta_fastparse_2.12_1.0.1_extension",
  srcs = ["@org.scalameta_fastparse_2.12_1.0.1//file"],
  outs = ["@maven//:org.scalameta/fastparse_2.12-1.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scalameta_fastparse_2.12_1.0.1",
  jars = ["@maven//:org.scalameta/fastparse_2.12-1.0.1.jar"],
  deps = [
    "org.scala-lang_scala-library_2.12.12", "org.scalameta_fastparse-utils_2.12_1.0.1",
    "com.lihaoyi_sourcecode_2.12_0.2.1"
  ],
  exports = [
    "org.scala-lang_scala-library_2.12.12", "org.scalameta_fastparse-utils_2.12_1.0.1",
    "com.lihaoyi_sourcecode_2.12_0.2.1"
  ],
  tags = ["jvm_module=org.scalameta:fastparse_2.12", "jvm_version=1.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang_scala-reflect_2.12.12_extension",
  srcs = ["@org.scala-lang_scala-reflect_2.12.12//file"],
  outs = ["@maven//:org.scala-lang/scala-reflect-2.12.12.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang_scala-reflect_2.12.12",
  jars = ["@maven//:org.scala-lang/scala-reflect-2.12.12.jar"],
  deps = ["org.scala-lang_scala-library_2.12.12"],
  exports = ["org.scala-lang_scala-library_2.12.12"],
  tags = ["jvm_module=org.scala-lang:scala-reflect", "jvm_version=2.12.12"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang.modules_scala-xml_2.12_1.0.6_extension",
  srcs = ["@org.scala-lang.modules_scala-xml_2.12_1.0.6//file"],
  outs = ["@maven//:org.scala-lang.modules/scala-xml_2.12-1.0.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang.modules_scala-xml_2.12_1.0.6",
  jars = ["@maven//:org.scala-lang.modules/scala-xml_2.12-1.0.6.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=org.scala-lang.modules:scala-xml_2.12", "jvm_version=1.0.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.lihaoyi_sourcecode_2.12_0.2.1_extension",
  srcs = ["@com.lihaoyi_sourcecode_2.12_0.2.1//file"],
  outs = ["@maven//:com.lihaoyi/sourcecode_2.12-0.2.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.lihaoyi_sourcecode_2.12_0.2.1",
  jars = ["@maven//:com.lihaoyi/sourcecode_2.12-0.2.1.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.lihaoyi:sourcecode_2.12", "jvm_version=0.2.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.thesamet.scalapb_lenses_2.12_0.10.8_extension",
  srcs = ["@com.thesamet.scalapb_lenses_2.12_0.10.8//file"],
  outs = ["@maven//:com.thesamet.scalapb/lenses_2.12-0.10.8.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.thesamet.scalapb_lenses_2.12_0.10.8",
  jars = ["@maven//:com.thesamet.scalapb/lenses_2.12-0.10.8.jar"],
  deps = ["org.scala-lang_scala-library_2.12.12", "org.scala-lang.modules_scala-collection-compat_2.12_2.1.6"],
  exports = ["org.scala-lang_scala-library_2.12.12", "org.scala-lang.modules_scala-collection-compat_2.12_2.1.6"],
  tags = ["jvm_module=com.thesamet.scalapb:lenses_2.12", "jvm_version=0.10.8"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scala-lang.modules_scala-collection-compat_2.12_2.1.6_extension",
  srcs = ["@org.scala-lang.modules_scala-collection-compat_2.12_2.1.6//file"],
  outs = ["@maven//:org.scala-lang.modules/scala-collection-compat_2.12-2.1.6.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scala-lang.modules_scala-collection-compat_2.12_2.1.6",
  jars = ["@maven//:org.scala-lang.modules/scala-collection-compat_2.12-2.1.6.jar"],
  deps = ["org.scala-lang_scala-library_2.12.12"],
  exports = ["org.scala-lang_scala-library_2.12.12"],
  tags = ["jvm_module=org.scala-lang.modules:scala-collection-compat_2.12", "jvm_version=2.1.6"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.lihaoyi_fastparse_2.12_2.3.0_extension",
  srcs = ["@com.lihaoyi_fastparse_2.12_2.3.0//file"],
  outs = ["@maven//:com.lihaoyi/fastparse_2.12-2.3.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.lihaoyi_fastparse_2.12_2.3.0",
  jars = ["@maven//:com.lihaoyi/fastparse_2.12-2.3.0.jar"],
  deps = ["com.lihaoyi_sourcecode_2.12_0.2.1", "com.lihaoyi_geny_2.12_0.6.0"],
  exports = ["com.lihaoyi_sourcecode_2.12_0.2.1", "com.lihaoyi_geny_2.12_0.6.0"],
  tags = ["jvm_module=com.lihaoyi:fastparse_2.12", "jvm_version=2.3.0"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "org.scalameta_fastparse-utils_2.12_1.0.1_extension",
  srcs = ["@org.scalameta_fastparse-utils_2.12_1.0.1//file"],
  outs = ["@maven//:org.scalameta/fastparse-utils_2.12-1.0.1.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "org.scalameta_fastparse-utils_2.12_1.0.1",
  jars = ["@maven//:org.scalameta/fastparse-utils_2.12-1.0.1.jar"],
  deps = ["org.scala-lang_scala-library_2.12.12", "com.lihaoyi_sourcecode_2.12_0.2.1"],
  exports = ["org.scala-lang_scala-library_2.12.12", "com.lihaoyi_sourcecode_2.12_0.2.1"],
  tags = ["jvm_module=org.scalameta:fastparse-utils_2.12", "jvm_version=1.0.1"],
  visibility = ["//visibility:public"]
)

genrule(
  name = "com.lihaoyi_geny_2.12_0.6.0_extension",
  srcs = ["@com.lihaoyi_geny_2.12_0.6.0//file"],
  outs = ["@maven//:com.lihaoyi/geny_2.12-0.6.0.jar"],
  cmd = "cp $< $@"
)
scala_import(
  name = "com.lihaoyi_geny_2.12_0.6.0",
  jars = ["@maven//:com.lihaoyi/geny_2.12-0.6.0.jar"],
  deps = [],
  exports = [],
  tags = ["jvm_module=com.lihaoyi:geny_2.12", "jvm_version=0.6.0"],
  visibility = ["//visibility:public"]
)
'''
    ctx.file("BUILD", build_content, executable = False)

jvm_deps_rule = repository_rule(
    implementation = _jvm_deps_impl,
)
def jvm_deps():
  jvm_deps_rule(name = "maven")
