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

/** Parse an `Int` */
val int32: Parsley[Int] = lexer.nonlexeme.integer.decimal32

/** Parse a `Long` */
val int64: Parsley[Long] = lexer.nonlexeme.integer.decimal64

/** Parse an unsigned `Int`, which can fit into `Long`.  */
val nat32: Parsley[Long] = lexer.nonlexeme.natural.decimal32

/** Parse an unsigned `Long` - I have no idea if this works? */
val nat64: Parsley[Long] = lexer.nonlexeme.natural.decimal64

val hexInt32: Parsley[Int] = lexer.nonlexeme.integer.hexadecimal32

val double: Parsley[Double] = lexer.nonlexeme.floating.doubleRounded

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

/** Parse an English vowel letter. (a, e, i, o, u) */
val englishVowel: Parsley[Char] = satisfy { isEnglishVowel } ? "English vowel (aeiou)"

/** Parse a special character. */
val specialCharacter: Parsley[Char] = satisfy { isSpecialCharacter } ? "special character"

/** Parse a newline-delimited line of alphanumeric text, including whitespace. */
val fullLine: Parsley[String] = stringOfSome(letterOrDigit <|> space)

/** Parse an alphanumeric string. Same as `fullLine` but does not parse whitespace. */
val anStr: Parsley[String] = stringOfSome(letterOrDigit)

/** Parse a single ASCII character. */
val asciiChar: Parsley[Char] = letterOrDigit <|> specialCharacter <|> space

/** Parse a string of ASCII characters. */
val asciiStr: Parsley[String] = stringOfSome(letterOrDigit <|> specialCharacter)

/** Parse a word, defined as a string of only letters. */
val word: Parsley[String] = stringOfSome(letter)

/** Parse a comma character. */
val comma: Parsley[Char] = char(',')

/** Parse a colon character. */
val colon: Parsley[Char] = char(':')

/** Parse a dash / hyphen character. */
val dash: Parsley[Char] = char('-')

/** Parse a dollar sign character. */
val dollar: Parsley[Char] = char('$')

/** Parse a pound sign character. */
val poundSign: Parsley[Char] = char('#')

//====================================================================================================================//
// COMBINATORS                                                                                                        //
//====================================================================================================================//

/** Parse one or more occurences of `p`, separated and optionally ended by an EOL. */
def sepByEol1[A](p: Parsley[A]) = sepEndBy1(p, endOfLine)

/**
 * Parse groups of one or more occurences of `p`, separated and optionally ended by an EOL,
 * where each group is also separated and optionally ended by an EOL.
 *
 * Meant for input like
 *
 * {{{
 * """
 * 1234
 * 5678
 *
 * 1234
 * 5678
 * """
 * }}}
 */
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
