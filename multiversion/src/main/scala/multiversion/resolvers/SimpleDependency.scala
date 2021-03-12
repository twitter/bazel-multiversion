package multiversion.resolvers

import scala.util.hashing.MurmurHash3

import com.twitter.multiversion.Build.Target

case class SimpleDependency(
    module: SimpleModule,
    version: String,
    classifier: Option[String],
) {
  private[this] def classifierStr: String =
    classifier match {
      case Some(x) => s"-$x"
      case _       => ""
    }
  // https://repo1.maven.org/maven2/org/apache/kafka/kafka-clients/2.4.0/ lists
  // kafka-clients-2.4.0-test.jar
  def repr: String = s"${module.repr}:$version${classifierStr}"

  override lazy val hashCode: Int = MurmurHash3.productHash(this)
}

object SimpleDependency {
  def fromTarget(target: Target): Option[SimpleDependency] = {
    val targetName = target.getGeneratedFile.getName.stripPrefix("@maven//:").stripSuffix(".jar")
    targetName.split("/") match {
      case Array(org, name, version, classifier) =>
        Some(SimpleDependency(SimpleModule(org, name), version, Some(classifier)))
      case Array(org, name, version) =>
        Some(SimpleDependency(SimpleModule(org, name), version, None))
      case _ =>
        None
    }
  }
}
