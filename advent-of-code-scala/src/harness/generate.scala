package harness

import core.text.fansi.*

import harness.templates.{compileDayImplTemplate, compileDayTestTemplate, compileYearTemplate}

val wd = os.pwd / "src" / "solutions"

def createYearDir(year: Year): Unit = {
  val yearFragment = f"y$year%04d"
  val yearDir = wd / yearFragment

  if (!os.exists(yearDir)) {
    os.makeDir(yearDir)
    println(f"Created ${y(yearDir.toString)}.")

    val yearTemplate = compileYearTemplate(year)

    os.write(yearDir / f"$yearFragment.scala", yearTemplate)

    println("Initialized year template.")
  } else {
    println(f"Skipping creating dir ${y(yearDir.toString)}; it already exists.")
  }
}

def createDayFiles(year: Year, day: Day): Unit = {
  val yearFragment = f"y$year%04d"
  val yearDir = wd / yearFragment

  val dayFragment = f"day$day%02d"

  val dayImplFile = yearDir / f"$dayFragment.scala"
  val dayTestFile = yearDir / f"$dayFragment.test.scala"

  createYearDir(year)

  if (!os.exists(dayImplFile)) {
    val dayImplTemplate = compileDayImplTemplate(year, day)

    os.write(dayImplFile, dayImplTemplate)

    println(f"Created day impl template at ${y(dayImplFile.toString)}")
  } else {
    println(f"Day impl file ${y(dayImplFile.toString)} already exists!")
  }

  if (!os.exists(dayTestFile)) {
    val dayTestTemplate = compileDayTestTemplate(year, day)

    os.write(dayTestFile, dayTestTemplate)

    println(f"Created day test template at ${y(dayTestFile.toString)}")
  } else {
    println(f"Day test file ${y(dayTestFile.toString)} already exists!")
  }
}
