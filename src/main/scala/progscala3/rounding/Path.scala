package progscala3.rounding

import  scala.annotation.targetName
import java.io.File

case class Path(
               value: String,
               separator: String = Path.defaultSeparator
               ):
  val file = File(value)
  override def toString: String = file.getPath

  /**
   * @targetName annotation is optional, but suggested for “operator” methods that might be called from other
   * language(Java).
   *  concat is the name the compiler will use internally when it generates byte code
   * @param node
   * @return
   */
  @targetName("concat") def / (node: String): Path =
    copy(value + separator + node)

  /**
   * in scala 2 you could call method with one parameter with infix notation, from scala 3 this
   * will be deprecated, so use infix if you want the behaviour
   */
  infix def append(node: String) = /(node)

object Path:
  val defaultSeparator = sys.props("file.separator")

