package progscala3.typelessdomore

object PartialFunctions {

  def tryPF(
           x: Matchable,
           f: PartialFunction[Matchable, String]
           ): String = {
    try f(x)
    catch case _: MatchError => "ERROR!"
  }
  
  def main(args: Array[String]): Unit = {
    val pfs: PartialFunction[Matchable, String] =
      case s: String => "YES"
      
    val pfd: PartialFunction[Matchable, String] =
      case d: Double => "YES"
    
    val pfsd = pfs.orElse(pfd)
    
    assert(tryPF("str", pfs)  == "YES")
    assert(tryPF("str", pfd)  == "ERROR!")
        assert(tryPF("str", pfsd) == "YES")
        assert(tryPF(3.142, pfs)  == "ERROR!")
        assert(tryPF(3.142, pfd)  == "YES")
        assert(tryPF(3.142, pfsd) == "YES")
        assert(tryPF(2, pfs)      == "ERROR!")
        assert(tryPF(2, pfd)      == "ERROR!")
        assert(tryPF(2, pfsd)     == "ERROR!")

        assert(pfs.isDefinedAt("str")  == true)
        assert(pfd.isDefinedAt("str")  == false)
        assert(pfsd.isDefinedAt("str") == true)
        assert(pfs.isDefinedAt(3.142)  == false)
        assert(pfd.isDefinedAt(3.142)  == true)
        assert(pfsd.isDefinedAt(3.142) == true)
        assert(pfs.isDefinedAt(2)      == false)
        assert(pfd.isDefinedAt(2)      == false)
        assert(pfsd.isDefinedAt(2)     == false)
    
    
    
    val fs = pfs.lift
    println(fs("str"))
    
    val fs2 = fs.unlift
    println(fs(2.3))
    
  }
}
