package multideps.resolvers

import scala.util.hashing.MurmurHash3

case class SimpleModule(
    org: String,
    name: String
) {
  def repr = s"$org:$name"
  override lazy val hashCode = MurmurHash3.productHash(this)
}
