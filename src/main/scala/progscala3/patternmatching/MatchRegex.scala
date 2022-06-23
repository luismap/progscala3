package progscala3.patternmatching

object MatchRegex {

  def main(args: Array[String]): Unit = {

    val BookExtractorRE = """Book: title=([^,]+),\s+author=(.+)""".r
    val MagazineExtractorRE = """Magazine: title=([^,]+),\s+issue=(.+)""".r

    val catalog = Seq(  "Book: title=Programming Scala Third Edition, author=Dean Wampler",
      "Magazine: title=The New Yorker, issue=January 2021",
      "Unknown: text=Who put this here??")

    val results = catalog.map {
      case BookExtractorRE(title, author) =>
        s"""Book "$title", written bwritten by $author"""
      case MagazineExtractorRE(title, issue) =>
        s"""Magazine "$title", issue $issue"""
      case entry => s"Unrecognized entry: $entry"}

    println(results)

    /**
     * matching interpolated strings
     */
    val results2 = catalog.map {
      case s"""Book: title=$t, author=$a""" => ("Book" -> (t -> a))
      case s"""Magazine: title=$t, issue=$d""" => ("Magazine" -> (t -> d))
      case item => ("Unrecognized", item)
    }

    println(results2)

  }

}

