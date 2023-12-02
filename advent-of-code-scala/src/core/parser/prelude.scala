/// Parser Prelude
///
///
package core.parser.prelude

import parsley.Parsley
import parsley.token.Lexer
import parsley.token.descriptions.*
import parsley.character.*
import parsley.combinator.*
import parsley.errors.combinator.*
import parsley.Result

import annotation.switch

extension (s: String) {
  def nnSplit(regex: String): Array[String] = s.split(regex).nn.map { _.nn }

  def nnTrim: String = s.trim.nn
}

val lexer: Lexer = Lexer(LexicalDesc.plain)

val int32: Parsley[Int] = lexer.nonlexeme.numeric.integer.decimal32

val nat32: Parsley[?] = lexer.nonlexeme.numeric.natural.decimal32

val hexInt32: Parsley[Int] = lexer.nonlexeme.numeric.integer.hexadecimal32

val double: Parsley[Double] = lexer.nonlexeme.numeric.floating.doubleRounded

def isSpecialCharacter(c: Char): Boolean = (c: @switch) match {
  case (
    '`' | '~' | '!' | '@' | '#' | '$' | '%' |
    '^' | '&' | '*' | '(' | ')' | '=' | '+' |
    '[' | ']' | '{' | '}' | ';' | ':' | '\'' |
    '"' | ',' | '.' | '<' | '>' | '/' | '\\' |
    '|' | '?'
  ) => true
  case _ => false
}

def isEnglishVowel(c: Char): Boolean = (c: @switch) match {
  case (
    'a' | 'e' | 'i' | 'o' | 'u' |
    'A' | 'E' | 'I' | 'O' | 'U'
  ) => true
  case _ => false
}


val englishVowel: Parsley[Char] = satisfy { isEnglishVowel } ? "English vowel (aeiou)"

val specialCharacter: Parsley[Char] = satisfy { isSpecialCharacter } ? "digit"

val fullLine: Parsley[String] = stringOfSome(letterOrDigit <|> space)

/** Alphanumeric string parser.
  * 
  */
val anStr: Parsley[String] = stringOfSome(letterOrDigit)

val asciiChar: Parsley[Char] = letterOrDigit <|> specialCharacter <|> space

val asciiStr: Parsley[String] = stringOfSome(letterOrDigit <|> specialCharacter)

val word: Parsley[String] = stringOfSome(letter)

val comma: Parsley[Char] = char(',')

val colon: Parsley[Char] = char(':')

val dash: Parsley[Char] = char('-')

val dollar: Parsley[Char] = char('$')


def sepByEol1[A](p: Parsley[A]) = sepEndBy1(p, endOfLine)

def sepByEol2[A](p: Parsley[A]) = sepByEol1(sepByEol1(p))

val strSepByEol: Parsley[List[String]] = sepByEol1(anStr)

val strSepByDoubleEol: Parsley[List[List[String]]] = sepByEol2(anStr)

def splitByEol(s: String): List[String] = s.nnSplit("\n").toList

def splitByEol2(s: String): List[String] = s.nnSplit("\n\n").toList

/** ***UNSAFE*** Parse a string of comma-separated integers */
def csi(s: String): List[Int] = s.nnTrim.nnSplit(",").map { _.toInt }.toList

def countParseSuccesses(xs: Seq[Result[?, ?]]): Int = xs.count { _.isSuccess }

def countParseFailures(xs: Seq[Result[?, ?]]): Int = xs.count { _.isFailure }

/**
  * Inverse of `between`. Parses `pa` and `pb` into a tuple, separated by `pc`.
  * 
  * @example ```
  * around(digit, digit, char(',')).parse("3,3") // => Success(('3', '3'))
  * ```
  *
  * @param pa
  * @param pb
  * @param pc
  * @return
  */
def around[A, B, C](pa: Parsley[A], pb: Parsley[B], pc: Parsley[C]): Parsley[(A, B)] = for {
  first <- pa
  _ <- pc
  second <- pb
} yield (first, second)
