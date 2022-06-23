package progscala3.contexts

/**
 * example, Scala 3 introduces scala.CanEqual, which restricts use of the comparison
 * operators == and != for instances of arbitrary types. Normally, it is permitted to
 * do these comparisons, but when the compiler flag -language:strictEquality or the import
 * statement import scala.language.strictEquality is used, then the comparison operators are
 * only allowed in certain specific contexts
 */
import scala.language.strictEquality

/**
 * Type class derivation is the idea that we should be able to automatically generate
 * type class given instances as long as they obey a minimum set of requirements, further
 * reducing “boilerplate”. A type uses the new keyword derives, which works like extends
 * or with, to trigger derivation
 */
enum Tree[T] derives CanEqual:
  case Branch(left: Tree[T], right: Tree[T])
  case Leaf(elem: T)

@main def TryDerived() =
  import Tree.*
  val l1 = Leaf("l1")
  val l2 = Leaf(2)
  val b = Branch(l1,Branch(Leaf("b1"),Leaf("b2")))
  assert(l1 == l1)
  // assert(l1 != l2)   // Compilation error!
  assert(l1 != b)
  assert(b  == b)
  println(s"For String, String: ${summon[CanEqual[Tree[String],Tree[String]]]}")
  println(s"For Int, Int: ${summon[CanEqual[Tree[Int],Tree[Int]]]}")
// Compilation error:
// println(s"For String, Int: ${summon[CanEqual[Tree[String],Tree[Int]]]}")
