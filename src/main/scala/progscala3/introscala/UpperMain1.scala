package progscala3.introscala

object UpperMain1 {

  /**
   * in scala 3 you key brackets are optional
   * @param params
   */
  def main(params: Array[String]): Unit = 
    print("UpperMain1.main: ")
    params.map(s => s.toUpperCase()).foreach(s => printf("%s ",s))
    println("")
  
}

/**
 * alternate main top level method
 * @param args
 */
def main(params: Array[String]): Unit =
  print("main: ")
  params.map(s => s.toUpperCase()).foreach(s => printf("%s ",s))
  println("")

/**
 * another entry point top-level method
 * where we can change the name
 * @param params
 */
@main def Hello(params: String*): Unit =
  print("Hello: ")
  params.map(s => s.toUpperCase()).foreach(s => printf("%s ",s))
  println("")
