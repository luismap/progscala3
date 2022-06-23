package progscala3.contexts



import scala.annotation.implicitNotFound

/**
 * you can improve the errors reported by the compiler when a context parameter isn’t
 * found in scope. The compiler’s default messages are usually sufficiently descriptive,
 * but you can customize them with the implicitNotFound annotation
 * @tparam T
 */
@implicitNotFound("No implicit found: Tagify[${T}]")
trait Tagify[T]:
  def toTag(t: T): String

case class Stringer[T : Tagify](t: T):
  override def toString: String =
    s"Stringer: ${summon[Tagify[T]].toTag(t)}"

object Zero:
  def makeXML[T](t: T)(
    using @implicitNotFound("makeXML: No Tagify[${T}] implicit found") tagger: Tagify[T]
  ): String =
    s"<xml>${tagger.toTag(t)}</xml>"



@main def entryImplicitNotFound =
  given Tagify[Int] with
    def toTag(i: Int): String = s"<int>$i</int>"

  given Tagify[String] with
    def toTag(s: String): String = s"<string>$s</string>"

  println(Stringer("Hello World!"))
  println(Stringer(1020))
  println(Zero.makeXML("Hellow World"))
  //println(Stringer(3.45)) //Error!! customized by annotation