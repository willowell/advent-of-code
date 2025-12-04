package core.data.colour

import core.parser.prelude.hexInt32

import java.lang.{Short as JShort}

import parsley.Parsley
import parsley.token.Lexer
import parsley.token.descriptions.*
import parsley.character.*
import parsley.combinator.*

import spire.math.UByte

case class RGB(r: UByte, g: UByte, b: UByte)

object RGB {
  object Parsers {
    val hexadecimalRGB: Parsley[RGB] = for {
      _ <- char('#')
      r <- exactly(2, hexDigit).map { xs => xs.mkString.toUByteFromHex }
      g <- exactly(2, hexDigit).map { xs => xs.mkString.toUByteFromHex }
      b <- exactly(2, hexDigit).map { xs => xs.mkString.toUByteFromHex }
    } yield RGB(r, g, b)
  }
}

extension (self: String) {
  def toUByteFromHex: UByte = UByte(JShort.parseShort(self, 16))
}

/** deperecated: replace with RGB.Parsers.hexadecimalRGB */
def pHexRgb: Parsley[RGB] = for {
  _ <- char('#')
  r <- exactly(2, hexDigit).map { xs => xs.mkString.toUByteFromHex }
  g <- exactly(2, hexDigit).map { xs => xs.mkString.toUByteFromHex }
  b <- exactly(2, hexDigit).map { xs => xs.mkString.toUByteFromHex }
} yield RGB(r, g, b)
