package progscala3.fp

object GeneralConcepts {

  def main(args: Array[String]): Unit = {
    /**
     * currying, convert from long
     * Currying is the transformation of a function that takes multiple parameters into a
     * chain of functions, each taking a single parameter
     */

    def mcat(s1: String, s2: String) = s1 + s2
    val mcatCurried = mcat.curried
    println(mcat("hello", "world"))
    println(mcatCurried("hello")("world"))


    /**
     * tupling, untupling
     */

    def mult(d1: Double, d2: Double) = d1 * d2
    val d23 = (2.2, 3.3)
    val multTup1 = Function.tupled(mult)
    val multTup2 = mult.tupled

    println(multTup1(d23))
    println(multTup2(d23))

    val mult2 = Function.untupled(multTup2)
    println( mult2(d23._1, d23._2))


    /**
     * about partial functions
     */

    val finicky: PartialFunction[String,String] =
      case "finicky" => "FINICKY"

    println(finicky("finicky"))
    println(finicky.isDefinedAt("other"))

    //lift if to return Option
    val finickyOption = finicky.lift

    println(finickyOption("other"))

    //we can unlift also
    val finicky2 = Function.unlift(finickyOption)





  }

}
