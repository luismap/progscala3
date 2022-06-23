package progscala3.introscala.shapes

sealed trait Message

case class Draw(shape: Shape) extends Message
case class Response(message: String) extends Message
case object Exit extends Message
