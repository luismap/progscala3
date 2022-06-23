package progscala3.contexts

trait Witness
case object IntWitness extends Witness
case object StringWitness extends Witness

def useWitness(using Witness): String = summon[Witness].toString


@main def entryMatchGivens(): Unit = {
  /**
   * givens are scoped inside the loop
   */
  for given Witness <- Seq(IntWitness, StringWitness)
    do println(useWitness)
}