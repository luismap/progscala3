package progscala3.contexts

import scala.language.implicitConversions
import scala.util.Try

case class InvalidFieldName(name: String)
  extends RuntimeException(s"Invalid Field Name $name")

/**
 * Define Record with a single field Map[String,Any] to hold the user-defined fields
 * and values. Use of private after the type name declares the constructor private,
 * forcing users to create records using Record.make followed by add calls. This prevents
 * users from using an unconstrained Map to construct a Record!
 * @param contents
 */
case class Record private (contents: Map[String, Any]):
  import Record.Conv

  private def col(colName: String): Any =
    contents.getOrElse(colName, throw InvalidFieldName(colName))

  /**
   * A method to add a field with a particular type and value. The context bound, Conv[T],
   * is used only used as a witness to constrain the allowed values for T. Its apply method
   * won’t be used. Since Records are immutable, a new instance is returned
   * @param nameValue
   * @tparam T
   * @return
   */
  def add[T: Conv](nameValue: (String, T)): Record =
    Record(contents + nameValue)

  /**
   * A method to retrieve a field value with the desired type T. Here the context bound both
   * constrains the allowed T types and it handles conversion from Any to T. On failure, an exception
   * is returned in the Try. Hence, this example can’t catch all type errors at compile time, as shown below.
   * @param colName
   * @tparam T
   * @return
   */
  def get[T : Conv](colName: String): Try[T] =
    Try {
      val conv = summon[Conv[T]]
      conv(col(colName))
    }

/**
 *
The companion object defines make to start “safe” construction of a Record.
It also defines a type member alias for Conversion, where we always use Any
as the first type parameter. This alias is necessary when we define the given ab below
 */
object Record:
  def make: Record = new Record(Map.empty)
  type Conv[T] = Conversion[Any, T]


@main def entryNoSqlRecords(): Unit =
  import Record.Conv
  /**
   * Only Int, Double, String, and pairs of them are supported. These definitions work as
   * witnesses for the allowed types in both the add and get methods, as well as function
   * as implicit conversions from Any to specific types when used in get. Note that the given
   * ab is for pairs, but the A and B types are themselves constrained by Conv, which could also
   * be other pairs. Hence, nested pairs are allowed
   * @return
   */
  given Conv[Int] = _.asInstanceOf[Int]
  given Conv[Double] = _.asInstanceOf[Double]
  given Conv[String] = _.asInstanceOf[String]
  given ab[A : Conv, B : Conv]: Conv[(A, B)] = _.asInstanceOf[(A,B)]

  val rec = Record.make.add("one" -> 1).add("two" -> 2.2)
    .add("three" -> "THREE!").add("four" -> (4.4, "four"))
    .add("five" -> (5, ("five", 5.5)))

  val one   = rec.get[Int]("one")
  val two   = rec.get[Double]("two")
  val three = rec.get[String]("three")
  val four  = rec.get[(Double, String)]("four")
  val five  = rec.get[(Int, (String, Double))]("five")
  val bad1  = rec.get[String]("two")
  val bad2  = rec.get[String]("five")
  val bad3  = rec.get[Double]("five")
  // val error  = rec.get[Byte]("byte")

  println(
    s"one, two, three, four, five ->\n  $one, $two, $three, $four,\n  $five")
  println(
    s"bad1, bad2, bad3 ->\n  $bad1\n  $bad2\n  $bad3")
