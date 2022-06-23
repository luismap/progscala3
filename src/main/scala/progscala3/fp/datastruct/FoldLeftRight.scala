package progscala3.fp.datastruct

import scala.annotation.tailrec

/**
 * Simplified implementations of foldLeft and foldRight.
 */
object FoldLeftRight:

  def foldLeft[A,B](s: Seq[A])(seed: B)(f: (B,A) => B): B =
    @tailrec
    def fl(accum: B, s2: Seq[A]): B = s2 match
      case head +: tail => fl(f(accum, head), tail)
      case _ => accum
    fl(seed, s)

  def foldRight[A,B](s: Seq[A])(seed: B)(f: (A,B) => B): B =
    s match
      case head +: tail => f(head, foldRight(tail)(seed)(f))
      case _ => seed


@main def entryFoldLeftRight = {
  import FoldLeftRight.*
  val left = (accum: String, element: Int) => s"($accum $element)"
  val right = (element: Int, accum: String) => s"($accum $element)"
  val right2 = (element: Int, accum: String) => s"($element $accum)"

  val seq6 = Seq(1,2,3,4,5,6)

  val strLeft3 = foldLeft(seq6)("(0)")(left)
  val strRight3 = foldRight(seq6)("(0)")(right)
  val strRight4 = foldRight(seq6)("(0)")(right2)

  println(strLeft3)
  println(strRight3)
  println(strRight4)

}