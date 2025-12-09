/// Day 12: JSAbacusFramework.io
///
///
package solutions.y2015.day12

import solutions.y2015.year

import harness.*

import io.circe.*
import io.circe.parser.*

import util.matching.Regex

given day: Day = Day(12)

object run extends AocDayRunner(part1, part2)

class SumSomeNumsFolder extends Json.Folder[Int] {
  override def onNull: Int = 0

  override def onBoolean(value: Boolean): Int = 0

  override def onString(value: String): Int = 0

  override def onNumber(value: JsonNumber): Int = value.toInt.getOrElse { 0 }

  override def onObject(value: JsonObject): Int = value.toMap.foldLeft(0) { (acc, kv) => acc + kv._2.foldWith(this) }

  override def onArray(value: Vector[Json]): Int = value.foldLeft(0) { (acc, cur) => acc + cur.foldWith(this) }
}

class SumSomeNumsNoRedsFolder extends Json.Folder[Int] {
  override def onNull: Int = 0

  override def onBoolean(value: Boolean): Int = 0

  override def onString(value: String): Int = 0

  override def onNumber(value: JsonNumber): Int = value.toInt.getOrElse { 0 }

  override def onObject(value: JsonObject): Int = {
    val kvMap = value.toMap

    if kvMap.values.exists { v => v.asString.getOrElse("") == "red" }
      then 0
      else kvMap.foldLeft(0) { (acc, kv) => acc + kv._2.foldWith(this) }
  }

  override def onArray(value: Vector[Json]): Int = value.foldLeft(0) { (acc, cur) => acc + cur.foldWith(this) }
}

val intRegex = """-?\d+""".r

def part1(input: String): Int = part1WithCirce(input)

def part1WithCirce(input: String): Int = {
  parse(input).getOrElse(Json.Null).foldWith(SumSomeNumsFolder())
}

def part1WithRegex(input: String): Int = {
  intRegex.findAllMatchIn(input).foldLeft(0) { case (acc, cur) =>
    acc + cur.matched.toInt
  }
}

def part2(input: String): Int = {
  parse(input).getOrElse(Json.Null).foldWith(SumSomeNumsNoRedsFolder())
}
