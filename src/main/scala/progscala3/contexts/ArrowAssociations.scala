package progscala3.contexts

/**
 * extension is similar to implicits
 */
extension [A] (a: A)
  def ~>[B](b: B): (A,B) = (a,b)

extension [A,B] (a: A)
  def ~~>(b: B): (A,B) = (a,b)









@main def arrowAssociations(params: String*): Unit = {

  println(1 ~> 2)


}
