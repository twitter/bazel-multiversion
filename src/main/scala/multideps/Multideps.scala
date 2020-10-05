package multideps

import coursier.core.Dependency
import coursier.core.Module
import coursier.core.Organization
import coursier.core.ModuleName
import coursier.params.ResolutionParams
import coursier.params.rule.RuleResolution
import coursier.params.rule.Rule
import coursier.core.Resolution
import coursier.error.ResolutionError
import coursier.error.conflict.UnsatisfiedRule
import java.net.URL
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.URI
import java.nio.file.Paths
import org.scalameta.bazel_multideps.Build.Target.Discriminator

object Multideps {
  val picocli = Module(
    Organization("info.picocli"),
    ModuleName("picocli"),
    Map.empty
  )
  val guava = Module(
    Organization("com.google.guava"),
    ModuleName("guava"),
    Map.empty
  )
  val lsp4j = Module(
    Organization("org.eclipse.lsp4j"),
    ModuleName("org.eclipse.lsp4j"),
    Map.empty
  )
  val mockitoCore = Module(
    Organization("org.mockito"),
    ModuleName("mockito-core"),
    Map.empty
  )
  val mockitoAll = Module(
    Organization("org.mockito"),
    ModuleName("mockito-all"),
    Map.empty
  )
  case class MockitoRule() extends Rule {
    type C = MockitoConflict
    def check(res: Resolution): Option[MockitoConflict] = {
      val isBothMockitoModules =
        res.retainedVersions.contains(mockitoAll) &&
          res.retainedVersions.contains(mockitoCore)
      if (isBothMockitoModules) {
        Some(new MockitoConflict(this))
      } else {
        None
      }
    }
    def tryResolve(
        res: Resolution,
        conflict: C
    ): Either[ResolutionError.UnsatisfiableRule, Resolution] = {
      Right(res)
    }
  }
  final class MockitoConflict(
      override val rule: MockitoRule
  ) extends UnsatisfiedRule(
        rule,
        s"mockito-core and mockito-all can't be on the resolution. To fix this problem, ..."
      ) {}
  def main(args: Array[String]): Unit = {
    val cwd = Paths.get(".").toAbsolutePath().normalize()
    val command = List(
      "bazel",
      "query",
      "--notool_deps",
      "--noimplicit_deps",
      "deps(tricky/user/src/main/scala/bincompat:NeedsVersion1)",
      "--output=proto"
    )
    val x = os.proc(command).call(cwd = os.Path(cwd))
    val query =
      org.scalameta.bazel_multideps.Build.QueryResult.parseFrom(x.out.bytes)
    import scala.collection.JavaConverters._
    val names = query
      .getTargetList()
      .asScala
      .filter(_.getType() == Discriminator.RULE)
      .map(t =>
        t.getRule().getName() -> t
          .getRule()
          .getAttributeList()
          .asScala
          .filter(_.getName() == "deps")
          .flatMap(_.getStringListValueList().asScala)
      )
    pprint.log(names)
    // scala.sys.process.Process(command, cwd = Some(cwd.toFile()))
    // val queryProcess =
    //   new ProcessBuilder(command.toArray: _*).directory(cwd.toFile()).start()
  }
  def app(): Unit = {
    val jars = coursier
      .Resolve()
      .addDependencies(
        Dependency(mockitoAll, "1.10.19"),
        Dependency(mockitoCore, "3.5.13")
        // Dependency(guava, "25.0-jre"),
        // Dependency(lsp4j, "0.9.0")
      )
      .withResolutionParams(
        ResolutionParams()
          .withForceVersion(
            Map(
              guava -> "25.0-jre"
            )
          )
      )
    jars.either() match {
      case Left(value) =>
        println(value)
        sys.exit(1)
      case Right(jars) =>
        val x = jars.dependencyArtifacts().map {
          case (d, _, a) =>
            val sha = requests.get(a.checksumUrls("SHA-1")).text()
            println(d)
            val name =
              s"${d.module.repr}_${d.version}".replaceAll("[^a-zA-Z0-9]", "_")
            s"""|http_file(
                |  name = "${name}_jar",
                |  urls = ["${a.url}"],
                |  sha256 = "${sha}",
                |)
                |java_import(
                |  name = ["${name}"],
                |  jars = [":${name}_jar"],
                |)
                |""".stripMargin
        }
        println(x.mkString("\n"))
    }
  }
}
