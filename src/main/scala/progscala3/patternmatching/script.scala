package progscala3.patternmatching


/**
 * starting with scala 3.0, addint the matchable trait
 * use upper bounds
 *
abstract class Any:
  def isInstanceOf
  def getClass
  def asInstanceOf      // Cast to a new type: myAny.asInstanceOf[String]
  def ==
  def !=
  def ##   // Alias for hashCode
  def equals
  def hashCode
  def toString

trait Matchable extends Any
class AnyVal extends Any, Matchable
class Object extends Any, Matchable
 *
 */

def examine[T <: Matchable ](seq: Seq[T]): Seq[String] = seq map {
  case i: Int => s"Int: $i "
  case other => s"other: $other"
}

def seqToString[T](seq: Seq[T]): String = seq match {
  case head +: tail => s"($head +: ${seqToString(tail)})"
  case Nil => "Nil"
}


@main def entry(args: String*): Unit = {
  val a = examine(Seq(1,2,"here"))
  println(a)

  println(seqToString(Seq(1, 2, 3)))

  /**
   * parameter untuppling
   * (using partial functions
   */

  Seq((1,2,3)) map {
  (x,y,z) => println(s"$x, $y, $z")
  }
}
