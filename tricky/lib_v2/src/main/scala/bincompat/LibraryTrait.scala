package bincompat

trait LibraryTrait {
  def defaultValues(name: String = "version 2"): Unit = println(s"Hello $name!")
}
