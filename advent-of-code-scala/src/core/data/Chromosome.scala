package core.data.chromosome

trait Chromosome[A] extends scala.math.Ordered[Chromosome[A]] {
  var fitness: Double

  def crossover(other: A): List[A]

  def mutate(): Unit

  def copy(): this.type

  def compare(that: Chromosome[A]): Int = {
    val mine = fitness
    val theirs = that.fitness

    mine compare theirs
  }
}
