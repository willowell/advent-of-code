package harness.templates

import harness.{Day, Year}

import scala.io.Source

val templatesPath = "templates"

val yearTemplate: String = Source.fromResource(f"$templatesPath/year.hbs").mkString

val dayImplTemplate: String = Source.fromResource(f"$templatesPath/dayImpl.hbs").mkString

val dayTestTemplate: String = Source.fromResource(f"$templatesPath/dayTest.hbs").mkString

def compileYearTemplate(year: Year): String = {
  yearTemplate.replace("{{year}}", f"$year%04d").nn
}

def compileDayImplTemplate(year: Year, day: Day): String = {
  dayImplTemplate
    .replace("{{year}}", f"$year%04d").nn
    .replace("{{day}}", f"$day%d").nn
}

def compileDayTestTemplate(year: Year, day: Day): String = {
  dayTestTemplate
    .replace("{{year}}", f"$year%04d").nn
    .replace("{{day}}", f"$day%d").nn
}
