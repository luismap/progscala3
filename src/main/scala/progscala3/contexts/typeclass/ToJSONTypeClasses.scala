package progscala3.contexts.typeclass

import progscala3.introscala.shapes.{Point, Shape, Circle, Rectangle, Triangle}
import progscala3.contexts.json.ToJSON

/**
 * declaring and instance of typeclass
 *
 *will generate a synthesize name
 */
given ToJSON[Point] with
  extension (point: Point)
    def toJSON(name: String = "", level: Int = 0): String =
      val (outdent, indent) = indentation(level)
      s"""${handleName(name)}{
         |${indent}"x": "${point.x}",
         |${indent}"y": "${point.y}"
         |$outdent}""".stripMargin

/**
 * adding a name
 */
given circleToJSON: ToJSON[Circle] with
  def toJSON2(
               circle: Circle,
               name: String = "",
               level: Int = 0): String =
    val (outdent, indent) = indentation(level)
    s"""${handleName(name)}{
       |${indent}${circle.center.toJSON("center", level + 1)},
       |${indent}"radius": ${circle.radius}
       |$outdent}""".stripMargin

  extension (circle: Circle)
    def toJSON(name: String = "", level: Int = 0): String =
      toJSON2(circle, name, level)


given rectangleToJSON: ToJSON[Rectangle] with
  def toJSON2(
               rectangle: Rectangle,
               name: String = "",
               level: Int = 0): String =
    val (outdent, indent) = indentation(level)
    s"""${handleName(name)}{
       |${indent}${rectangle.lowerleft.toJSON("lowerleft", level + 1)},
       |${indent}"height": ${rectangle.height}
       |${indent}"width": ${rectangle.width}
       |$outdent}""".stripMargin

  extension (rectangle: Rectangle)
    def toJSON(name: String = "", level: Int = 0): String =
      toJSON2(rectangle, name, level)


/**
 *
 */
given triangleToJSON: ToJSON[Triangle] with
  def toJSON2(
               tri: Triangle,
               name: String = "",
               level: Int = 0): String =
    val (outdent, indent) = indentation(level)
    s"""${handleName(name)}{
       |${indent}${tri.point1.toJSON("point1", level + 1)},
       |${indent}${tri.point2.toJSON("point2", level + 1)},
       |${indent}${tri.point3.toJSON("point3", level + 1)},
       |$outdent}""".stripMargin

  extension (tri: Triangle)
    def toJSON(name: String = "", level: Int = 0): String =
      toJSON2(tri, name, level)

given ToJSON[Shape] with
  extension (shape: Shape)
    def toJSON(name: String = "", level: Int = 0): String =
      shape match
        case c: Circle    => circleToJSON.toJSON2(c, name, level)
        case r: Rectangle => rectangleToJSON.toJSON2(r, name, level)
        case t: Triangle  => triangleToJSON.toJSON2(t, name, level)

@main def TryJSONTypeClasses() =
  /**
   * use summon to bring to scope
   */
  println(s"summon[ToJSON[Point]] = ${summon[ToJSON[Point]]}")
  println(Circle(Point(1.0,2.0), 1.0).toJSON("circle", 0))
  println(Rectangle(Point(2.0,3.0), 2, 5).toJSON("rectangle", 0))
  println(Triangle(Point(0.0,0.0), Point(2.0,0.0), Point(1.0,2.0)).toJSON("triangle", 0))

  val c = Circle(Point(1.0,2.0), 1.0)
  val r = Rectangle(Point(2.0,3.0), 2, 5)
  val t = Triangle(Point(0.0,0.0), Point(2.0,0.0), Point(1.0,2.0))
  println("==== Use shape.toJSON:")
  Seq(c, r, t).foreach(s => println(s.toJSON("shape", 0)))
  println("==== call toJSON on each shape explicitly:")
  println(c.toJSON("circle", 0))
  println(r.toJSON("rectangle", 0))
  println(t.toJSON("triangle", 0))