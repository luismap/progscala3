package progscala3.contexts

type Status = String

case class Transaction(database: String):
  def begin(query: String): Status = s"$database: Starting transaction: $query"
  def rollback(): Status = s"$database: Rolling back transaction"
  def commit(): Status = s"$database: Committing transaction"


case class ConnectionManager(database: String):
  println(s"... expensive initialization for database $database")
  def createTransaction: Transaction = Transaction(database)

@main def entryByNameContextParameters() =
  def doTransaction(query: => String)(
  using cm: => ConnectionManager): Seq[Status] =
    val trans = cm.createTransaction
    Seq(trans.begin(query), trans.commit())


  def doPostgreSQL =
    println("Start of doPostgreSQL.")
    given ConnectionManager = ConnectionManager("PostgreSQL")
    println("Start of doTransaction.")
    doTransaction("SELECT * FROM table")

  doPostgreSQL.foreach(println)