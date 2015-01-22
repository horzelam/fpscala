package fpinscala.ch7

import java.util.concurrent.ExecutorService

import java.util.concurrent.Executors

object Excercise7 {
  def main(args: Array[String]) = {
    println("Excercise 7.6")
    val ints = ((1 to 9).toList reverse) ::: ((10 to 19).toList reverse)
    println("ints: " + ints.mkString(";"))
    val es: ExecutorService = Executors.newCachedThreadPool()
    println(" Par.max(ints)= " + Par.run(es)(Par.max(ints.toIndexedSeq)).get);
    println(" Par.sum(ints)= " + Par.run(es)(Par.sum(ints.toIndexedSeq)).get + " ; expected: " + ints.sum);

    
    val pargphs:List[String]= "  Par with four words    " ::"  Par with four words    " :: "   Par four five other words   " :: Nil
    
    println("");
    println("Paragraphs to count: " + pargphs.mkString(" || "))
    
    println(" Par.countWords(pargphs)= " + Par.run(es)(Par.countWords(pargphs.toIndexedSeq)).get() + " ; expected: " + pargphs.foldRight(0)(_.trim().split("\\W+").size + _));
    
    es.shutdown()
 
  }
}