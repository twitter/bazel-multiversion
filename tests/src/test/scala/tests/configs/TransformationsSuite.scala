package tests.configs

import moped.json.ErrorResult
import moped.json.Result
import multiversion.configs.Transformation
import multiversion.diagnostics.ConflictingTransformationsDiagnostic
import munit.TestOptions
import tests.Addition
import tests.ConfigSyntax
import tests.Exclusion
import tests.Force
import tests.SimplifiedTransformation

class TransformationsSuite extends BaseConfigSuite with ConfigSyntax {

  def checkTransformations(
      name: TestOptions,
      original: String,
      check: Result[List[Transformation]] => Unit
  )(implicit
      loc: munit.Location
  ): Unit = {
    test(name) {
      val fullConfig = s"""dependencies:
                          |$original""".stripMargin
      parseConfig(name, fullConfig, cfg => check(cfg.transformations), fail(_))
    }
  }

  def expectTransformations(
      name: TestOptions,
      original: String,
      expected: List[SimplifiedTransformation]
  )(implicit
      loc: munit.Location
  ): Unit =
    checkTransformations(
      name,
      original,
      transformations =>
        assertEquals(
          transformations.get.map(SimplifiedTransformation.convert).sorted,
          expected.sorted
        )
    )

  def expectConflicts(
      name: TestOptions,
      original: String,
      expected: List[(SimplifiedTransformation, SimplifiedTransformation)]
  )(implicit loc: munit.Location): Unit =
    checkTransformations(
      name,
      original,
      {
        case ErrorResult(ConflictingTransformationsDiagnostic(conflicts)) =>
          def sortedTuple[T](tpl: (T, T))(implicit ord: Ordering[T]): (T, T) =
            tpl match {
              case (a, b) if ord.lteq(a, b) => tpl
              case (a, b)                   => (b, a)
            }
          val converted = conflicts.map {
            case (a, b) =>
              sortedTuple(
                (SimplifiedTransformation.convert(a), SimplifiedTransformation.convert(b))
              )
          }.sorted
          assertEquals(converted, expected.map(sortedTuple(_)).sorted)
        case other => fail(s"Unexpected result: $other")
      }
    )

  expectTransformations(
    "removal transformations are inferred",
    deps(
      dep("org.scalameta:munit:0.7.13")
        .exclude("org.checkerframework:checker-qual")
    ),
    List(
      Exclusion(false, Nil, "org.checkerframework:checker-qual")
    )
  )

  expectTransformations(
    "addition transformations are inferred",
    deps(
      dep("org.scalameta:munit:0.7.13")
        .dependency("checker-qual"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual")
    ),
    List(
      Addition(false, Nil, "org.checkerframework:checker-qual:1.2.3")
    )
  )

  expectTransformations(
    "replacement transformations are inferred",
    deps(
      dep("org.scalameta:munit:0.7.13")
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual")
    ),
    List(
      Force(false, Nil, "org.checkerframework:checker-qual", "1.2.3")
    )
  )

  expectTransformations(
    "transformations defined on a canonical definitions are canonical",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.checkerframework:checker-qual")
    ),
    List(
      Exclusion(true, List("3rdparty/jvm/org/scalameta:munit"), "org.checkerframework:checker-qual")
    )
  )

  expectTransformations(
    "transformations defined on local definitions are local",
    deps(
      dep("org.scalameta:munit:0.7.13")
        .exclude("org.checkerframework:checker-qual")
        .target("foo/bar:baz")
    ),
    List(
      Exclusion(false, List("foo/bar:baz"), "org.checkerframework:checker-qual")
    )
  )

  expectTransformations(
    "canonical exclusion transformations are de-duplicated",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.checkerframework:checker-qual"),
      dep("org.apache.thrift:libthrift:0.10.0").canonical
        .exclude("org.checkerframework:checker-qual")
    ),
    List(
      Exclusion(
        true,
        List("3rdparty/jvm/org/apache/thrift:libthrift"),
        "org.checkerframework:checker-qual"
      )
    )
  )

  expectTransformations(
    "canonical replacement transformations are de-duplicated",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual"),
      dep("org.apache.thrift:libthrift:0.10.0").canonical
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual")
    ),
    List(
      Force(
        true,
        List("3rdparty/jvm/org/apache/thrift:libthrift"),
        "org.checkerframework:checker-qual",
        "1.2.3"
      )
    )
  )

  expectTransformations(
    "local replace subsumed by canonical replacements are de-duplicated",
    deps(
      dep("org.apache.thrift:libthrift:0.10.0")
        .target("libthrift")
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual"),
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual")
    ),
    List(
      Force(
        true,
        List("3rdparty/jvm/org/scalameta:munit"),
        "org.checkerframework:checker-qual",
        "1.2.3"
      )
    )
  )

  expectTransformations(
    "local exclusion subsumed by canonical exclusion are de-duplicated",
    deps(
      dep("org.apache.thrift:libthrift:0.10.0")
        .target("libthrift")
        .exclude("org.checkerframework:checker-qual"),
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.checkerframework:checker-qual")
    ),
    List(
      Exclusion(true, List("3rdparty/jvm/org/scalameta:munit"), "org.checkerframework:checker-qual")
    )
  )

  expectConflicts(
    "global replacement transformations for different version are in conflict",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual"),
      dep("org.apache.thrift:libthrift:0.10.0").canonical
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual-other"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual"),
      dep("org.checkerframework:checker-qual:4.5.6")
        .target("checker-qual-other")
    ),
    List(
      (
        Force(
          true,
          List("3rdparty/jvm/org/scalameta:munit"),
          "org.checkerframework:checker-qual",
          "1.2.3"
        ),
        Force(
          true,
          List("3rdparty/jvm/org/apache/thrift:libthrift"),
          "org.checkerframework:checker-qual",
          "4.5.6"
        )
      )
    )
  )

  expectTransformations(
    "local replacements don't conflict with global replacements",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual"),
      dep("org.apache.thrift:libthrift:0.10.0")
        .target("libthrift")
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual-other"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual"),
      dep("org.checkerframework:checker-qual:4.5.6")
        .target("checker-qual-other")
    ),
    List(
      Force(
        true,
        List("3rdparty/jvm/org/scalameta:munit"),
        "org.checkerframework:checker-qual",
        "1.2.3"
      ),
      Force(false, List("libthrift"), "org.checkerframework:checker-qual", "4.5.6")
    )
  )

  expectConflicts(
    "local replacements of different versions of the same artifact are in conflict",
    deps(
      dep("org.scalameta:munit:0.7.13")
        .target("munit")
        .exclude("org.checkerframework:checker-qual")
        .dependency("checker-qual")
        .dependency("checker-qual-other"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual"),
      dep("org.checkerframework:checker-qual:4.5.6")
        .target("checker-qual-other")
    ),
    List(
      (
        Force(false, List("munit"), "org.checkerframework:checker-qual", "1.2.3"),
        Force(false, List("munit"), "org.checkerframework:checker-qual", "4.5.6")
      )
    )
  )

  expectConflicts(
    "global addition for the same module with different versions are in conflict",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .dependency("checker-qual")
        .dependency("checker-qual-other"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual"),
      dep("org.checkerframework:checker-qual:4.5.6")
        .target("checker-qual-other")
    ),
    List(
      (
        Addition(
          true,
          List("3rdparty/jvm/org/scalameta:munit"),
          "org.checkerframework:checker-qual:1.2.3"
        ),
        Addition(
          true,
          List("3rdparty/jvm/org/scalameta:munit"),
          "org.checkerframework:checker-qual:4.5.6"
        )
      )
    )
  )

  expectTransformations(
    "wildcard exclusion and addition in the same organization produce addition, exclusion and force",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.checkerframework:*")
        .dependency("checker-qual"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("checker-qual")
    ),
    List(
      Exclusion(true, List("3rdparty/jvm/org/scalameta:munit"), "org.checkerframework:*"),
      Force(
        true,
        List("3rdparty/jvm/org/scalameta:munit"),
        "org.checkerframework:checker-qual",
        "1.2.3"
      ),
      Addition(
        true,
        List("3rdparty/jvm/org/scalameta:munit"),
        "org.checkerframework:checker-qual:1.2.3"
      )
    )
  )

  expectTransformations(
    "addition of multi-jar dependency",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .dependency("thrift-and-checker-qual"),
      dep("org.apache.thrift:libthrift:0.10.0")
        .target("thrift-and-checker-qual"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("thrift-and-checker-qual")
    ),
    List(
      Addition(
        true,
        List("3rdparty/jvm/org/scalameta:munit"),
        "org.apache.thrift:libthrift:0.10.0"
      ),
      Addition(
        true,
        List("3rdparty/jvm/org/scalameta:munit"),
        "org.checkerframework:checker-qual:1.2.3"
      )
    )
  )

  expectTransformations(
    "exclusion and addition of multi-jar dependency",
    deps(
      dep("org.scalameta:munit:0.7.13").canonical
        .exclude("org.apache.thrift:libthrift")
        .dependency("thrift-and-checker-qual"),
      dep("org.apache.thrift:libthrift:0.10.0")
        .target("thrift-and-checker-qual"),
      dep("org.checkerframework:checker-qual:1.2.3")
        .target("thrift-and-checker-qual")
    ),
    List(
      Addition(
        true,
        List("3rdparty/jvm/org/scalameta:munit"),
        "org.checkerframework:checker-qual:1.2.3"
      ),
      Force(
        true,
        List("3rdparty/jvm/org/scalameta:munit"),
        "org.apache.thrift:libthrift",
        "0.10.0"
      )
    )
  )

}
