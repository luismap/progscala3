package progscala3.rounding

import scala.language.reflectiveCalls
import reflect.Selectable.reflectiveSelectable
import scala.util.control.NonFatal
import scala.io.Source

/**
 * implementing ARM(autamatic resource management) using
 * powerfull controls statement like call by name.( => T)
 * 
 * {def close():Unit} is a structural type, means that is expected anything that implements
 * that control.
 * 
 * We declare this using a structural type defined with the braces. 
 * What would be more intuitive, especially if you are new to structural types, would be 
 * for all resources to implement a Closable interface that defines a close():Unit method
 *
 * we use uncapitalize object declaration to look like a builtin control structure
 * 
 * 
 */

object manage:
  def apply[R <: {def close(): Unit}, T](resource: => R)(f: R => T): T =
    var res: Option[R] = None
    try
      res = Some(resource)
      f(res.get)
    catch
      case NonFatal(ex) => 
        println(s"manage.apply: Non fatal exception $ex")
        throw ex
    finally
      res match {
        case Some(resource) => 
          println("Closing Resource")
          res.get.close()
        case None => //do nothing  
      }



@main def TryCatchARM(fileNames: String*) =
  val sizes = fileNames.map { fileName =>
    try
      val size = manage(Source.fromFile(fileName)) { source =>
        source.getLines.size
      }
      println(s"file $fileName has $size lines")
      size
    catch
      case NonFatal(ex) =>
        println(s"caught $ex")
  }
  
  println("Returned sizes: " + (sizes.mkString(", ")))