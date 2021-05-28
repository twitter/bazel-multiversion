package tests

trait ConfigSyntax {

  def deps(dependencies: ConfigNode.Dependency*): String = {
    dependencies.map(_.toYaml).mkString(System.lineSeparator())
  }

  def overrideTargets(overrides: (String, String)*): String = {
    s"""|
        |overrideTargets:
        |${overrides.map(overrideYaml).mkString(System.lineSeparator())}
        |""".stripMargin
  }

  def dep(str: String): ConfigNode.Dependency =
    str.split(":").toList match {
      case org :: name :: version :: Nil =>
        ConfigNode.Dependency(
          org,
          name,
          version,
          url = None,
          classifier = None,
          dependencies = Nil,
          exclusions = Nil,
          targets = Nil,
          force = true,
          transitive = true,
          versionPattern = None
        )
      case _ => throw new IllegalArgumentException(str)
    }

  private def overrideYaml(or: (String, String)): String =
    s"""|  - from: '${or._1}'
        |    to: '${or._2}'
        |""".stripMargin
}

sealed trait ConfigNode {
  def toYaml: String
}

object ConfigNode {

  case class Dependency(
      organization: String,
      name: String,
      version: String,
      url: Option[String],
      classifier: Option[String],
      dependencies: List[String],
      exclusions: List[Exclusion],
      targets: List[String],
      force: Boolean,
      transitive: Boolean,
      versionPattern: Option[String]
  ) extends ConfigNode {

    def url(url: String): Dependency =
      copy(url = Option(url))

    def classifier(classifier: String): Dependency =
      copy(classifier = Option(classifier))

    def exclude(exclusion: String): Dependency =
      copy(exclusions = Exclusion(exclusion) :: exclusions)

    def target(target: String): Dependency =
      copy(targets = target :: targets)

    def dependency(target: String): Dependency =
      copy(dependencies = target :: dependencies)

    def force(value: Boolean): Dependency =
      copy(force = value)

    def transitive(value: Boolean): Dependency =
      copy(transitive = value)

    def versionPattern(value: String): Dependency =
      copy(versionPattern = Option(value))

    def toYaml: String = {
      val exclusionsStr =
        if (exclusions.isEmpty) ""
        else {
          val clausesStr = exclusions.reverse.map(_.toYaml).mkString(System.lineSeparator())
          s"""|    exclusions:
          |$clausesStr
          |""".stripMargin
        }
      val dependenciesStr =
        if (dependencies.isEmpty) ""
        else {
          val clausesStr =
            dependencies.reverse.map(d => s"      - $d").mkString(System.lineSeparator())
          s"""|    dependencies:
          |$clausesStr
          |""".stripMargin
        }
      val targetsStr =
        if (targets.isEmpty) ""
        else {
          val clausesStr = targets.reverse.map(t => s"      - $t").mkString(System.lineSeparator())
          s"""|    targets:
          |$clausesStr
          |""".stripMargin
        }

      s"""|  - dependency: $organization:$name:$version
          |    url: ${url.orNull}
          |    classifier: ${classifier.orNull}
          |    force: $force
          |    transitive: $transitive
          |    versionPattern: ${versionPattern.orNull}
          |$exclusionsStr
          |$dependenciesStr
          |$targetsStr
          |""".stripMargin
    }
  }

  case class Exclusion(
      organization: String,
      name: String
  ) extends ConfigNode {
    override def toYaml: String =
      s"""|      - organization: $organization
        |        name: '$name'
        |""".stripMargin
  }

  object Exclusion {
    def apply(str: String): Exclusion =
      str.split(":").toList match {
        case org :: name :: Nil => Exclusion(org, name)
        case _                  => throw new IllegalArgumentException(str)
      }
  }
}
