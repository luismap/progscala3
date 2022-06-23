package progscala3.typelessdomore.api


/**
 * before scala 3.0 it supported the same using the package syntax
 * 
 */
  
val DEFAULT_COUNT = 5
def countTo(limit: Int = DEFAULT_COUNT) = (0 to limit).foreach(println)

class Class1:
  def m = "cm1"

object Object1:
  def m = "om1"

