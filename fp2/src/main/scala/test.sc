object test {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  fpinscala.ch5.Stream.ones.map( _ + 1) .exists( _ %2 ==0)
                                                  //> res0: Boolean = true
  fpinscala.ch5.Stream.ones.takeWhile( _ ==1)     //> res1: fpinscala.ch5.Stream[Int] = Cons(<function0>,<function0>)
  fpinscala.ch5.Stream.ones.forAll( _ != 1)       //> res2: Boolean = false
}