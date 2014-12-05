package fpinscala.ch3

sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {
  def size[A](t: Tree[A]): Int = t match {
    case Leaf(_) => 1
    case Branch(a, b) => 1 + size(a) + size(b)
  }

  def max(t: Tree[Int]): Int = t match {
    case Leaf(a) => a
    case Branch(a, b) => max(a) max max(b)
  }
  def depth[A](t: Tree[A]): Int = t match {
    case Leaf(_) => 1
    case Branch(a, b) => 1 + depth(a) max depth(b)
  }

  def map[A, B](t: Tree[A])(f: A => B): Tree[B] = t match {
    case Leaf(a) => Leaf(f(a))
    case Branch(l, r) => Branch(map(l)(f), map(r)(f))
  }

  def fold[A, B](t: Tree[A])(z: A => B)(f: (B, B) => B): B = t match {
    case Leaf(a) => z(a)
    case Branch(l, r) => f(fold(l)(z)(f), fold(r)(z)(f))
  }

  def sizeViaFold[A](t: Tree[A]): Int =
    fold(t)(a => 1)(1 + _ + _)

  def maxViaFold[Int](t: Tree[scala.Int]): scala.Int =
    fold(t)(a => a)(_ max _)

  def depthViaFold[A](t: Tree[A]): Int =
    fold(t)(a => 1)(1 + _ max _)

  def mapViaFold[A, B](t: Tree[A])(f: A => B): Tree[B] =
    fold(t)(a => Leaf(f(a)): Tree[B])(Branch(_, _))
  //    { //with the helper function
  //      def leafFunc(a: B): Tree[B] = Leaf(a)
  //      fold(t)(a => leafFunc(f(a)))(Branch(_, _))
  //    }

}