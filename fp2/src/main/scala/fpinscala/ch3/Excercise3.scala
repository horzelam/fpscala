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
    println("Result List.foldRight(List(1, 2, 3, 4, 9), Nil:List[Int]) (Cons(_,_))= " + List.foldRight(List(1, 2, 3, 4, 9), Nil: List[Int])(Cons(_, _)))

    println("----excersise 3.9:")
    println("Result List.length(List(1, 2, 3, 4, 9))= " + List.length(List(1, 2, 3, 4, 9)))

    println("----excersise 3.10:")
    println("Result List.foldLeft(List(1, 2, 3, 4, 9), 0)(_+_))= " + List.foldLeft(List(1, 2, 3, 4, 9), 0)(_ + _))

    println("----excersise 3.11:")
    println("Result List.sumLeft(List(1, 2, 3, 4, 9))= " + List.sumLeft(List(1, 2, 3, 4, 9)))
    println("Result List.productLeft(List(1, 2, 3, 4, 9))= " + List.productLeft(List(1, 2, 3, 4, 9)))
    println("Result List.lengthLeft(List(1, 2, 3, 4, 9))= " + List.lengthLeft(List(1, 2, 3, 4, 9)))

    println("----excersise 3.12:")
    println("Result List.reverse(List(1, 2, 3, 4, 9))= " + List.reverse(List(1, 2, 3, 4, 9)))

    println("----excersise 3.13:")
    println("Result List.foldRightUsingRight(List(1, 2, 3, 4, 9), 0)(_+_))= " + List.foldRightUsingLeft(List(1, 2, 3, 4, 9), 0)(_ + _))
    println("Result List.foldRightUsingRight(List(1, 2, 3, 4, 9), 0)(_*_))= " + List.foldRightUsingLeft(List(1, 2, 3, 4, 9), 1)(_ * _))
    //substituting - see List.scala

    println("----excersise 3.14:")
    println("Result List.append(List(1, 2, 3, 4, 9), 10)= " + List.append(List(1, 2, 3, 4, 9), List(10)))

    println("----excersise 3.15:")
    println("Result  List.concatenate( List ( List(1, 2, 3, 4, 9), List(10,11,12) ))= " + List.concatenate(List(List(1, 2, 3, 4, 9), List(10, 11, 12))))

    println("----excersise 3.16:")
    println("Result   List.addOne(List(1, 2, 3, 4, 9))= " + List.addOne(List(1, 2, 3, 4, 9)))

    println("----excersise 3.17:")
    println("Result   List.doubleToStrings(List(1.2, 2.2, 3.2, 4.2, 9.2))= " + List.doubleToStrings(List(1.2, 2.2, 3.2, 4.2, 9.2)))

    println("----excersise 3.18:")
    println("Result   List.map(List(1.2, 2.2, 3.2, 4.2, 9.2)) (_.toString)= " + List.map_2(List(1.2, 2.2, 3.2, 4.2, 9.2))("s:" + _.toString))

    println("----excersise 3.19:")
    println("Result   List.filter(List(1, 2, 3, 4, 9)) (_%2==0)= " + List.filter(List(1, 2, 3, 4, 9))(_ % 2 > 0))
    println("Result   List.filter_2(List(1, 2, 3, 4, 9)) (_%2==0)= " + List.filter_2(List(1, 2, 3, 4, 9))(_ % 2 > 0))

    println("----excersise 3.20:")
    println("Result   List.flatMap(List(1, 2, 3)) ((i=>List(i,i))= " + List.flatMap(List(1, 2, 3))(i => List(i, i)))

    println("----excersise 3.21:")
    println("Result   List.filterViaFlatMap(List(1, 2, 3, 4, 9)) (_%2>0)= " + List.filterViaFlatMap(List(1, 2, 3, 4, 9))(_ % 2 > 0))

    println("----excersise 3.22:")
    println("Result   List.addPairwise(List(1, 2, 3) List(4, 9))  (_%2>0)= " + List.addPairwise(List(1, 2, 3), List(4, 9)))

    println("----excersise 3.24:")
    println("Result   List.hasSubsequence(List(\"a\", \"b\", \"c\") , List(\"b\", \"c\"))   = " + List.hasSubsequence(List("a", "b", "c"), List("b", "c")))
    println("Result   List.hasSubsequence(List(\"a\", \"b\", \"c\") , List(\"b\", \"c\",\"d\"))   = " + List.hasSubsequence(List("a", "b", "c"), List("b", "c", "d")))

    //------------------------TREE------------------------//
    val tree = Branch(Branch(Leaf("a"),Leaf("b")), Branch(Leaf("c"),Leaf("d")) ) 
    val intTree = Branch(Branch(Leaf(1),Leaf(2)), Branch(Leaf(10),Leaf(9)) )
    val treeDepth = Branch(Branch(Leaf("a"), Branch(Leaf("x"),Leaf("y"))  ), Branch(Leaf("c"),Leaf("d")) )
    
    println("----excersise 3.25:")
    println("Result Tree.size(tree)  = " + Tree.size(intTree))
    
    println("----excersise 3.26:")
    println("Result Tree.max(intTree)  = " + Tree.max(intTree))
    
    println("----excersise 3.27:")
    println("Result Tree.depth(treeDepth)  = " + Tree.depth(treeDepth))
    
    println("----excersise 3.28:")
    println("Result Tree.map(tree)(_ + \"_node\")  = " + Tree.map(tree)(_ + "_node"))

    println("----excersise 3.29:")
    println("Result Tree.sizeViaFold(tree) = " + Tree.sizeViaFold(intTree))
    println("Result Tree.maxViaFold(tree) = " + Tree.maxViaFold(intTree))
    println("Result Tree.depthViaFold(treeDepth) = " + Tree.depthViaFold(treeDepth))

  }
}