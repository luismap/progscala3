package progscala3.contexts.typeclass
import scala.annotation.targetName

/**
 * Semigroup generalizes the notion of addition or composition
 * @tparam T
 */
trait Semigroup[T]:
  /**
   * Extension methods allow one to add methods to a type after the type is defined. Example:
   */
  extension (t: T) {
    infix def combine(other: T): T

    @targetName("plus") def <+>(other: T): T = t.combine(other)
  }

/**
 * Monoid adds the idea of a “unit” value. If you add zero to a number,
 * you get the number back. If you prepend or append an empty string to
 * another string, you get the second string back
 * @tparam T
 */
trait Monoid[T] extends Semigroup[T]:
  def unit: T


given StringMonoid: Monoid[String] with
  def unit = ""
  extension (s: String)
    infix def combine(other: String): String = s + other


given IntMonoid: Monoid[Int] with
  def unit = 0
  extension (i: Int)
    infix def combine(other: Int): Int = i + other

given NumericMonoid[T : Numeric]: Monoid[T] with
  def unit: T = summon[Numeric[T]].zero
  extension (t: T)
    infix def combine(other: T): T = summon[Numeric[T]].plus(t, other)


  /**
   * The type [T : Numeric] is a Context Bound, a shorthand way of
   * writing the definition this way.
   *
   * Note the using clause. If a given Numeric is in scope for a particular type T,
   * then this type class instance can be used. The bodies are slightly different,
   * too. In the previous version, we used summon to get the anonymous using parameter,
   * so we can reference zero and plus. In this version, we have a name for the using parameter,
   * num
   */

  given NumericMonoidC[T] (using num: Numeric[T]): Monoid[T] with
    def unit: T = num.zero

    extension (t: T)
      infix def combine(other: T): T = num.plus(t, other)


@main def tryMonoidTypeClass(param: String*): Unit ={
  println("2" <+> ("3" <+> "4"))             // "234"
  println(("2" <+> "3") <+> "4")            // "234"
  println(("2" combine "3") combine "4")     // "234"
  println(StringMonoid.unit <+> "2" )        // "2"
  println("2" <+> StringMonoid.unit   )      // "2"
  println(2 <+> (3 <+> 4) )        // 9
  println( (2 <+> 3) <+> 4 )                  // 9
  println(IntMonoid.unit <+> 2  )            // 2
  println( 2 <+> IntMonoid.unit )             // 2

  println( BigDecimal(3.14) <+> NumericMonoid.unit)
  println(NumericMonoid[BigDecimal].unit <+> BigDecimal(3.14))
  println(NumericMonoid[BigDecimal].unit combine BigDecimal(3.14))
}
