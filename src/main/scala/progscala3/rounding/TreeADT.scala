package progscala3.rounding

/**
 * doing enumerations using sealed class(valide in scala 2 and 3)
 * and enum (valid in scala 3)
 * ADT(algebraic datatypes)
 */
object SealedADT:
  sealed trait Tree[T]
  final case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]
  final case class Leaf[T](elem: T) extends Tree[T]

  val tree = Branch(    
    Branch(      
      Leaf(1),      
      Leaf(2)),    
    Branch(      
      Leaf(3),      
      Branch(Leaf(4),Leaf(5)))
  )

object EnumADT:

  /**
   * The enum syntax provides the same benefits as sealed type hierarchies, but with less code.
   */
  enum Tree[T]:
    case Branch(left: Tree[T], right: Tree[T])
    case Leaf(elem: T)
  
  import Tree.*
  val tree = Branch(
    Branch(
      Leaf(1),
      Leaf(2)),
    Branch(
      Leaf(3),
      Branch(Leaf(4),Leaf(5)))
  )

@main def callSealedTree(): Unit = {
  println(SealedADT.tree)
}

@main def callEnumTree(): Unit = {
  println(EnumADT.tree)
}


