package progscala3.patternmatching

case class Address(street: String, city: String)
case class Person(name: String, age: Int, addrees: Address)




object MatchDeep {
  def main(args: Array[String]): Unit = {

    val alice   = Person("Alice",   25, Address("1 Scala Lane", "Chicago"))
    val bob     = Person("Bob",     29, Address("2 Java Ave.",  "Miami"))
    val charlie = Person("Charlie", 32, Address("3 Python Ct.", "Boston"))

    val results = Seq(alice, bob, charlie).map {
      case p @ Person("Alice", age, a @ Address(_, "Chicago")) =>
        s"Hi Alice! $p"
      case p @ Person("Bob", 29, a @ Address(street, city)) =>
        s"Hi ${p.name}! age ${p.age}, in ${a}"
      case p @ Person(name, age, Address(street, city)) =>
        s"Who are you, $name (age: $age, city = $city)?"
    }

    assert(results == Seq(  "Hi Alice! Person(Alice,25,Address(1 Scala Lane,Chicago))",
          "Hi Bob! age 29, in Address(2 Java Ave.,Miami)",
          "Who are you, Charlie (age: 32, city = Boston)?"))
    }

}
