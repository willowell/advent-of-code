package harness

def time[A](block: => A): A = time("TIME")(block)

def time[A](prefix: String)(block: => A): A = {
  val start = System.nanoTime()

  val result = block

  val end = System.nanoTime()

  val (took, unit, color) = (end - start).toDouble / 1000000 match {
    case ms if ms > 1000 => ((ms / 1000).toString(), "s", Console.RED)
    case ms if ms > 100  => (ms.toString(), "ms", Console.YELLOW)
    case ms              => (ms.toString(), "ms", Console.GREEN)
  }

  val timed = s"$prefix: " + color + s"${"%-3.4s".format(took)}$unit" + Console.RESET

  println(s"$timed")

  result
}
