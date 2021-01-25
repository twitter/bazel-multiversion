package tests.commands

class ExportCommandSuite extends tests.BaseSuite {

  checkDeps(
    "evicted artifacts do not create genrules",
    s"""|  - dependency: org.slf4j:slf4j-log4j12:1.6.1
        |  - dependency: org.slf4j:slf4j-log4j12:1.6.4
        |""".stripMargin,
    queryArgs = allGenrules,
    expectedQuery = """|@maven//:genrules/log4j_log4j_1.2.16
                       |@maven//:genrules/org.slf4j_slf4j-api_1.6.4
                       |@maven//:genrules/org.slf4j_slf4j-log4j12_1.6.4
                       |""".stripMargin
  )

  checkDeps(
    "scalatest",
    """|  - dependency: org.scalatest:scalatest_2.12:3.1.2
       |""".stripMargin,
    queryArgs = allScalaImports,
    expectedQuery = """|@maven//:org.scala-lang.modules_scala-xml_2.12_1.2.0
                       |@maven//:org.scala-lang_scala-library_2.12.11
                       |@maven//:org.scala-lang_scala-reflect_2.12.11
                       |@maven//:org.scalactic_scalactic_2.12_3.1.2
                       |@maven//:org.scalatest_scalatest_2.12_3.1.2
                       |""".stripMargin
  )

  checkDeps(
    "netty",
    """|  - dependency: io.netty:netty:3.10.1.Final
       |  - dependency: io.netty:netty:3.7.0.Final
       |""".stripMargin,
    queryArgs = allGenrules,
    expectedQuery = """|@maven//:genrules/io.netty_netty_3.10.1.Final
                       |""".stripMargin
  )

  checkDeps(
    "basic",
    """|  - dependency: com.google.guava:guava:29.0-jre
       |  - dependency: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
       |""".stripMargin,
    expectedOutput = """|/workingDirectory/3rdparty.yaml:3:16 error: transitive dependency 'com.google.guava:guava' has conflicting versions.
         |       found versions: 27.1-jre
         |    declared versions: 29.0-jre
         |      popular version: 29.0-jre
         |              ok deps: com.google.guava:guava:29.0-jre
         |           ok targets:
         |       unpopular deps: org.eclipse.lsp4j:org.eclipse.lsp4j:0.9.0
         |    unpopular targets:
         |  To fix this problem, add 'dependency = com.google.guava:guava:27.1-jre' to the root dependencies OR add 'targets' to the transitive dependency.
         |  - dependency: com.google.guava:guava:29.0-jre
         |                ^""".stripMargin,
    expectedExit = 1
  )

  checkDeps(
    "libthrift",
    """|  - dependency: org.apache.thrift:libthrift:0.10.0
       |""".stripMargin
  )

  checkDeps(
    "version_scheme",
    s"""|  - dependency: com.lihaoyi:fansi_2.12:0.2.8
        |    versionScheme: pvp
        |  - dependency: com.lihaoyi:sourcecode_2.12:0.2.0
        |    versionScheme: pvp
        |  - dependency: com.lihaoyi:pprint_2.12:0.5.9
        |${scalaLibrary("MyApp.scala", "object MyApp { val x = 42 }")}
        |""".stripMargin,
  )

  checkDeps(
    "classifier",
    s"""|  - dependency: jline:jline:2.14.6
        |  - dependency: jline:jline:2.14.6
        |    classifier: test
        |""".stripMargin
  )

  checkDeps(
    "classifier with eviction",
    s"""|  - dependency: org.apache.kafka:kafka-clients:2.4.1
        |    versionScheme: pvp
        |  - dependency: org.apache.kafka:kafka-clients:2.4.0
        |    versionScheme: pvp
        |    classifier: test
        |""".stripMargin,
    queryArgs = allGenrules,
    expectedQuery = """|@maven//:genrules/com.github.luben_zstd-jni_1.4.3-1
                       |@maven//:genrules/org.apache.kafka_kafka-clients_2.4.1
                       |@maven//:genrules/org.apache.kafka_kafka-clients_2.4.1_test
                       |@maven//:genrules/org.lz4_lz4-java_1.6.0
                       |@maven//:genrules/org.slf4j_slf4j-api_1.7.28
                       |@maven//:genrules/org.xerial.snappy_snappy-java_1.1.7.3
                       |""".stripMargin
  )

  checkDeps(
    "kafka-streams",
    s"""|  - dependency: org.apache.kafka:kafka-streams:2.4.1
        |    versionScheme: pvp
        |""".stripMargin,
    queryArgs = allScalaImportDeps("@maven//:org.apache.kafka_kafka-streams_2.4.1"),
    expectedQuery = """|@maven//:com.fasterxml.jackson.core_jackson-annotations_2.10.0
                       |@maven//:com.fasterxml.jackson.core_jackson-core_2.10.0
                       |@maven//:com.fasterxml.jackson.core_jackson-databind_2.10.0
                       |@maven//:com.fasterxml.jackson.datatype_jackson-datatype-jdk8_2.10.0
                       |@maven//:com.github.luben_zstd-jni_1.4.3-1
                       |@maven//:org.apache.kafka_connect-api_2.4.1
                       |@maven//:org.apache.kafka_connect-json_2.4.1
                       |@maven//:org.apache.kafka_kafka-clients_2.4.1
                       |@maven//:org.apache.kafka_kafka-streams_2.4.1
                       |@maven//:org.lz4_lz4-java_1.6.0
                       |@maven//:org.rocksdb_rocksdbjni_5.18.3
                       |@maven//:org.slf4j_slf4j-api_1.7.28
                       |@maven//:org.xerial.snappy_snappy-java_1.1.7.3
                       |""".stripMargin
  )

  checkDeps(
    "classifier with eviction dependencies",
    s"""|  - dependency: org.apache.kafka:kafka-clients:2.4.1
        |    versionScheme: pvp
        |  - dependency: org.apache.kafka:kafka-clients:2.4.0
        |    versionScheme: pvp
        |    classifier: test
        |  - dependency: org.apache.kafka:kafka-streams:2.4.0
        |    versionScheme: pvp
        |""".stripMargin,
    queryArgs = allScalaImportDeps("@maven//:org.apache.kafka_kafka-streams_2.4.0"),
    expectedQuery = """|@maven//:com.fasterxml.jackson.core_jackson-annotations_2.10.0
                       |@maven//:com.fasterxml.jackson.core_jackson-core_2.10.0
                       |@maven//:com.fasterxml.jackson.core_jackson-databind_2.10.0
                       |@maven//:com.fasterxml.jackson.datatype_jackson-datatype-jdk8_2.10.0
                       |@maven//:com.github.luben_zstd-jni_1.4.3-1
                       |@maven//:org.apache.kafka_connect-api_2.4.0
                       |@maven//:org.apache.kafka_connect-json_2.4.0
                       |@maven//:org.apache.kafka_kafka-clients_2.4.1
                       |@maven//:org.apache.kafka_kafka-streams_2.4.0
                       |@maven//:org.lz4_lz4-java_1.6.0
                       |@maven//:org.rocksdb_rocksdbjni_5.18.3
                       |@maven//:org.slf4j_slf4j-api_1.7.28
                       |@maven//:org.xerial.snappy_snappy-java_1.1.7.3
                       |""".stripMargin
  )

  checkDeps(
    "inter-target dependency",
    s"""|  - dependency: org.apache.thrift:libthrift:0.10.0
        |    dependencies:
        |      - mytargets:guava
        |  - dependency: com.google.guava:guava:25.1-jre
        |    targets:
        |      - mytargets:guava
        |""".stripMargin,
    queryArgs = allScalaImportDeps("@maven//:org.apache.thrift_libthrift_0.10.0"),
    expectedQuery = """
                    |@maven//:com.google.code.findbugs_jsr305_3.0.2
                    |@maven//:com.google.errorprone_error_prone_annotations_2.1.3
                    |@maven//:com.google.guava_guava_25.1-jre
                    |@maven//:com.google.j2objc_j2objc-annotations_1.1
                    |@maven//:commons-codec_commons-codec_1.9
                    |@maven//:commons-logging_commons-logging_1.2
                    |@maven//:org.apache.httpcomponents_httpclient_4.4.1
                    |@maven//:org.apache.httpcomponents_httpcore_4.4.1
                    |@maven//:org.apache.thrift_libthrift_0.10.0
                    |@maven//:org.checkerframework_checker-qual_2.0.0
                    |@maven//:org.codehaus.mojo_animal-sniffer-annotations_1.14
                    |@maven//:org.slf4j_slf4j-api_1.7.12
                    |""".stripMargin
  )
}
