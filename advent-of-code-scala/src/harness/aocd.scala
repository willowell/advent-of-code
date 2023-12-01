package harness

import core.text.fansi.*

import sttp.client4.quick.*
import sttp.client4.Response
import sttp.model.Uri

object Api {
  val USER_AGENT = "advent-of-code-scala-v1"

  def mkUrl(year: Int, day: Int): Uri = uri"https://adventofcode.com/$year/day/$day"
  
  def mkInputUrl(year: Int, day: Int): Uri = uri"${mkUrl(year, day)}/input"

  def getData(year: Int, day: Int, token: String): Response[String] = quickRequest
    .get(Api.mkInputUrl(year, day))
    .cookie("session" -> token)
    .header("User-Agent", Api.USER_AGENT)
    .send()
}

case class TokenNotFound() extends Exception(TokenNotFound.msg) {}

object TokenNotFound {
  val msg = """You must provide your Advent of Code session token as environment variable
as AOC_SESSION_TOKEN or in ~/.aocd/token"""
}

object Aocd {
  val TOKEN_ENV = "AOC_SESSION_TOKEN"

  val aocdDir = os.pwd / ".aocd"

  val wd = os.pwd / "resources" / "input"

  def getTokenFile(dir: os.Path): os.Path = dir / "token.txt"

  def getTokenFrom(tokenFile: os.Path, envvar: String = TOKEN_ENV): String = scala.sys.env.get(envvar).getOrElse {
    if (os.exists(tokenFile)) {
      os.read(tokenFile).trim().nn
    } else {
      throw TokenNotFound()
    }
  }

  def dayInputPath(year: Int, day: Int) = {
    val padDay = f"${day}%02d"

    wd / f"$year%04d" / f"day${padDay}.txt"
  }

  def resourceExists(path: String = ""): Boolean = {
    val uri = this.getClass().getResource(path)

    uri match {
      case _: java.net.URL => true
      case null            => false
    }
  }

  def writeInputFile(year: Int, day: Int, content: String): Unit = {
    val path = dayInputPath(year, day)

    val yearFragment = f"$year%04d"
    val yearDir = wd / yearFragment

    if (!os.exists(yearDir)) {
      os.makeDir(yearDir)

      println(f"Created ${y(yearDir.toString)}.")
    } else {
      println(f"Skipping creating dir ${y(yearDir.toString)}; it already exists.")
    }

    if (!os.exists(path)) {
      os.write(path, content)

      println(f"Created ${y(path.toString)} and wrote content to it.")
    } else {
      println(f"Skipping writing to ${y(path.toString)}; it already exists.")
    }
  }

  def fetchDayInputContent(year: Int, day: Int): String = {
    val token = getTokenFrom(getTokenFile(aocdDir))

    val res = Api.getData(year, day, token)

    val content = res.body

    content
  }

  def downloadToInputFile(year: Int, day: Int): Unit = {
    println(f"Downloading content for Year $year%04d - Day $day%02d.")

    val content = fetchDayInputContent(year, day)

    println(f"Downloaded content; writing it to file now.")

    writeInputFile(year, day, content)
  }
}
