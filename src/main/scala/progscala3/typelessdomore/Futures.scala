package progscala3.typelessdomore
import scala.annotation.tailrec
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Futures {
  
  def factorial(i: Int): BigInt = {
    @tailrec
    def fact(i: Int, acc: BigInt): BigInt = {
      if (i <= 1) acc
      else fact(i-1, acc * i)
    }
    
    fact(i, BigInt(1))
  }

  def doSomeWork() = {
    def sleep(millis: Long) = Thread.sleep(millis)

    (1 to 5).foreach {
      i =>
        val future = Future {
          val duration = (math.random() * 1000).toLong
          sleep(duration)
          if i == 3 then throw RuntimeException(s"$i -> $duration")
          duration
        }

        future.onComplete {
          case Failure(throwable) => println(s"Failure #$i -> $throwable")
          case Success(result) => println(s"Succes #$i -> $result")
        }

    }

    sleep(1000)

    println("FINISHED")
    
    (1 to 10).map(factorial).foreach(println)
    
  }

  def main(args: Array[String]): Unit = {

    doSomeWork()
    
  }

}
