package multiversion.configs

sealed trait Changes[T] extends Any {
  def value: T
  def changed: Boolean
}
object Changes {
  case class Changed[T](value: T) extends AnyVal with Changes[T] {
    override def changed: Boolean = true
  }
  case class Unchanged[T](value: T) extends AnyVal with Changes[T] {
    override def changed: Boolean = false
  }
}
