package progscala3.rounding

import scala.io.Source
import scala.util.control.NonFatal


@main def TryCatch(fileNames: String*): Unit = {
  fileNames.foreach{ filename =>
    var source: Option[Source] = None
    
    try
      source = Some(Source.fromFile(filename))
      val size = source.get.getLines().size
      println(s"file $filename has $size lines")
    catch
      case NonFatal(ex) => println(s"Non fatal exception! $ex")
    finally
      for s <- source do
        println(s"Closing $filename...")
        s.close
  }
}