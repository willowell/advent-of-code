package common

export core.control.combinators.{fst, snd, |>}

export core.data.freqmap.{FreqMap, toFreqMap}
export core.data.linear.v2.V2
export core.data.seq.{eitherWholyContains, overlaps, rotate90, splitHalf}

export core.parser.prelude.{
  nnSplit,
  nnTrim,

  int32,
  nat32,
  hexInt32,
  double,

  englishVowel,
  specialCharacter,
  fullLine,
  anStr,
  asciiChar,
  asciiStr,
  word,

  colon,
  comma,
  dash,

  sepByEol1,
  sepByEol2,

  strSepByEol,
  strSepByDoubleEol,

  splitByEol,
  splitByEol2,

  csi,

  countParseSuccesses,
  countParseFailures,
}

export cats.effect.{IO, IOApp}

export fs2.{Pure, Stream}

export monocle.{Getter, Lens, Setter}

export parsley.Parsley
export parsley.Result
export parsley.Success
export parsley.Failure

export parsley.character.{
  char,
  endOfLine,
  string,
  space,
  whitespace,
}

export parsley.combinator.{
  eof,
  many,
  manyUntil,
  some,
  someUntil,
  traverse,
}

export parsley.lift.{
  lift1 as plift1,
  lift2 as plift2,
  lift3 as plift3,
  lift4 as plift4,
}

export annotation.{switch, tailrec}

export collection.mutable.ArrayBuffer
export collection.mutable.HashMap

export util.matching.Regex
