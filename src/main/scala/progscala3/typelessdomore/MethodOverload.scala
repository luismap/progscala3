package progscala3.typelessdomore

case class Money(value: BigDecimal)
case object Money:
  def apply(s: String): Money = apply(BigDecimal(s.toDouble))
  def apply(d: Double): Money = apply(BigDecimal(d))

object MethodOverload {

  def main(args: Array[String]): Unit = {
    println(Money("3.034"))
    println(Money(30d))
  }
}
