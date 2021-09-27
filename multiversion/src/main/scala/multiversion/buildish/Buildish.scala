package multiversion.buildish

import java.nio.file.Files
import java.nio.file.Path

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer

import com.google.common.collect.ImmutableMap
import multiversion.configs.DependencyConfig
import multiversion.configs.ThirdpartyConfig
import multiversion.configs.VersionsConfig
import net.starlark.java.eval.Module
import net.starlark.java.eval.Mutability
import net.starlark.java.eval.Starlark
import net.starlark.java.eval.StarlarkSemantics
import net.starlark.java.eval.StarlarkThread
import net.starlark.java.syntax.FileOptions
import net.starlark.java.syntax.ParserInput

// This uses Starlark to provide BUILDish DSL.
object Buildish {
  def eval(expr: String): (List[JarLibraryDef], List[VersionsConfig]) = {
    val input = ParserInput.fromString(expr, "<expr>")
    eval(input)
  }

  def eval(input: ParserInput): (List[JarLibraryDef], List[VersionsConfig]) = {
    val defs = ListBuffer.empty[JarLibraryDef]
    val scalaConfigs = ListBuffer.empty[VersionsConfig]
    val env = makeEnvironment(defs, scalaConfigs)
    val module = Module.withPredeclared(StarlarkSemantics.DEFAULT, env)
    withMutability(input.getFile) { mu =>
      val thread = new StarlarkThread(mu, StarlarkSemantics.DEFAULT)
      Starlark.execFile(input, FileOptions.DEFAULT, module, thread)
    }
    (defs.toList, scalaConfigs.toList)
  }

  def evalRecursive(dir: Path, relativeTo: Path): ThirdpartyConfig = {
    var scalaConfig: VersionsConfig = VersionsConfig("")
    val deps = listBuildFiles(dir).flatMap { file =>
      val input = ParserInput.readFile(file.toAbsolutePath.toString)
      val (defs, scalaConfigs) = eval(input)
      if (scalaConfigs.nonEmpty) {
        scalaConfig = scalaConfigs.head
      }
      val pkg = relativeTo.relativize(file).getParent()
      defs.map(d => d.copy(targets = List(s"$pkg/${d.name}")))
    }
    ThirdpartyConfig(
      dependencies = deps.flatMap(toDependencyConfigs),
      scala = scalaConfig,
    )
  }

  private def toDependencyConfigs(jarLib: JarLibraryDef): List[DependencyConfig] =
    jarLib.jars map { jar =>
      jar.copy(
        targets = jarLib.targets,
        versionPattern = jarLib.version_pattern,
      )
    }

  private val buildFileSet = Set("BUILD", "BUILD.bmv")
  private def listBuildFiles(dir: Path): List[Path] =
    Files
      .walk(dir)
      .filter(file => Files.isRegularFile(file) && buildFileSet(file.getFileName().toString))
      .iterator()
      .asScala
      .toList
      .sorted

  // close mutability to freeze the values
  private def withMutability(fileName: String)(f: Mutability => Unit): Unit = {
    val mu = Mutability.create(fileName)
    try {
      f(mu)
    } finally {
      mu.close()
    }
  }

  private def makeEnvironment(
      defs: ListBuffer[JarLibraryDef],
      scalaConfigs: ListBuffer[VersionsConfig]
  ): ImmutableMap[String, AnyRef] = {
    val env = ImmutableMap.builder[String, AnyRef]()
    Starlark.addMethods(env, new Functions(defs, scalaConfigs), StarlarkSemantics.DEFAULT)
    env.build()
  }
}
