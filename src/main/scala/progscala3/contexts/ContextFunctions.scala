package progscala3.contexts

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*


object FutureCF:
  /**
   * Context functions are functions with context parameters only.
   * Scala 3 introduces a new context function type for them, indicated by ?=> T
   */
  type Executable[T] = ExecutionContext ?=> T


  def apply[T](body: => T): Executable[Future[T]] = Future(body)

def sleepN(dur: Duration): Duration =
  val start = System.currentTimeMillis()
  Thread.sleep(dur.toMillis)
  Duration(System.currentTimeMillis - start, MILLISECONDS)


@main def entryContextFunctions() =
/**
 * 1: Executable(Future(sleepN(1.second))) is supposed to be returned,
 * 2: which is the same as (given ExecutionContext) ?=> Future(sleepN(1.second))
 *    (from the type member alias for Executable),
 * 3: which the compiler converts to Future(sleepN(1.second))(given ExecutionContext)
 */
  val future1 = FutureCF(sleepN(1.second))
  val future2 = FutureCF(sleepN(1.second))(using global)
  val duration1 = Await.result(future1, 2.seconds)
  val duration2 = Await.result(future2, 2.seconds)