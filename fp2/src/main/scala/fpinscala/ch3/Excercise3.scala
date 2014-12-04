package fpinscala.ch3

object Excercise3 {
  //see https://github.com/fpinscala/fpinscala/blob/master/answers/src/main/scala/fpinscala/datastructures/List.scala
  def main(args: Array[String]) = {

    println("----excersise 3.1:")
    println("Result = " + List.x)

    println("----excersise 3.2:")
    println("Result List.tail(List(1,2,3,4))= " + List.tail(List(1, 2, 3, 4)))
    //println("Result List.tail(Nil)= " + List.tail(Nil))

    println("----excersise 3.3:")
    println("Result  List.setHead(List(1,2,3,4),0)= " + List.setHead(List(1, 2, 3, 4), 0))
    //println("Result  List.setHead(Nil,0)= " + List.setHead(Nil,0))

    println("----excersise 3.4:")
    println("Result List.drop(List(1,2,3,4),2)= " + List.drop(List(1, 2, 3, 4), 2))
    println("Result List.drop(List(1,2,3,4),4)= " + List.drop(List(1, 2, 3, 4), 4))
    println("Result List.drop(List(1,2,3,4),5)= " + List.drop(List(1, 2, 3, 4), 5))

    println("----excersise 3.5:")
    println("Result List.dropWhile(List(1,2,3,4),(x:Int) => (x<3))= " + List.dropWhile(List(1, 2, 3, 4), (x: Int) => (x < 3)))
    println("Result List.dropWhile(List(1,2,3,4),(x:Int) => (x<=4))= " + List.dropWhile(List(1, 2, 3, 4), (x: Int) => (x <= 4)))
    println("Result List.dropWhile(List(1,2,3,4),(x:Int) => (x>10))= " + List.dropWhile(List(1, 2, 3, 4), (x: Int) => (x > 10)))

    println("Result List.dropWhileTypeInterf(List(1, 2, 3, 4))(x => x < 3)= " + List.dropWhileTypeInterf(List(1, 2, 3, 4))(x => x < 3))

    println("----excersise 3.5:")
    println("Result List.init(List(1,2,3,4))= " + List.init(List(1, 2, 3, 4)))
    //    println("Result List.init(Nil)= " + List.init(Nil))
    
    println("----excersise 3.8:")
    println("Result List.foldRight(List(1, 2, 3, 4, 9), Nil:List[Int]) (Cons(_,_))= " + List.foldRight(List(1, 2, 3, 4, 9), Nil:List[Int]) (Cons(_,_)))
    
    println("----excersise 3.9:")
    println("Result List.length(List(1, 2, 3, 4, 9))= " + List.length(List(1, 2, 3, 4, 9)))
    
    println("----excersise 3.10:")
    println("Result List.foldLeft(List(1, 2, 3, 4, 9), 0)(_+_))= " + List.foldLeft(List(1, 2, 3, 4, 9), 0)(_+_))
   
    println("----excersise 3.11:")
    println("Result List.sumLeft(List(1, 2, 3, 4, 9))= " + List.sumLeft(List(1, 2, 3, 4, 9)))
    println("Result List.productLeft(List(1, 2, 3, 4, 9))= " + List.productLeft(List(1, 2, 3, 4, 9)))
    println("Result List.lengthLeft(List(1, 2, 3, 4, 9))= " + List.lengthLeft(List(1, 2, 3, 4, 9)))
  
    println("----excersise 3.12:")
    println("Result List.reverse(List(1, 2, 3, 4, 9))= " + List.reverse(List(1, 2, 3, 4, 9)))
 
     
    println("----excersise 3.13:")
//    println("Result List.foldLeftUsingRight(List(1, 2, 3, 4, 9), 0)(_+_))= " + List.foldLeftUsingRight(List(1, 2, 3, 4, 9), 0)(_+_))
//    println("Result List.foldLeftUsingRight(List(1, 2, 3, 4, 9), 0)(_*_))= " + List.foldLeftUsingRight(List(1, 2, 3, 4, 9), 1)(_*_))
    println("Result List.foldRightUsingRight(List(1, 2, 3, 4, 9), 0)(_+_))= " + List.foldRightUsingLeft(List(1, 2, 3, 4, 9), 0)(_+_))
    println("Result List.foldRightUsingRight(List(1, 2, 3, 4, 9), 0)(_*_))= " + List.foldRightUsingLeft(List(1, 2, 3, 4, 9), 1)(_*_))
    
    //substituting:
    //  foldLeft(List(1,2,3), 0)(_ + _)
    //
   
  }
}