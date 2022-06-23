package progscala3.contexts

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.*

/**
 * Create a simple ExecutionContext that just runs tasks in the same thread.
 * It is used to demonstrate replacing the use of the global implicit value
 * with one we control in scoped contexts.
 */
val sameThreadExecutionContext = new ExecutionContext:
  def execute(runnable: Runnable): Unit =
    printf("start > ")
    runnable.run()
    printf("finish > ")
  def reportFailure(cause: Throwable): Unit =
    println(s"sameThreadExecutionContext failure: $cause")

/**
 * The Scala 2 way of writing this logic. The user passes a function that
 * takes an ExecutionContext argument and returns a Future. AsyncRunner2.apply()
 * calls the function, passing our custom ExecutionContext, then waits for the
 * results up to two seconds (arbitrary)
 */
object AsyncRunner2:
  def apply[T](body: ExecutionContext => Future[T]): T =
    val future = body(sameThreadExecutionContext)
    Await.result(future, 2.seconds)

/**
 * How it is used. The function takes a “normal” ExecutionContext, but if you add
 * the implicit keyword, it becomes an implicit value that will be passed to all
 * the Future methods called inside the function that take an implicit ExecutionContext
 */
val result2 = AsyncRunner2 {
  implicit executionContext =>
    Future(1).map(_ * 2).filter(_ > 0)
}

object AsyncRunner3:
  type RunnerContext[T] = ExecutionContext ?=> Future[T]

  /**
   * Now a by-name parameter of type RunnerContext[T], aliased to ExecutionContext ?=> Future[T]
   * is passed as the body to execute. A given ExecutionContext is declared in this scope,
   * aliased to sameThreadExecutionContext
   * @param body
   * @tparam T
   * @return
   */
  def apply[T](body: => RunnerContext[T]): T =
    given ExecutionContext = sameThreadExecutionContext
    Await.result(body, 2.seconds)

val result3 = AsyncRunner3 {
  Future(1).map(_ * 2).filter(_ > 0)
}
