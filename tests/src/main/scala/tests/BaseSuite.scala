package tests

import moped.testkit.MopedSuite
import multideps.Multideps

abstract class BaseSuite extends MopedSuite(Multideps.app) {}
