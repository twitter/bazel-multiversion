package multiversion.configs

import scala.util.matching.Regex

import coursier.core.Classifier
import coursier.core.Configuration
import coursier.core.Dependency
import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import coursier.core.Publication
import coursier.core.Type
import coursier.version.VersionCompatibility
import moped.json.DecodingContext
import moped.json.ErrorResult
import moped.json.JsonCodec
import moped.json.JsonDecoder
import moped.json.JsonElement
import moped.json.JsonObject
import moped.json.JsonString
import moped.json.Result
import moped.json.ValueResult
import moped.macros.ClassShape
import moped.reporters.Diagnostic
import multiversion.configs.MultidepsJsonDecoders.jsonStringDecoder
import multiversion.resolvers.DependencyId
import net.starlark.java.eval.StarlarkValue

final case class DependencyConfig(
    organization: JsonString = JsonString(""),
    name: String = "",
    version: String = "",
    url: Option[String] = None,
    classifier: Option[String] = None,
    exclusions: Set[ModuleConfig] = Set.empty,
    crossVersions: List[CrossVersionsConfig] = Nil,
    forceVersions: ForceVersionsConfig = ForceVersionsConfig(),
    modules: List[String] = Nil,
    lang: LanguagesConfig = JavaLanguagesConfig,
    dependencies: List[String] = Nil,
    exports: List[String] = Nil,
    targets: List[String] = Nil,
    versionScheme: Option[VersionCompatibility] = None,
    versionPattern: Option[String] = None,
    force: Boolean = true,
    transitive: Boolean = true
) extends StarlarkValue {

  val id: DependencyId =
    DependencyId(
      organization.value,
      name,
      version,
      classifier,
      dependencies,
      exclusions
    )

  val versionExtractor: Option[String => String] = versionPattern.map { pattern => v =>
    pattern.r.findAllMatchIn(v).flatMap(_.subgroups).mkString(".")
  }

  val suffix: String = "_" + id.##

  def toCoursierDependency(scalaVersion: VersionsConfig): Dependency =
    Dependency(
      module = coursierModule(scalaVersion),
      version = version,
      configuration = Configuration.empty,
      exclusions = exclusions.map(e => e.coursierModule.organization -> e.coursierModule.name),
      publication = classifier match {
        case Some(c) =>
          Publication.empty.withClassifier(Classifier(c)).withType(Type("jar"))
        case _ => Publication.empty
      },
      optional = false,
      transitive = transitive
    )

  val classifierRepr: String = classifier match {
    case Some(value) => s"_$value"
    case None        => ""
  }
  def coursierModule(scalaVersion: VersionsConfig): Module = {
    val suffix = lang match {
      case JavaLanguagesConfig => ""
      case ScalaLanguagesConfig =>
        if (scalaVersion.binaryVersion == "") sys.error("Unspecified Scala version")
        else "_" + scalaVersion.binaryVersion
      case ScalaCompilerLanguagesConfig => "_" + scalaVersion.default.value
    }
    Module(
      Organization(organization.value),
      ModuleName(name + suffix),
      Map.empty
    )
  }
  def allVersions: List[String] =
    version :: crossVersions.map(_.version.value)
  def getVersion(key: String): Option[String] =
    if (key == "default") Some(version)
    else crossVersions.find(_.name.value == key).map(_.version.value)
  def coursierDependencies(scalaVersion: VersionsConfig): List[Dependency] = {
    val coursierDep = toCoursierDependency(scalaVersion)
    allVersions.map(v => coursierDep.withVersion(v))
  }
}

object DependencyConfig {
  implicit val versionSchemeCodec: JsonCodec[VersionCompatibility] =
    new JsonCodec[VersionCompatibility] {
      def decode(
          context: DecodingContext
      ): Result[VersionCompatibility] =
        JsonDecoder.stringJsonDecoder.decode(context).flatMap {
          case "semver" => ValueResult(VersionCompatibility.SemVerSpec)
          case other =>
            VersionCompatibility(other).map(ValueResult(_)).getOrElse {
              ErrorResult(
                Diagnostic.error(
                  s"invalid version scheme '$other'",
                  context.json.position
                )
              )
            }
        }
      def encode(value: VersionCompatibility): JsonElement =
        JsonString(value.name)
      def shape: ClassShape = ClassShape.empty

    }
  private val Full: Regex = "(.+):::(.+):(.+)".r
  private val Half: Regex = "(.+)::(.+):(.+)".r
  private val Java: Regex = "(.+):(.+):(.+)".r
  private object FromJsonString {
    def unapply(s: JsonString): Option[DependencyConfig] = {
      def json(value: String) = JsonString(value).withPosition(s.position)
      s.value match {
        case Full(org, artifact, version) =>
          Some(
            DependencyConfig(
              json(org),
              artifact,
              version = version,
              lang = ScalaCompilerLanguagesConfig
            )
          )
        case Half(org, artifact, version) =>
          Some(
            DependencyConfig(
              json(org),
              artifact,
              version = version,
              lang = ScalaLanguagesConfig
            )
          )
        case Java(org, artifact, version) =>
          Some(
            DependencyConfig(
              json(org),
              artifact,
              version = version
            )
          )
        case _ => None

      }
    }
  }
  val default: DependencyConfig = DependencyConfig()
  def automaticCodec(d: DependencyConfig): JsonCodec[DependencyConfig] =
    moped.macros.deriveCodec(d)
  val automaticCodec: JsonCodec[DependencyConfig] =
    automaticCodec(default)
  implicit val codec: JsonCodec[DependencyConfig] =
    new JsonCodec[DependencyConfig] {
      def decode(context: DecodingContext): Result[DependencyConfig] = {
        context.json match {
          case FromJsonString(dep) => ValueResult(dep)
          case obj: JsonObject =>
            obj.value.get("dependency") match {
              case Some(FromJsonString(dep)) =>
                val newJson = JsonObject(
                  obj.members.filterNot(_.key.value == "dependency")
                )
                automaticCodec(dep).decode(context.withJson(newJson))
              case _ =>
                automaticCodec.decode(context)
            }
          case _ =>
            automaticCodec.decode(context)
        }
      }
      def encode(value: DependencyConfig): JsonElement =
        automaticCodec.encode(value)
      def shape: ClassShape = automaticCodec.shape
    }

}
