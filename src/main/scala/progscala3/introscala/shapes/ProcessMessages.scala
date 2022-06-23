package progscala3.introscala.shapes

object ProcessMessages {

  def apply(message: Message): Message =
    message match {
      case Exit =>
        println("Process message: exiting ...")
        Exit
      case Draw(shape) =>
        shape.draw(str => println(s"Process message: $str"))
        Response(s"Processed message $shape  drawn")
      case Response(unexpected) =>
        val response = Response(s"ERROR: Unexpected Response: $unexpected")
        println(s"ProcessMessage: $response")
        response


    }
  
}
