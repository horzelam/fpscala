object test {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(57); 
  println("Welcome to the Scala worksheet");$skip(62); val res$0 = 
  
  fpinscala.ch5.Stream.ones.map( _ + 1) .exists( _ %2 ==0);System.out.println("""res0: Boolean = """ + $show(res$0));$skip(46); val res$1 = 
  fpinscala.ch5.Stream.ones.takeWhile( _ ==1);System.out.println("""res1: fpinscala.ch5.Stream[Int] = """ + $show(res$1));$skip(44); val res$2 = 
  fpinscala.ch5.Stream.ones.forAll( _ != 1);System.out.println("""res2: Boolean = """ + $show(res$2))}
}
