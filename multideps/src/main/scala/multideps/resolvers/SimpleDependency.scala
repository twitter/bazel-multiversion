package multideps.resolvers

import scala.util.hashing.MurmurHash3

case class SimpleDependency(
    module: SimpleModule,
    version: String
) {
  def repr = s"${module.repr}:$version"
  override lazy val hashCode = MurmurHash3.productHash(this)
}
