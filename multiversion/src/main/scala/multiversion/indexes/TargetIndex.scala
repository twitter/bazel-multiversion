package multiversion.indexes

import scala.collection.JavaConverters._
import scala.util.matching.Regex

import com.twitter.multiversion.Build.Target
import coursier.core.Module
import coursier.core.ModuleName
import coursier.core.Organization
import multiversion.resolvers.SimpleDependency
import multiversion.resolvers.SimpleModule

case class TargetIndex(
    name: String,
    deps: List[String],
    tags: List[String],
    dependency: Option[SimpleDependency]
)

object TargetIndex {
  private object JvmVersion {
    private val Version: Regex = "jvm_version=(.+)".r
    def unapply(s: String): Option[String] =
      s match {
        case Version(version) => Some(version)
        case _                => None
      }
  }
  private object JvmModule {
    private val OrgModule: Regex = "jvm_module=([^:]+):([^:]+)".r
    def unapply(s: String): Option[Module] =
      s match {
        case OrgModule(org, module) =>
          Some(Module(Organization(org), ModuleName(module), Map.empty))
        case _ => None
      }
  }
  private object JvmClassifier {
    private val Classifier: Regex = "jvm_classifier=(.+)".r
    def unapply(s: String): Option[String] =
      s match {
        case Classifier(x) => Some(x)
        case _             => None
      }
  }
  def fromQuery(t: Target): TargetIndex = {
    def getStringAttribute(key: String): Option[String] =
      t.getRule().getAttributeList.asScala.find(_.getName() == key).map(_.getStringValue())
    def getStringListAttribute(key: String): List[String] =
      (for {
        attr <- t.getRule().getAttributeList().asScala.iterator
        if attr.getName() == key
        dep <- attr.getStringListValueList().asScala.iterator
      } yield dep).toList
    val deps =
      if (t.getRule.getRuleClass() == "alias")
        getStringAttribute("actual").map(List(_)).getOrElse(Nil)
      else getStringListAttribute("deps")
    val tags = getStringListAttribute("tags")
    val module = tags.collectFirst { case JvmModule(module) =>
      module
    }
    val version = tags.collectFirst { case JvmVersion(version) =>
      version
    }
    val classifier = tags.collectFirst { case JvmClassifier(classifier) =>
      classifier
    }
    val dependency = (module, version) match {
      case (Some(m), Some(v)) =>
        Some(
          SimpleDependency(SimpleModule(m.organization.value, m.name.value), v, classifier)
        )
      case _ => None
    }
    TargetIndex(
      name = t.getRule().getName(),
      deps = deps,
      tags = tags,
      dependency = dependency
    )
  }
}
