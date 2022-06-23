package progscala3.contexts

case class SortableSeq[A](seq: Seq[A]){
  def sortBy1[B](transform: A => B)(implicit o: Ordering[B]): SortableSeq[A] =
    SortableSeq(seq.sortBy(transform)(o))

  def sortBy2[B : Ordering](transform: A=>B): SortableSeq[A] =
    SortableSeq(seq.sortBy(transform)(implicitly[Ordering[B]]))
}


@main def entryImplicitClauses(): Unit =
  val seq = SortableSeq(Seq(1,3,5,2,4))

  def defaultOrdering() = {
    assert(seq.sortBy1(i => -i) == SortableSeq(Seq(5, 4, 3, 2, 1)))
    assert(seq.sortBy2(i => -i) == SortableSeq(Seq(5, 4, 3, 2, 1)))
  }
  defaultOrdering()

  /**
   * Define a custom oddEven ordering, which will be the implicit value that takes precedence
   * in the method’s scope for the following lines.
   */
  def oddEvenOrdering() = {
    implicit val oddEven: Ordering[Int] = new Ordering[Int]:
      def compare(i: Int, j: Int): Int = i%2 compare j%2 match
        case 0 => i compare j
        case c => c

    /**
     * Implicitly use the custom oddEven ordering.
     */
    assert(seq.sortBy1(i => -i) == SortableSeq(Seq(5, 3, 1, 4, 2)))
    assert(seq.sortBy2(i => -i) == SortableSeq(Seq(5, 3, 1, 4, 2)))
  }
  oddEvenOrdering()


@main def entryImplicitClausesScala3():Unit =
  def evenOddGivenOrdering() =
    given evenOdd: Ordering[Int] with
      def compare(i: Int, j: Int): Int = i%2 compare j%2 match
        case 0 => i compare j
        case c => -c

    val seq = SortableSeqScala3(Seq(1,3,5,2,4))
    val expected = SortableSeqScala3(Seq(4, 2, 5, 3, 1))

    assert(seq.sortBy1a(i => -i) == expected)
    assert(seq.sortBy1b(i => -i) == expected)
    assert(seq.sortBy2(i => -i)  == expected)

    assert(seq.sortBy1a(i => -i)(using evenOdd) == expected)
    assert(seq.sortBy1b(i => -i)(using evenOdd) == expected)
    assert(seq.sortBy2(i => -i)(using evenOdd)  == expected)

  evenOddGivenOrdering()


case class SortableSeqScala3[A](seq: Seq[A]):
  /**
   * using instead of implicit
   * @param transform
   * @param o
   * @tparam B
   * @return
   */
  def sortBy1a[B](transform: A => B)(using o: Ordering[B]): SortableSeqScala3[A] =
    SortableSeqScala3(seq.sortBy(transform)(o))

  /**
   * summon instead of implicitly
   * @param transform
   * @param _
   * @tparam B
   * @return
   */
  def sortBy1b[B](transform: A => B)(using Ordering[B]): SortableSeqScala3[A] =
    SortableSeqScala3(seq.sortBy(transform)(summon[Ordering[B]]))

  def sortBy2[B : Ordering](transform: A => B): SortableSeqScala3[A] =
    SortableSeqScala3(seq.sortBy(transform)(summon[Ordering[B]]))