package multiversion.loggers

import java.time.Clock
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference

import moped.progressbars.ProgressRenderer
import moped.progressbars.ProgressStep
import multiversion.outputs.Docs
import org.typelevel.paiges.Doc

class LintProgressRenderer(
    total: Int,
    clock: Clock,
    isTesting: Boolean
) extends ProgressRenderer {
  private val completed = new AtomicInteger(0)
  private val conflictsCount = new AtomicInteger(0)
  private val currentTarget = new AtomicReference[String]()

  def advance(current: String): Unit = {
    currentTarget.set(current)
    completed.incrementAndGet()
  }

  def reportConflict(conflicts: Int): Unit = {
    conflictsCount.addAndGet(conflicts)
  }

  override def renderStop(): Doc = {
    if (isTesting) Doc.empty
    else {
      val emoji = if (conflictsCount.get() == 0) Docs.emoji.success else Docs.emoji.error
      emoji + Doc.text(s"Linted $total targets; ${currentConflicts()}")
    }
  }

  override def renderStep(): ProgressStep = {
    val current = Option(currentTarget.get())
      .map(t => s" Linting $t")
      .getOrElse("")
    val percentage = 100 * completed.get() / total
    ProgressStep()
      .withDynamic(
        Doc.text(s"${completed.get()}/${total} ($percentage%)$current; ${currentConflicts()}")
      )
  }

  private def currentConflicts(): String = s"found $conflictsCount conflicts"
}
