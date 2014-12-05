package fpinscala.ch3

sealed trait List[+A]
case object Nil extends List[Nothing]

case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {

  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  //Excercise 3.1
  val x = List(1, 2, 3, 4, 5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101
  }

  //Excercise 3.2  
  def tail[A](list: List[A]): List[A] = list match {
    case Nil => sys.error("Error: tail of empty list")
    case Cons(_, xs) => xs
  }

  //Excercise 3.3  
  def setHead[A](list: List[A], elem: A): List[A] = list match {
    case Nil => sys.error("Error: empty list")
    case Cons(_, xs) => Cons(elem, xs)
  }

  //Excercise 3.4  
  def drop[A](list: List[A], n: Int): List[A] =
    //    list match {
    //      case Nil =>Nil        
    //      case Cons(_, xs) =>
    //        if (n > 1) drop(xs, n - 1)
    //        else xs
    //    }
    if (n > 0) list match {
      case Nil => Nil
      case Cons(_, xs) => drop(xs, n - 1)
    }
    else list

  //Excercise 3.4  
  def dropWhile[A](list: List[A], f: A => Boolean): List[A] =
    list match {
      //      case Cons(x, xs) =>
      //        if (f(x)) dropWhile(xs, f)
      //        else list
      //      case _ => list

      //more concise solution  !!!:
      case Cons(x, xs) if f(x) => dropWhile(xs, f)
      case _ => list
    }
  
   def dropWhileTypeInterf[A](list: List[A])(f: A => Boolean): List[A] =
    list match {
      //      case Cons(x, xs) =>
      //        if (f(x)) dropWhile(xs, f)
      //        else list
      //      case _ => list

      //more concise solution  !!!:
      case Cons(x, xs) if f(x) => dropWhile(xs, f)
      case _ => list
    }

  def init[A](l: List[A]): List[A] = {
    // can lead to stack overflows for large lists:
//    l match {
//      case Nil => sys.error("init of empty list")
//      case Cons(_, Nil) => Nil
//      case Cons(x, xs) => Cons(x, init(xs))
//    }
    
    import collection.mutable.ListBuffer
    val buf = new ListBuffer[A]
    @annotation.tailrec
    def go(cur: List[A]): List[A] = cur match {
      case Nil => sys.error("init of empty list")
      case Cons(_,Nil) => List(buf.toList: _*)
      case Cons(h,t) => buf += h; go(t)
    }
    go(l)
  }
   

  
  def sum2(ns: List[Int]) = 
    foldRight(ns, 0)((x,y) => x + y)
  
  def product2(ns: List[Double]) = 
    foldRight(ns, 1.0)(_ * _) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar

  def length[A](ns:List[A]) = 
    foldRight(ns, 0)((_,y)=>y+1)
    
  @annotation.tailrec  
  def foldLeft[A,B](as: List[A], z: B)(f: (B, A) => B): B =
    as match {
      	case Nil => z
      	case Cons(x, xs) => foldLeft( xs,  f(z,x) )(f)
    }
  
  def sumLeft(ns: List[Int]) = 
    foldLeft(ns, 0)(_ + _) 
  def productLeft(ns: List[Double]) = 
    foldLeft(ns, 1.0)(_ * _) 
  def lengthLeft[A](ns:List[A]) = 
    foldLeft(ns, 0) ((acc,_) => acc+1 )
  def reverse[A](ns:List[A]) = 
    foldLeft(ns, List[A]()) ( (acc, x) => Cons(x , acc) )
  def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = // Utility functions
    as match {
    case Nil => z
    case Cons(x, xs) => f(x, foldRight(xs, z)(f) )
    }  
  def foldRightUsingLeft[A,B](as: List[A], z: B)(f: (A, B) => B): B =
    foldLeft(reverse(as), z)( (x,y) => f(y,x))    
  //Copied from answers -  build up a chain of functions which, when called, results in the operations being performed with the correct associativity:
  def foldRightViaFoldLeft_1[A,B](l: List[A], z: B)(f: (A,B) => B): B = 
    foldLeft(l, (b:B) => b) ( (g,a) => b => g(f(a,b)) ) (z)
    
  def foldLeftViaFoldRight[A,B](list: List[A], z: B)(f: (B,A) => B): B = 
    foldRight(list, (b:B) => b)    ((a,g) => b => g(f(b,a)))        (z)
    
   /*
    foldLeftViaFoldRight(List(1,2,3), 0) (_ + _) 
    = foldRight(List(1,2,3),                    (b:B) => b)     ((a,g) => b => g(b + a))       (0)
    = foldRight(Cons(1, Cons(2, Cons(3, Nul))), (b:B) => b)     ((a,g) => b => g(b + a))       (0)
    	///case with pair of arguments :		1, foldRight(Cons(2, Cons(3, Nul)) , (b:B) => b)    ((a,g) => b => g(b + a))	must be put into :			    			(a,g) => b => g(b + a)
    = ( b =>    foldRight(Cons(2, Cons(3, Nul)) , (b:B) => b)    ((a,g) => b => g(b + a))  (b + 1)         )    (0)
    = ( b =>    (     b =>    foldRight(Cons(3, Nul) , (b:B) => b)    ((a,g) => b => g(b + a))  (b + 2)         )  (b + 1)                                    )   (0)
    = ( b =>    (     b =>    (     b =>    foldRight(Nil , (b:B) => b)    ((a,g) => b => g(b + a))  (b + 3)         )        (b + 2)         )  (b + 1)                                    )   (0)
    = ( b =>    (     b =>    (     b =>    (b:B)=>b  		(b + 3)         )        (b + 2)         )  (b + 1)                                    )   (0)
    = ( b =>    (     b =>    (b:B)=>b  		(b + 3)         )        (b + 2)         )  (0 + 1)
    = ( b =>    (     b =>    (b:B)=>b  		(b + 3)         )        (b + 2)         )  (1)
    = ( b =>    (b:B)=>b  		(b + 3)         )        (1 + 2)  
    = ( b =>    (b:B)=>b  		(b + 3)         )        (3)  
    = (b:B)=>b  		(3 + 3)
    = (b:B)=>b  		(6)
    = (b:B)=>b  		(6)
    = 6
    */ 

    
   def append[A](ns:List[A], listToAdd:List[A]):List[A] =	 foldRight(ns, listToAdd) (Cons(_,_))
   
   def concatenate[A](lists:List[List[A]]):List[A] = {
    //because append is depending on first argument lenght - this will work OK to requirements: 
    foldRight( lists, Nil:List[A] ) (append)
    //non-well-writen version:    foldRight( lists, Nil:List[A] ) ((l1,l2) => append(l1,l2) )
    //other version:
	//     def func[A](acc:List[A] , lists2:List[List[A]]):List[A] = 
	//       lists2 match{
	//         case Nil => acc
	//         case Cons(l1,tail) => func( append(acc, l1) , tail)  
	//       }
	//     func(Nil,  lists)
  }
  
  def map[A,B](list:List[A])(f:(A) => B):List[B] =
    //not stack-safe:
    //  foldRight( list, Nil:List[B]) ( (a,b) => Cons(f(a),b))
    //based on stack-safe foldRight:
    foldRightViaFoldLeft_1( list, Nil:List[B]) ( (a,b) => Cons(f(a),b))

  
    //based on locally done mutation:
  def map_2[A,B](l: List[A])(f: A => B): List[B] = {
	val buf = new collection.mutable.ListBuffer[B]
	@annotation.tailrec 
    def go(l:List[A]):Unit = l match {
	  case Nil => ()
	  case Cons(h,t) => buf += f(h) ; go(t) 
    }
    go(l)
    List(buf.toList: _*) // converting from the standard Scala list to the list we've defined here
  }
  
  def addOne(list:List[Int]):List[Int] = 
      foldRight( list, Nil:List[Int]) ( (a,b) => Cons(a+1,b))
    	//List.map_2(list)(_+1)
    
  def doubleToStrings(list:List[Double]):List[String] = 
       foldRight( list, Nil:List[String]) ( (a,b) => Cons(a.toString,b))
    	//List.map_2(list)(_.toString )
    
  def filter[A](l: List[A])(f: A => Boolean): List[A] = {
//     l match {
//	  case Nil => Nil
//	  case Cons(h,t) => if(f(h)) Cons(h, filter(t)(f)) else filter(t)(f) 
//    }
    foldRight(l, Nil:List[A])  ( (h,t) => if (f(h)) Cons(h,t) else t )
  } 
  def filter_2[A](l: List[A])(f: A => Boolean): List[A] = {
    val buf = new collection.mutable.ListBuffer[A]
	@annotation.tailrec 
    def go(l:List[A]):Unit = l match {
	  case Nil => ()
	  case Cons(h,t) => if(f(h)) buf += h ; go(t) 
    }
    go(l)
    List(buf.toList: _*) // converting from the standard Scala list to the list we've defined here
  }
   
   def flatMap[A,B](l:List[A])(f:A=>List[B]):List[B] = 
    	   concatenate(map_2(l)(f))
		   
   def filterViaFlatMap[A](l: List[A])(f: A => Boolean): List[A] =
		   flatMap(l)(  (a) => if(f(a))  List(a) else Nil )
		   
  def addPairwise(l1: List[Int], l2:List[Int]) :List[Int] = (l1,l2) match {
    	case (Nil , _) => Nil
    	case (_, Nil) => Nil
    	case (Cons(h1,t1), Cons(h2,t2)) => Cons( h1 + h2 , addPairwise(t1, t2))
    }
   
   
   def zipWith[A,B,C](a: List[A], b: List[B])(f: (A,B) => C): List[C] = (a,b)  match {
    	case (Nil , _) => Nil
    	case (_, Nil) => Nil
    	case (Cons(h1,t1), Cons(h2,t2)) => Cons( f(h1 , h2) , zipWith(t1, t2)(f))
    }
   
   
    def startsWith[A](l: List[A], sub: List[A]): Boolean = (l,sub) match {
      case (_ , Nil)=> true
      case (Cons(h,t), Cons(subh,subt)) if(h==subh) => startsWith(t, subt) 
      case _ => false
    }
    
    def hasSubsequence[A](l: List[A], sub: List[A]): Boolean = l match {
      case Nil => false
      case Cons(h,t) if startsWith(l, sub) => true  
      case Cons(h,t) => hasSubsequence(t, sub)
    }
}
