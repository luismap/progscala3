package progscala3.fp.datastruct

import scala.math.BigInt

object Fibonacci:
  val fibs: LazyList[BigInt] =
    BigInt(0) #:: BigInt(1) #:: fibs.zip(fibs.tail).map(n12 => n12._1 + n12._2)


@main def entryLazyListFib =
  println(Fibonacci.fibs.take(10).toList)