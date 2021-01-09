package multiversion.resolvers

import scala.util.hashing.MurmurHash3

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
