package tests.commands

class ExportCommandSuite extends tests.BaseSuite with tests.ConfigSyntax {

  checkDeps(
    "transitive version ranges do not show up",
    s"""|  - dependency: com.nimbusds:nimbus-jose-jwt:4.41.1
        |""".stripMargin,
    queryArgs = allGenrules,
    expectedQuery = """|@maven//:genrules/com.github.stephenc.jcip_jcip-annotations_1.0-1
                       |@maven//:genrules/com.nimbusds_nimbus-jose-jwt_4.41.1
                       |@maven//:genrules/net.minidev_accessors-smart_1.2
                       |@maven//:genrules/net.minidev_json-smart_2.3
                       |@maven//:genrules/org.ow2.asm_asm_5.0.4
                       |""".stripMargin
  )

  checkDeps(
    "evicted artifacts do not create genrules",
    s"""|  - dependency: org.slf4j:slf4j-log4j12:1.6.1
        |    force: true
        |  - dependency: org.slf4j:slf4j-log4j12:1.6.4
        |    force: false
        |""".stripMargin,
    queryArgs = allGenrules,
    expectedQuery = """|@maven//:genrules/log4j_log4j_1.2.16
                       |@maven//:genrules/org.slf4j_slf4j-api_1.6.1
                       |@maven//:genrules/org.slf4j_slf4j-log4j12_1.6.1
                       |""".stripMargin
  )

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
       |    targets: [scalatest]
       |""".stripMargin,
    // queryArgs = allScalaImports,
    queryArgs = allJars("@maven//:scalatest"),
    expectedQuery = """|@maven//:org.scala-lang.modules/scala-xml_2.12/1.2.0.jar
                       |@maven//:org.scala-lang/scala-library/2.12.11.jar
                       |@maven//:org.scala-lang/scala-reflect/2.12.11.jar
                       |@maven//:org.scalactic/scalactic_2.12/3.1.2.jar
                       |@maven//:org.scalatest/scalatest_2.12/3.1.2.jar
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
        |    targets: [client-2.4.1]
        |  - dependency: org.apache.kafka:kafka-clients:2.4.0
        |    versionScheme: pvp
        |    classifier: test
        |    targets: [client-2.4.0]
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

  checkMultipleDeps(
    "classifier with eviction 2",
    s"""|  - dependency: org.apache.kafka:kafka-clients:2.5.0
        |    versionScheme: pvp
        |    targets: [client-2.5.0]
        |  - dependency: org.apache.kafka:kafka-clients:2.4.0
        |    versionScheme: pvp
        |    classifier: test
        |    targets: [client-2.4.0]
        |""".stripMargin,
    queries = List(
      allScalaImportDeps("@maven//:client-2.5.0") ->
        """|@maven//:_com.github.luben_zstd-jni_1.4.4-7
           |@maven//:_org.lz4_lz4-java_1.7.1
           |@maven//:_org.slf4j_slf4j-api_1.7.30
           |@maven//:_org.xerial.snappy_snappy-java_1.1.7.3
           |@maven//:client-2.5.0
           |@maven//:org.apache.kafka_kafka-clients_2.5.0_1493927235
           |""".stripMargin,
      allScalaImportDeps("@maven//:client-2.4.0") ->
        """|@maven//:_com.github.luben_zstd-jni_1.4.4-7
           |@maven//:_org.lz4_lz4-java_1.7.1
           |@maven//:_org.slf4j_slf4j-api_1.7.30
           |@maven//:_org.xerial.snappy_snappy-java_1.1.7.3
           |@maven//:client-2.4.0
           |@maven//:org.apache.kafka_kafka-clients_2.4.0_test_1232654558
           |""".stripMargin
    )
  )

  checkDeps(
    "kafka-streams",
    s"""|  - dependency: org.apache.kafka:kafka-streams:2.4.1
        |    versionScheme: pvp
        |    targets: [kafka-streams]
        |""".stripMargin,
    queryArgs = allJars("@maven//:kafka-streams"),
    expectedQuery = """|@maven//:com.fasterxml.jackson.core/jackson-annotations/2.10.0.jar
                       |@maven//:com.fasterxml.jackson.core/jackson-core/2.10.0.jar
                       |@maven//:com.fasterxml.jackson.core/jackson-databind/2.10.0.jar
                       |@maven//:com.fasterxml.jackson.datatype/jackson-datatype-jdk8/2.10.0.jar
                       |@maven//:com.github.luben/zstd-jni/1.4.3-1.jar
                       |@maven//:org.apache.kafka/connect-api/2.4.1.jar
                       |@maven//:org.apache.kafka/connect-json/2.4.1.jar
                       |@maven//:org.apache.kafka/kafka-clients/2.4.1.jar
                       |@maven//:org.apache.kafka/kafka-streams/2.4.1.jar
                       |@maven//:org.lz4/lz4-java/1.6.0.jar
                       |@maven//:org.rocksdb/rocksdbjni/5.18.3.jar
                       |@maven//:org.slf4j/slf4j-api/1.7.28.jar
                       |@maven//:org.xerial.snappy/snappy-java/1.1.7.3.jar""".stripMargin
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
        |    targets: [kafka-streams-2.4.0]
        |""".stripMargin,
    queryArgs = allJars("@maven//:kafka-streams-2.4.0"),
    expectedQuery = """|@maven//:com.fasterxml.jackson.core/jackson-annotations/2.10.0.jar
                       |@maven//:com.fasterxml.jackson.core/jackson-core/2.10.0.jar
                       |@maven//:com.fasterxml.jackson.core/jackson-databind/2.10.0.jar
                       |@maven//:com.fasterxml.jackson.datatype/jackson-datatype-jdk8/2.10.0.jar
                       |@maven//:com.github.luben/zstd-jni/1.4.3-1.jar
                       |@maven//:org.apache.kafka/connect-api/2.4.0.jar
                       |@maven//:org.apache.kafka/connect-json/2.4.0.jar
                       |@maven//:org.apache.kafka/kafka-clients/2.4.1.jar
                       |@maven//:org.apache.kafka/kafka-streams/2.4.0.jar
                       |@maven//:org.lz4/lz4-java/1.6.0.jar
                       |@maven//:org.rocksdb/rocksdbjni/5.18.3.jar
                       |@maven//:org.slf4j/slf4j-api/1.7.28.jar
                       |@maven//:org.xerial.snappy/snappy-java/1.1.7.3.jar
                       |""".stripMargin
  )

  checkMultipleDeps(
    "target dependency are transitively included",
    deps(
      dep("commons-logging:commons-logging:1.2")
        .target("commons-logging")
        .dependency("commons-codec"),
      dep("commons-codec:commons-codec:1.9")
        .target("commons-codec")
        .dependency("apiguardian"),
      dep("org.apiguardian:apiguardian-api:1.1.1")
        .target("apiguardian")
    ),
    queries = List(
      allJars("@maven//:commons-logging") ->
        """|@maven//:commons-codec/commons-codec/1.9.jar
           |@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
      allJars("@maven//:commons-codec") ->
        """|@maven//:commons-codec/commons-codec/1.9.jar
           |@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
      allJars("@maven//:apiguardian") ->
        """|@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
    )
  )

  checkMultipleDeps(
    "exclusions of target dependencies are respected",
    deps(
      dep("org.apiguardian:apiguardian-api:1.1.1")
        .target("apiguardian")
        .dependency("httpclient"),
      dep("org.apache.httpcomponents:httpclient:4.4.1")
        .target("httpclient")
        .exclude("commons-codec:commons-codec")
    ),
    queries = List(
      allJars("@maven//:httpclient") ->
        """|@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apache.httpcomponents/httpclient/4.4.1.jar
           |@maven//:org.apache.httpcomponents/httpcore/4.4.1.jar""".stripMargin,
      allJars("@maven//:apiguardian") ->
        """|@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apache.httpcomponents/httpclient/4.4.1.jar
           |@maven//:org.apache.httpcomponents/httpcore/4.4.1.jar
           |@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
    )
  )

  checkMultipleDeps(
    "exclusions are respected and don't leak to other resolutions",
    deps(
      dep("org.apache.thrift:libthrift:0.10.0")
        .target("libthrift")
        .exclude("commons-codec:commons-codec"),
      dep("org.apache.httpcomponents:httpclient:4.4.1")
        .target("httpclient")
    ),
    queries = List(
      allJars("@maven//:libthrift") ->
        """|@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apache.httpcomponents/httpclient/4.4.1.jar
           |@maven//:org.apache.httpcomponents/httpcore/4.4.1.jar
           |@maven//:org.apache.thrift/libthrift/0.10.0.jar
           |@maven//:org.slf4j/slf4j-api/1.7.12.jar""".stripMargin,
      allJars("@maven//:httpclient") ->
        """|@maven//:commons-codec/commons-codec/1.9.jar
           |@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apache.httpcomponents/httpclient/4.4.1.jar
           |@maven//:org.apache.httpcomponents/httpcore/4.4.1.jar""".stripMargin
    )
  )

  checkMultipleDeps(
    "replacements are respected",
    deps(
      dep("org.apache.thrift:libthrift:0.10.0")
        .target("libthrift")
        .exclude("commons-codec:commons-codec")
        .dependency("commons-codec-1-10"),
      dep("commons-codec:commons-codec:1.10")
        .target("commons-codec-1-10"),
      dep("org.apache.httpcomponents:httpclient:4.4.1")
        .target("httpclient")
    ),
    queries = List(
      allJars("@maven//:libthrift") ->
        """|@maven//:commons-codec/commons-codec/1.10.jar
           |@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apache.httpcomponents/httpclient/4.4.1.jar
           |@maven//:org.apache.httpcomponents/httpcore/4.4.1.jar
           |@maven//:org.apache.thrift/libthrift/0.10.0.jar
           |@maven//:org.slf4j/slf4j-api/1.7.12.jar""".stripMargin,
      allJars("@maven//:httpclient") ->
        """|@maven//:commons-codec/commons-codec/1.10.jar
           |@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apache.httpcomponents/httpclient/4.4.1.jar
           |@maven//:org.apache.httpcomponents/httpcore/4.4.1.jar""".stripMargin
    )
  )

  checkMultipleDeps(
    "additions are respected and don't leak to other resolutions",
    deps(
      dep("org.apiguardian:apiguardian-api:1.1.1")
        .target("apiguardian"),
      dep("commons-codec:commons-codec:1.9")
        .target("commons-codec")
        .dependency("apiguardian"),
      dep("org.apache.thrift:libthrift:0.10.0")
        .target("libthrift")
    ),
    queries = List(
      allJars("@maven//:apiguardian") ->
        """|@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
      allJars("@maven//:libthrift") ->
        """|@maven//:commons-codec/commons-codec/1.9.jar
           |@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apache.httpcomponents/httpclient/4.4.1.jar
           |@maven//:org.apache.httpcomponents/httpcore/4.4.1.jar
           |@maven//:org.apache.thrift/libthrift/0.10.0.jar
           |@maven//:org.slf4j/slf4j-api/1.7.12.jar""".stripMargin,
      allJars("@maven//:commons-codec") ->
        """|@maven//:commons-codec/commons-codec/1.9.jar
           |@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin
    )
  )

  checkMultipleDeps(
    "eviction keeps target information",
    deps(
      dep("org.apiguardian:apiguardian-api:1.1.1")
        .target("apiguardian"),
      dep("org.apiguardian:apiguardian-api:1.1.0")
        .target("apiguardian-old")
    ),
    queries = List(
      allJars("@maven//:apiguardian") ->
        """|@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
      allJars("@maven//:apiguardian-old") ->
        """|@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin
    )
  )

  checkMultipleDeps(
    "multi-jar dependency and eviction",
    deps(
      dep("org.apiguardian:apiguardian-api:1.1.1")
        .target("multi-jar"),
      dep("commons-logging:commons-logging:1.2")
        .target("multi-jar"),
      dep("org.apiguardian:apiguardian-api:1.1.0")
        .target("apiguardian-old")
    ),
    queries = List(
      allJars("@maven//:multi-jar") ->
        """|@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
      allJars("@maven//:apiguardian-old") ->
        """|@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin
    )
  )

  checkMultipleDeps(
    "multi-jar and multi-target",
    deps(
      dep("org.apiguardian:apiguardian-api:1.1.1")
        .target("apiguardian-and-commons-logging")
        .target("apiguardian-and-commons-codec"),
      dep("commons-logging:commons-logging:1.2")
        .target("apiguardian-and-commons-logging")
        .target("commons-logging-and-commons-codec"),
      dep("commons-codec:commons-codec:1.9")
        .target("apiguardian-and-commons-codec")
        .target("commons-logging-and-commons-codec")
    ),
    queries = List(
      allJars("@maven//:apiguardian-and-commons-logging") ->
        """|@maven//:commons-logging/commons-logging/1.2.jar
           |@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
      allJars("@maven//:apiguardian-and-commons-codec") ->
        """|@maven//:commons-codec/commons-codec/1.9.jar
           |@maven//:org.apiguardian/apiguardian-api/1.1.1.jar""".stripMargin,
      allJars("@maven//:commons-logging-and-commons-codec") ->
        """|@maven//:commons-codec/commons-codec/1.9.jar
           |@maven//:commons-logging/commons-logging/1.2.jar""".stripMargin
    )
  )

  checkMultipleDeps(
    "Generate target for inexistent classifiers",
    deps(
      dep("io.zipkin:zipkin-thrift:1.4.1")
        .classifier("whatever")
        .target("with-classifier")
    ),
    queries = List(
      allJars("@maven//:with-classifier") ->
        "@maven//:org.scala-lang/scala-library/2.11.7.jar"
    )
  )

  checkMultipleDeps(
    "different exclusions and eviction",
    deps(
      dep("org.apache.parquet:parquet-thrift:1.9.0")
        .target("parquet-thrift-1.9.0")
        .exclude("com.twitter.elephantbird:*"),
      dep("org.apache.parquet:parquet-thrift:1.11.0")
        .target("parquet-thrift-1.11.0")
        .exclude("com.hadoop.gplcompression:hadoop-lzo")
    ),
    queries = List(
      allJars("@maven//:parquet-thrift-1.11.0") ->
        s"""|@maven//:com.google.code.findbugs/jsr305/1.3.9.jar
            |@maven//:com.google.guava/guava/11.0.1.jar
            |@maven//:com.google.protobuf/protobuf-java/2.4.1.jar
            |@maven//:com.googlecode.json-simple/json-simple/1.1.jar
            |@maven//:com.twitter.elephantbird/elephant-bird-core/4.4.jar
            |@maven//:com.twitter.elephantbird/elephant-bird-hadoop-compat/4.4.jar
            |@maven//:com.twitter.elephantbird/elephant-bird-pig/4.4.jar
            |@maven//:commons-codec/commons-codec/1.3.jar
            |@maven//:commons-lang/commons-lang/2.5.jar
            |@maven//:commons-logging/commons-logging/1.1.1.jar
            |@maven//:commons-pool/commons-pool/1.6.jar
            |@maven//:javax.annotation/javax.annotation-api/1.3.2.jar
            |@maven//:javax.servlet/servlet-api/2.5.jar
            |@maven//:org.apache.httpcomponents/httpclient/4.0.1.jar
            |@maven//:org.apache.httpcomponents/httpcore/4.0.1.jar
            |@maven//:org.apache.parquet/parquet-column/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-common/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-encoding/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-format-structures/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-hadoop/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-jackson/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-pig/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-thrift/1.11.0.jar
            |@maven//:org.apache.thrift/libthrift/0.7.0.jar
            |@maven//:org.apache.yetus/audience-annotations/0.11.0.jar
            |@maven//:org.slf4j/slf4j-api/1.7.22.jar
            |@maven//:org.xerial.snappy/snappy-java/1.1.7.3.jar
            |""".stripMargin,
      allJars("@maven//:parquet-thrift-1.9.0") ->
        s"""|@maven//:commons-pool/commons-pool/1.6.jar
            |@maven//:javax.annotation/javax.annotation-api/1.3.2.jar
            |@maven//:org.apache.parquet/parquet-column/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-common/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-encoding/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-format-structures/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-hadoop/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-jackson/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-pig/1.11.0.jar
            |@maven//:org.apache.parquet/parquet-thrift/1.11.0.jar
            |@maven//:org.apache.yetus/audience-annotations/0.11.0.jar
            |@maven//:org.slf4j/slf4j-api/1.7.22.jar
            |@maven//:org.xerial.snappy/snappy-java/1.1.7.3.jar
            |""".stripMargin
    )
  )
}
