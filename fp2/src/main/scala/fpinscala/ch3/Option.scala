package fpinscala.ch4

import scala.{ Option => _, Some => _, Either => _, _ } // hide std library `Option`, `Some` and `Either`, since we are writing our own in this chapter
sealed trait Option[+A] {
  def map[B](f: A => B): Option[B] = this match {
    case None => None
    case Some(a) => Some(f(a))
  }

  def getOrElse[B >: A](default: => B): B = this match {
    case None => default
    case Some(a) => a
  }

  def flatMap[B](f: A => Option[B]): Option[B] = map(f) getOrElse None
  //  this match{
  //    case None => None
  //    case Some(a) => f(a)
  //  }

  def orElse[B >: A](ob: => Option[B]): Option[B] = (this.map(Some(_))).getOrElse(ob)
  //    this match {
  //    case None => ob
  //    case _ => this
  //  }

  def filter(f: A => Boolean): Option[A] =
    this match {
      case Some(a) if f(a) => this
      case _ => None
    }
  //or: 
  //flatMap( a => if(f(a)) this else None )


}

case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

object Option {
  def failingFn(i: Int): Int = {
    val y: Int = throw new Exception("fail!") // `val y: Int = ...` declares `y` as having type `Int`, and sets it equal to the right hand side of the `=`.
    try {
      val x = 42 + 5
      x + y
    } catch { case e: Exception => 43 } // A `catch` block is just a pattern matching block like the ones we've seen. `case e: Exception` is a pattern that matches any `Exception`, and it binds this value to the identifier `e`. The match returns the value 43.
  }
  def failingFn2(i: Int): Int = {
    try {
      val x = 42 + 5
      x + ((throw new Exception("fail!")): Int) // A thrown Exception can be given any type; here we're annotating it with the type `Int`
    } catch { case e: Exception => 43 }
  }
  
  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)
    
  def variance(xs: Seq[Double]): Option[Double] = 
    mean(xs).flatMap( m => mean( xs.map( x => math.pow(x - m,2) ) )  )

  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = 
    a flatMap (aa => (b map (bb => (f(aa, bb)))))

    
  def sequence[A](a: List[Option[A]]): Option[List[A]] = {
      //a.foldRight(List())( (a:A,b:List[A]) => b::a )
      
      //(aa:Option[A], b:Option[List[A]]) => aa map ( aaa:A => List(aaa) ) 
//      def mapA(aa:A, bb:Option[List[A]]):List[A] = ??? ///bb.flatMap( l => l ++ aa  )
//      def procA(aa:Option[A],b:Option[List[A]] ) : Option[List[A]] = aa map ( mapA(_,b) ) 
//      val z:Option[List[A]] = Some(List())
//      a.foldRight(z)(procA)
      
      a match {
         case Nil => Some(Nil)
         case head :: tail =>  map2 (head , sequence(tail) ) ( (h,l)=> h :: l )
         //which is the same as:
         //case h :: t => h flatMap (hh => sequence(t) map (hh :: _))
      }    
      
    }

  //another solution in answers:
  /*
	It can also be implemented using `foldRight` and `map2`. 
	The type annotation on `foldRight` is needed here; otherwise Scala wrongly infers the 
	result type of the fold as `Some[Nil.type]` 
	and reports a type error (try it!). This is an unfortunate consequence of Scala using subtyping to encode algebraic data types.
  */
  def sequence_1[A](a: List[Option[A]]): Option[List[A]] = {
    a.foldRight[Option[List[A]]](Some(Nil))((opA, opListA) => map2(opA, opListA)(_ :: _))
  }
    
  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = sys.error("todo")
}