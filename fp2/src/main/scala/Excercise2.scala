object Excercise2 {
  //see: https://github.com/fpinscala/fpinscala/blob/master/exercises/src/main/scala/fpinscala/gettingstarted/GettingStarted.scala

  def main(args: Array[String]) = {

    def fx(a1: String, a2: String): String = a1 + " " + a2
    println("fx (\"ala\", \"ma\") = " + fx("ala", "ma"))
    
    println("----excersise 2.3:")
    println("curry(fx) = " + (curry(fx))("ala")("ma"))
    
    println("----excersise 2.4:")
    println("uncurry(curry(fx)) = " + uncurry( (curry(fx)) )  ("ala", "ma") )
    
    
    println("----excersise 2.5:")
    

  }

  def curry[A, B, C](f: (A, B) => C): A => (B => C) = 
    // my solution: 
    (a) => f(a, _)
    // idea based on scala.Function2.curried:
    //(a: A) => ((b: B) => f(a, b))
    
   def uncurry[A, B, C](f: A => B => C): (A, B) => C = 
    // my solution: 
    (a,b) => f(a) (b)
    // is based on fact: 
     // f: A => B => C   ===   f: A => (B=>C)
    
    //original in scala.Function :
    //  def uncurried[a1, a2, b](f: a1 => a2 => b): (a1, a2) => b = {
    //   (x1, x2) => f(x1)(x2)
    
}