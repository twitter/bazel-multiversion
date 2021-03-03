package tests

trait ConfigSyntax {

  def deps(dependencies: ConfigNode.Dependency*): String = {
    val clausesStr = dependencies.map(_.toYaml).mkString(System.lineSeparator())
    s"""|  dependencies:
        |$clausesStr
        |""".stripMargin
  }

  def dep(str: String): ConfigNode.Dependency =
    str.split(":").toList match {
      case org :: name :: version :: Nil => ConfigNode.Dependency(org, name, version, Nil, Nil, Nil)
      case _                             => throw new IllegalArgumentException(str)
    }
}

sealed trait ConfigNode {
  def toYaml: String
}

object ConfigNode {

  case class Dependency(
      organization: String,
      name: String,
      version: String,
      dependencies: List[String],
      exclusions: List[Exclusion],
      targets: List[String]
  ) extends ConfigNode {

    def exclude(exclusion: String): Dependency =
      copy(exclusions = Exclusion(exclusion) :: exclusions)

    def target(target: String): Dependency =
      copy(targets = target :: targets)

    def dependency(target: String): Dependency =
      copy(dependencies = target :: dependencies)

    def canonical: Dependency = {
      val orgPath = organization.replaceAllLiterally(".", "/")
      target(s"3rdparty/jvm/$orgPath:$name")
    }

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
