package fpinscala.ch5

import Stream._
trait Stream[+A] {

  def foldRight[B](z: => B)(f: (A, => B) => B): B = // The arrow `=>` in front of the argument type `B` means that the function `f` takes its second argument by name and may choose not to evaluate it.
    this match {
      case Cons(h, t) => f(h(), t().foldRight(z)(f)) // If `f` doesn't evaluate its second argument, the recursion never occurs.
      case _ => z
    }

  def exists(p: A => Boolean): Boolean =
    foldRight(false)((a, b) => p(a) || b) // Here `b` is the unevaluated recursive step that folds the tail of the stream. If `p(a)` returns `true`, `b` will never be evaluated and the computation terminates early.

  @annotation.tailrec
  final def find(f: A => Boolean): Option[A] = this match {
    case Empty => None
    case Cons(h, t) => if (f(h())) Some(h()) else t().find(f)
  }

  final def take(n: Int): Stream[A] = {
    if (n > 0)
      this match {
        case Cons(h, t) if n == 1 => cons(h(), Stream.empty)
        case Cons(h, t) => cons(h(), t().take(n - 1))
        case _ => Stream.empty
      }
    else {
      Stream()
    }

  }

  def drop(n: Int): Stream[A] = {
    //    if (n > 0)
    //      this match {
    //        case Cons(h, t) if n == 1 => t()
    //        case Cons(h, t) => t().drop(n - 1)
    //        case _ => Stream.empty
    //      }
    //    else {
    //      this
    //    }
    @annotation.tailrec
    def go(org: Stream[A], n: Int): Stream[A] =
      if (n <= 0) org
      else org match {
        case Cons(h, t) => go(t(), n - 1)
        case _ => Stream()
      }
    go(this, n)
  }

  def takeWhile(p: A => Boolean): Stream[A] =
    this match {
      case Cons(h, t) if p(h()) => cons(h(), t() takeWhile p)
      case _ => Stream.empty
    }

  def takeWhileWithFold(p: A => Boolean): Stream[A] =
    foldRight(empty[A])((a, b) => if (p(a)) cons(a, b) else empty)

  def forAll(p: A => Boolean): Boolean =
    foldRight(true)((a, b) => p(a) && b)

  def startsWith[B](s: Stream[B]): Boolean =
    zipAll(s).takeWhile(!_._2.isEmpty).forAll { case (a, b) => a == b }

  def tails: Stream[Stream[A]] =
    //Original solution 
        unfold(this) { 
          case Empty => None
          case s => Some((s, s drop 1))
        } append (Stream(empty))
    //mine solution:
    //is this OK ? 
//    unfold(this)(str => str match {
//      case Empty => None
//      case Cons(h, t) => Some((str, t()))
//    }) append (Stream(empty))

 def scanRight[B](z: B)(f: (A,=>B) => B): Stream[B] = 
 foldRight((z, Stream(z))) ((a,p) => {
      val b2 = f(a,p._1)
      (b2, cons(b2,p._2))
    })._2
 
  def toListNotTailRecursive: List[A] = this match {
    case Cons(h, t) => h() :: t().toList
    case _ => List()
  }

  def headOption: Option[A] =
    foldRight(None: Option[A])((a, _) => Some(a))

  def map[B](f: A => B): Stream[B] =
    foldRight(empty[B])((a, b) => cons(f(a), b))

  def filter[B](f: A => Boolean): Stream[A] =
    foldRight(empty[A])((a, b) => if (f(a)) cons(a, b) else b)

  def append[B >: A](s: => Stream[B]): Stream[B] =
    foldRight(s)((a, b) => cons(a, b))

  def flatMap[B](f: A => Stream[B]): Stream[B] =
    foldRight(empty[B])((a, b) => f(a).append(b))

  def toList: List[A] = {
    @annotation.tailrec
    def go(org: Stream[A], acc: List[A]): List[A] = org match {
      case Cons(h, t) => go(t(), h() :: acc)
      case Empty => acc
    }
    go(this, List()).reverse
  }

  def mapViaUnfold[B](f: A => B): Stream[B] =
    unfold(this)(s => s match {
      case Cons(h, t) => Some((f(h()), t()))
      case _ => None
    })
  //  //or just !:
  //  unfold(this) {
  //    case Cons(h, t) => Some((f(h()), t()))
  //    case _ => None
  //  }
  def takeViaUnfold(n: Int): Stream[A] =
    unfold((this, n)) {
      case (Cons(h, t), currN) if currN == 1 => Some((h(), (empty, n - 1)))
      case (Cons(h, t), currN) if currN > 0 => Some((h(), (t(), currN - 1)))
      case _ => None
    }

  def takeWhileViaUnfold(f: A => Boolean): Stream[A] =
    unfold((this)) {
      case Cons(h, t) if f(h()) => Some((h(), t()))
      case _ => None
    }

  def zipWith[B, C](s2: Stream[B])(f: (A, B) => C): Stream[C] =
    unfold((this, s2)) {
      case (Cons(h, t), Cons(h2, t2)) => Some((f(h(), h2()), (t(), t2())))
      case _ => None
    }

  def zipAll[B](s2: Stream[B]): Stream[(Option[A], Option[B])] =
    unfold((this, s2)) {
      case (Cons(h, t), Cons(h2, t2)) => Some(((Some(h()), Some(h2())), (t(), t2())))
      case (Cons(h, t), Empty) => Some(((Some(h()), None), (t(), Empty)))
      case (Empty, Cons(h2, t2)) => Some(((None, Some(h2())), (Empty, t2())))
      case _ => None
    }

  def hasSubsequence[A](s: Stream[A]): Boolean = this match {
    case Empty => false
    case Cons(h, t) if this.startsWith(s) => true
    case Cons(h, t) => t().hasSubsequence(s)
  }

}
case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty
  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = Stream.cons(1, ones)
  def constant[A](a: A): Stream[A] = { //Stream.cons(a, constant(a));
    lazy val tail: Stream[A] = Cons(() => a, () => tail)
    tail
  }

  def from(n: Int): Stream[Int] =
    Stream.cons(n, from(n + 1))

  def fibs(): Stream[Int] = {
    def go(i1: Int, i2: Int): Stream[Int] =
      cons(i1, go(i2, i1 + i2))

    go(0, 1)
  }
  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] =
    f(z) match {
      case Some((a, s)) => cons(a, unfold(s)(f))
      case None => Stream.empty
    }

  def fibsViaUnfold(): Stream[Int] =
    Stream.unfold((0, 1))(z => Some((z._1, (z._2, z._1 + z._2))))

  def fromViaUnfold(n: Int) =
    Stream.unfold(n)(x => Some((x, x + 1)))

  def constantViaUnfold[A](a: A) =
    Stream.unfold(a)(_ => Some((a, a)))

  val onesViaUnfold =
    Stream.unfold(1)(_ => Some((1, 1)))
}