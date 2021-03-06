package fpinscala.ch4

import fpinscala.ch4.Option
import fpinscala.ch4.Some
import fpinscala.ch4.None
import scala.collection.immutable.List

object Excercise4 {
  //see https://github.com/fpinscala/fpinscala/blob/master/answers/src/main/scala/fpinscala/datastructures/List.scala
  def main(args: Array[String]) = {
    val some45 = Some(45)
    val none1: Option[Int] = None
    println("some45 = " + some45)
    println("none1 = " + none1)

    println("----excersise 4.1:")

    println("Result some45.map(_ + 3) = " + some45.map(_ + 3))
    println("Result none1.map(_ + 3) = " + none1.map(_ + 3))

    println("Result some45.getOrElse(11) = " + some45.getOrElse(11))
    println("Result none1.getOrElse(11) = " + none1.getOrElse(11))

    println("Result some45.flatMap(a => Some(a + 3)) = " + some45.flatMap(a => Some(a + 3)))
    println("Result none1.flatMap(a => Some(a + 3)) = " + none1.flatMap(a => Some(a + 3)))

    println("Result some45.orElse(Some(\"ala\")) = " + some45.orElse(Some("ala")))
    println("Result none1.orElse(Some(\"ala\")) = " + none1.orElse(Some("ala")))

    println("Result some45.filter(_>10) = " + some45.filter(_ > 10))
    println("Result some45.filter(_<10) = " + some45.filter(_ < 10))
    println("Result none1.filter(_<10) = " + none1.filter(_ < 10))

    println("----excersise 4.2:")

    val se1: Seq[Double] = Seq(1.0, 3.0, 5.0)
    println("Result Option.variance(se1) = " + Option.variance(se1))

    println("----excersise 4.3:")
    val opA:Option[String] = Some("Ala")
    val opB:Option[String] = Some("ma")
    println("Result  Option.map2(opA, opB)((a, b) => a + \" \" + b) = " + Option.map2(opA, opB)((a, b) => a + " " + b))
    
    
    println("----excersise 4.4:")
    val list = scala.collection.immutable.List(Some("Ala") , Some("ma") ) 
    val listWithNone = scala.collection.immutable.List(Some("Ala") , None, Some("ma") )
    println("Result  Option.sequence(list) = " + Option.sequence_1(list))
    println("Result  Option.sequence(listWithNone) = " + Option.sequence_1(listWithNone))
 
    println("----excersise 4.5:")
    val listWithParseableNumbers = scala.collection.immutable.List("1","2","3") 
    val listWithParseableNumbersAndText = scala.collection.immutable.List("1","text")
    def Try[A](a: => A):Option[A] = 
      	try Some(a) catch {case e: Exception=>None}
    println("Result  Option.traverse_1(listWithParseableNumbers)(i => Try(i.toInt) ) = " + Option.traverse_1(listWithParseableNumbers)(i => Try(i.toInt) ))
    println("Result  Option.traverse_1(listWithParseableNumbersAndText)(i => Try(i.toInt) ) = " + Option.traverse_1(listWithParseableNumbersAndText)(i => Try(i.toInt) ))
    

    println("----excersise 4.7:")
    val listRight = scala.collection.immutable.List(Right("Ala") , Right("ma"), Right("kota") ) 
    val listWithLeft = scala.collection.immutable.List(Right("Ala") , Left("nie ma"), Right("ma") )
  
    println("Result Either.sequence(listRight) = " + Either.sequence(listRight))
    println("Result Either.sequence(listWithLeft) = " + Either.sequence(listWithLeft))

    println("----excersise 4.8:")
  
  }
}