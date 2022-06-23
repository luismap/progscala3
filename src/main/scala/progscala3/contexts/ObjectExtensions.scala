package progscala3.contexts

object Foo:
  def one: Int = 1

/**
 * use the Object.type
 */

extension (foo: Foo.type)
  def add(i: Int): Int = i + foo.one

@main def objectExtensions(params: String*): Unit = {
  val f = Foo

  println( f add 20)
}