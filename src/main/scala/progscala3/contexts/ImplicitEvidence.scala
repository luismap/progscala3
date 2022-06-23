package progscala3.contexts

@main def entryImplicitEvidence =
  /**
   * The implicit parameter ev is the “evidence” we need to enforce our constraint. It uses
   * a type defined in Predef called <:<, named to resemble the type parameter constraint
   * <:. Effectively, it imposes the requirement that A <: (K,V).
   * In other words, A must be a subtype of (K,V)
   */

  summon[Int <:< Int]
  summon[Int <:< AnyVal]
  summon[Int =:= Int]
  //summon[Int =:= AnyVal] //error!

  summon[(Int, String) <:< (Int, String)]
  summon[(Int, String) <:< (AnyVal, AnyRef)]
  summon[(Int, String) =:= (Int, String)]
  //summon[(Int, String) =:= (AnyVal, AnyRef)] //error!
