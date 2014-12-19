package fpinscala.ch5

import scala.collection.immutable.List

object Excercise5 {
  //see https://github.com/fpinscala/fpinscala/blob/master/answers/src/main/scala/fpinscala/datastructures/List.scala
  def main(args: Array[String]) = {
    
    println("----excersise 5.1:")
    val x = Stream("a","b","c","d")
    println("x.toListNotTailRecursive:" + x.toListNotTailRecursive)
    println("x.toList:" + x.toList)
    
    println("----excersise 5.2:")
    println("x.take(2): " + x.take(2).toList)
    println("x.drop(2): " + x.drop(2).toList)
    println("x.takeWhile(_<\"d\"): " + x.takeWhile(_<"d").toList)
    
  
  }
}