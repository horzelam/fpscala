package fpinscala.ch5

import scala.collection.immutable.List

object Excercise5 {
  //see https://github.com/fpinscala/fpinscala/blob/master/answers/src/main/scala/fpinscala/datastructures/List.scala
  def main(args: Array[String]) = {

    println("----excersise 5.1:")
    val x = Stream("a", "b", "c", "d")
    println("x.toListNotTailRecursive:" + x.toListNotTailRecursive)
    println("x.toList:" + x.toList)

    println("----excersise 5.2:")
    println("x.take(2): " + x.take(2).toList)
    println("x.drop(2): " + x.drop(2).toList)

    println("----excersise 5.3:")
    println("x.takeWhile(_<\"d\"): " + x.takeWhile(_ < "d").toList)

    println("----excersise 5.4:")
    println("x.forAll(_>=\"a\"): " + x.forAll(_ >= "a"))

    println("----excersise 5.5:")
    println("x.takeWhileWithFold(_<\"d\"): " + x.takeWhileWithFold(_ < "d").toList)

    println("----excersise 5.6:")
    println("x.headOption: " + x.headOption)
    println("Stream().headOption: " + Stream().headOption)

    println("----excersise 5.7:")
    println("x.map( _ + \"x\").toList: " + x.map(_ + "x").toList)
    println("x.filter( _ != \"b\").toList: " + x.filter(_ != "b").toList)
    println("x.append(Stream(\"e\",\"f\")).toList: " + x.append(Stream("e", "f")).toList)
    println("Stream(\"abc\",\"def\").flatMap( a=>Stream( a.toCharArray(): _* )).toList: " + Stream("abc", "def").flatMap(a => Stream(a.toCharArray(): _*)).toList)

    println("ones.map(_ + 1).exists(_ % 2 == 0): " + Stream.ones.map(_ + 1).exists(_ % 2 == 0))
    println("ones.takeWhile(_ == 1): " + Stream.ones.takeWhile(_ == 1))
    println("ones.forAll(_ != 1): " + Stream.ones.forAll(_ != 1))
    
    
    println("----excersise 5.9:")
    println("Stream.from(5).takeWhile(_ < 10).toList: " + Stream.from(5).takeWhile(_ < 10).toList)
    
    println("----excersise 5.10:")
    println("Stream.fibs().takeWhile(_ < 20).toList: " + Stream.fibs().takeWhile(_ < 20).toList)
    
    println("----excersise 5.12:")
    println("Stream.fibsViaUnfold().takeWhile(_ < 20).toList: " + Stream.fibsViaUnfold().takeWhile(_ < 20).toList)
    println("Stream.fromViaUnfold(5).takeWhile(_ < 10).toList: " + Stream.fromViaUnfold(5).takeWhile(_ < 10).toList)

    println("----excersise 5.13:")
    println("x.mapViaUnfold(_+\"x\").toList: " + x.mapViaUnfold(_+"x").toList);
    println("x.takeViaUnfold(3).toList: " + x.takeViaUnfold(3).toList);
    println("x.takeWhileViaUnfold(_<\"d\").toList: " + x.takeWhileViaUnfold(_<"d").toList);
    println("x.zipWith(Stream(\"a\", \"b\", \"c\", \"d\"))(_+_).toList: " + x.zipWith(Stream("a", "b", "c", "d"))(_+_).toList);
    println("x.zipAll(Stream(1, 2, 3)).toList: " + x.zipAll(Stream(1, 2, 3)).toList);
 
    println("----excersise 5.14:")
    val abc =  Stream("a", "b", "c") // Stream( "abc": _* ) -- to jest stream of chars , nie stream of string
    println("val abc = " + abc.toList);
    println("x.startsWith(abc).toList: " + x.startsWith(abc));
    println("----excersise 5.15:")
    println("x.tails: " + x.tails.map( str => { str.toList} ).toList);
    val gabcd =  Stream("g").append(x) 
    println("gabcd.hasSubsequence(abc): " + gabcd.hasSubsequence(abc));
    
    println("----excersise 5.16:")
    println("gabcd.scanRight(\"_\")(_+_).toList: " + gabcd.scanRight("_")(_+_).toList);
  }
}