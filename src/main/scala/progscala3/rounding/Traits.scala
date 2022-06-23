package progscala3.rounding

import util.Random

/**
 * The open keyword is optional in Scala 3.0. It indicates that
 * subtypes can be derived from this concrete class
 * @param name
 */
open class Service(name: String):
  def work(i: Int): (Int, Int) = (i, Random.between(0,1000))

/**
 * using buil-in ordinal value of case classes
 */
enum Level:
  case INFO, WARNING, ERROR
  def ==(other: Level): Boolean = this.ordinal == other.ordinal
  def >=(other: Level): Boolean = this.ordinal >= other.ordinal

trait Logging:
  import Level.*
  
  def level: Level
  def log(level: Level, message: String): Unit

  final def info(message: String): Unit =
    if level >= INFO then log(INFO, message)
  final def warning(message: String): Unit =
    if level >= WARNING then log(WARNING, message)
  final def error(message: String): Unit =
    if level >= ERROR then log(ERROR, message)

trait StdoutLogging extends Logging:
  def log(level: Level, message: String) =
    println(s"$level: $message")


/**
 *implementing a logged service now 
 */
case class LoggedService(name: String, level: Level)
  extends Service(name) with StdoutLogging:
  override def work(i: Int): (Int, Int) =
    info(s"Starting work: i = $i")
    val result = super.work(i)
    info(s"Ending work: result = $result")
    result  
  
@main def ServiceMain =
  val service1 = new Service("one")
  (1 to 3) foreach (i => println(s"Result: ${service1.work(i)}"))

  val service2 = LoggedService("two", Level.INFO)
  (1 to 3) foreach (i => println(s"Result:  ${service2.work(i)}"))



