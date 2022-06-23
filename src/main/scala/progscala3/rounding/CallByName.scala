package progscala3.rounding

import scala.annotation.tailrec
import scala.annotation.targetName

@tailrec def continue(conditional: => Boolean)(body: => Unit): Unit =
  if conditional then
    body
    continue(conditional)(body)

/**
 * we can use targetName to disambiguate the overloading
 * (by name functions get same name
 * @param conditional
 * @param body
 */
@targetName("nonNegativeContinue")
@tailrec def continue(conditional: => Int)(body: => Unit): Unit =
  if conditional > 0 then
    body
    continue(conditional)(body)    

@main def CallByName =
  var count = 0
  
  continue (count < 5) {
    println(s"at $count")
    count += 1
  }
  
  println(assert(count == 5))

