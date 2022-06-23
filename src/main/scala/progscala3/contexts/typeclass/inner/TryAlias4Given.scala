package progscala3.contexts.typeclass.inner

import progscala3.contexts.typeclass.Monoid


/**
 * The method NumericMonoid2 is called every single time the <+> extension method is used
 * for a Numeric[T] value. The println output occurs twice for each example, because we
 * construct two instances, one for each <+> invocation. So, for given “instances” with
 * type parameters, be careful about using an alias given
 * @tparam T
 * @return
 */
  given NumericMonoid2[T: Numeric]: Monoid[T] = new Monoid[T] {
    println("Initializing NumericMonoid2")

    def unit: T = summon[Numeric[T]].zero

    extension (t: T)
      infix def combine(other: T): T = summon[Numeric[T]].plus(t, other)
  }

/**
 * StringMonoid2 is a lazy val, it will be initialized once and only once, and initialization
 * will be delayed until the first time we use it. Hence, this is a good option when you need
 * delayed initialization
 * @return
 */
  given StringMonoid2: Monoid[String] = new Monoid[String] {
    println("Initializing StringMonoid2")

    def unit: String = ""

    extension (s: String)
      infix def combine(other: String): String = s + other
  }



object TryAlias4Given {
  def main(args: Array[String]): Unit = {

      println("hello")
      println(2.2 <+> (3.3 <+> 4.4))
      println((2.2 <+> 3.3) <+> 4.4)

      println("2" <+> ("3" <+> "4"))
      println(("2" <+> "3") <+> "4")
  }
}
