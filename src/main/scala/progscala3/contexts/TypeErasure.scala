package progscala3.contexts

/**
object O:
  def m(is: Seq[Int]): Int = is.sum
  def m(ss: Seq[String]): Int = ss.length //type erasure problem

**/

object Two:
  /**
   * didn’t just use given Int and String values, rather than invent the Marker type.
   * Using given values for very common types is not recommended. It would be too easy f
   * or one or more given String values, for example, to show up in a particular scope. If
   * you don’t expect a given String instance to be in scope, you will be surprised when it
   * gets used. If you do expect one to be in scope, but there are several of them, you’ll
   * get a compiler error because of the ambiguous choices.
   * @tparam T
   */
  trait Marker[T]
  given IntMarker: Marker[Int] with {}
  given StringMarker: Marker[String] with {}

  /**
   * we can add an implicit parameter to disambiguate the methods:
   * @param is
   * @param _
   * @return
   */
  def m(is: Seq[Int])(using IntMarker.type): Int = is.sum
  def m(ss: Seq[String])(using StringMarker.type): Int = ss.length


@main def entryTypeErasure =
  import Two.{given, *}

  println(m(Seq(1,2,3)))
  println(m(Seq("one", "two", "three")))
