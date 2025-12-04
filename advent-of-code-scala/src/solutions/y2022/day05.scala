/// Day 4: Supply Stacks
///
/// AKA I can't believe it's not Towers of Hanoi!
///
package solutions.y2022.day05

import solutions.y2022.year
import harness.*

import core.control.combinators.*
import core.parser.prelude.*

import parsley.*
import parsley.character.*
import parsley.combinator.*
import parsley.errors.combinator.*

given day: Day = Day(5)

object run extends AocDayRunner(part1, part2)

type Crate = Char

type Stack = (List[Crate], Int)

type Instruction = (Int, Int, Int)

case class Procedure(
  stacks: List[Stack],
  instructions: List[Instruction]
) {
  def prettyPrint(): Unit = {
    val instructionsPretty = instructions.map { (numCrates, from, to) =>
      f"move $numCrates from $from to $to"
    }.mkString("\n")

    val stacksPretty = stacks.map { (xs, i) =>
      f"$i: " ++ xs.map(x => f"[$x]").mkString(" ")
    }.mkString("\n")


    println(stacksPretty ++ "\n" ++ instructionsPretty)
  }
}

def prettyPrintStacks(stacks: List[Stack]): Unit = {
  val stacksPretty = stacks.map { (xs, i) =>
    f"$i: " ++ xs.map(x => f"[$x]").mkString(" ")
  }.mkString("\n")

  println(stacksPretty)
}

//====================================================================================================================//
// PARSERS                                                                                                            //
//====================================================================================================================//

val pCrate: Parsley[Option[Crate]] = choice(
  exactly(3, space) #> None,
  (char('[') *> letter <* char(']')).map { Some(_) }
) ? "crate"

val pStackRow: Parsley[List[Option[Crate]]] = sepBy(pCrate, space) ? "row of crates"

val pNumberLabels: Parsley[String] = fullLine <* endOfLine ? "number labels"

val pInstruction: Parsley[Instruction] = (for {
  _ <- string("move") ? "move"
  target <- (space *> int32 <* space) ? "num stacks to move"

  _ <- string("from") ? "from"
  from <- (space *> int32 <* space) ? "from num"

  _ <- string("to") ? "to"
  to <- space *> int32 ? "to num"
} yield (target, from, to)) ? "instruction"

val pInstructions: Parsley[List[Instruction]] = sepByEol1(pInstruction) ? "instructions list"

val pProcedure: Parsley[Procedure] = for {
  maybeStacks <- someTill(
    pStackRow <* endOfLine ? "stack row",
    pNumberLabels ? "stack number labels"
  ) ? "stacks"

  stacks = maybeStacks
    .reverse
    .transpose
    .map(_.flatten.reverse)
    .zipWithIndex
    .map { (xs, i) => (xs, i + 1) }

  _ <- endOfLine ? "empty line"

  instructions <- pInstructions ? "instructions"
} yield Procedure(stacks, instructions)

//====================================================================================================================//
// CRANE FUNCTIONS                                                                                                    //
//====================================================================================================================//

def replaceStack(stacks: List[Stack], stack: Int, newStack: List[Crate]): List[Stack] = {
  stacks.map {
    case s @ (_, i) => if i == stack then (newStack, i) else s
  }
}

def rearrange(stacks: List[Stack], numCrates: Int, from: Int, to: Int, reversePulled: Boolean): List[Stack] = {
  val fromIndex = from - 1
  val toIndex = to - 1

  val fromStack = stacks(fromIndex)
  val toStack = stacks(toIndex)

  val movedCrates = fromStack._1.take(numCrates)

  val oldStack = fromStack._1.drop(numCrates)

  val newStack = {
    (if reversePulled then movedCrates.reverse else movedCrates) ++ toStack._1
  }

  // println(f"NEW STACK: ")
  // println(newStack.map(x => f"[$x]").mkString(" "))

  val tempStack = replaceStack(stacks, from, oldStack)

  val withNewStack = replaceStack(tempStack, to, newStack)

  withNewStack
}

def getTopCrates(stacks: List[Stack]): List[Char] = stacks.map(_._1.head)

def runInstructions(reversePulled: Boolean, procedure: Procedure): List[Stack] = {
  val ins = procedure.instructions
  val startingStacks = procedure.stacks

  // println("STARTING STACKS:")
  // prettyPrintStacks(startingStacks)
  // println("=====================")

  ins.foldLeft(startingStacks) { case (curStacks, (numCrates, from, to))  =>
    // println("CURRENT STACKS:")
    // prettyPrintStacks(curStacks)
    // println("=====================")

    rearrange(curStacks, numCrates, from, to, reversePulled)
  }
}

//====================================================================================================================//
// THE ACTUAL PART FUNCTIONS                                                                                          //
//====================================================================================================================//

def part1(input: String): String = { // => "QNNTGTPFN"
  val res = pProcedure.parse(input)

  res match {
    case Success(procedure) => {
      val finalStacks = runInstructions(reversePulled = true, procedure)

      // println("FINAL STACKS:")
      // prettyPrintStacks(finalStacks)
      // println("=====================")

      getTopCrates(finalStacks).mkString
    }
    case Failure(err) => err.toString
  }
}


def part2(input: String): String = { // => "GGNPJBTTR"
  val res = pProcedure.parse(input)

  res match {
    case Success(procedure) => {
      val finalStacks = runInstructions(reversePulled = false, procedure)

      // println("FINAL STACKS:")
      // prettyPrintStacks(finalStacks)
      // println("=====================")

      getTopCrates(finalStacks).mkString
    }
    case Failure(err) => err.toString
  }
}
