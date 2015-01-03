package fpinscala.ch6

trait RNG {
  def nextInt: (Int, RNG) // Should generate a random `Int`. We'll later define other functions in terms of `nextInt`.
}

object RNG {
  // NB - this was called SimpleRNG in the book text

  case class Simple(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL // `&` is bitwise AND. We use the current seed to generate a new seed.
      val nextRNG = Simple(newSeed) // The next state, which is an `RNG` instance created from the new seed.
      val n = (newSeed >>> 16).toInt // `>>>` is right binary shift with zero fill. The value `n` is our new pseudo-random integer.
      (n, nextRNG) // The return value is a tuple containing both a pseudo-random integer and the next `RNG` state.
    }
  }

  type Rand[+A] = RNG => (A, RNG)

  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] =
    rng => (a, rng)

  def map[A, B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }

  def nonNegativeInt(rng: RNG): (Int, RNG) = {
    //    RNG.map(rng => rng.nextInt)(a => if (a < 0) -(a + 1) else a)(rng)
    val (a, r) = rng.nextInt
    (if (a < 0) -(a + 1) else a, r)
    //rng.nextInt match { case (a,b) => (  if(a<0) -(a+1) else a,b) }
  }

  def double(rng: RNG): (Double, RNG) = {
    val (a, r) = nonNegativeInt(rng)
    (a / (Int.MaxValue.toDouble + 1), r)
  }

  def doubleViaMap: Rand[Double] = {
    map(nonNegativeInt)(_ / (Int.MaxValue.toDouble + 1))
  }

  def intDouble(rng: RNG): ((Int, Double), RNG) = {
    val (i, r1) = rng.nextInt
    val (d, r2) = double(r1)
    ((i, d), r2)
  }

  def doubleInt(rng: RNG): ((Double, Int), RNG) = {
    val ((i, d), r1) = intDouble(rng)
    ((d, i), r1)
  }

  def double3(rng: RNG): ((Double, Double, Double), RNG) = {
    val (d1, r1) = double(rng)
    val (d2, r2) = double(r1)
    val (d3, r3) = double(r2)
    ((d1, d2, d3), r3)
  }

  def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
    if (count < 1) {
      (List(), rng)
    } else {
      val (i, r1) = rng.nextInt
      val (list1, r2) = ints(count - 1)(r1)
      (i :: list1, r2)
    }

  }

  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = {
    rng =>
      {
        val (a, ra1) = ra(rng)
        val (b, rb2) = rb(ra1)
        (f(a, b), rb2)
      }
  }
  def both[A, B](ra: Rand[A], rb: Rand[B]): Rand[(A, B)] =
    map2(ra, rb)((_, _))

  val randIntDoube: Rand[(Int, Double)] = both(int, double)

  def sequence1[A](fs: List[Rand[A]]): Rand[List[A]] = {
    rng =>
      fs match {
        case Nil => (List(), rng)
        case x :: xs => {
          val (a, r1) = x(rng)
          val (listA, r2) = sequence1(xs)(r1)
          (a :: listA, r2)
        }
      }
  }

  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] =
    fs.foldRight(unit(List[A]()))((f, b) => map2(f, b)(_ :: _))

  def flatMap[A, B](f: Rand[A])(g: A => Rand[B]): Rand[B] =
    rng => {
      val (a, r1) = f(rng)
      g(a)(r1)
    }

  def nonNegativeLessThan(n: Int): Rand[Int] = {
    flatMap(nonNegativeInt) { i =>
      val mod = i % n
      if (i + (n - 1) - mod >= 0)
        unit(mod)
      else
        nonNegativeLessThan(n)
    }
  }

  def mapViaFlatMap[A, B](s: Rand[A])(f: A => B): Rand[B] =
    flatMap(s)(i => unit(f(i)))

  def map2ViaFlatMap[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] =
    //flatMap(rb)(b => flatMap(ra)(a => unit(f(a, b))))
    flatMap(ra)(a => mapViaFlatMap(rb)(b => f(a, b)))

}

import State._
case class State[S, +A](run: S => (A, S)) {

  def map[B](f: A => B): State[S, B] =
    flatMap(a => State.unit(f(a)))

  def map2[B, C](sb: State[S, B])(f: (A, B) => C): State[S, C] =
    flatMap(a => sb.map(b => f(a, b)))

  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State(s => {
      val (a, r1) = run(s)
      f(a).run(r1)
    })

}

object State {
  def unit[S, A](a: A): State[S, A] =
    State(s => (a, s))

  //Type Rand is now just alias for specific state:
  type Rand[A] = State[RNG, A]

  def sequenceFoldRight[S, A](sas: List[State[S, A]]): State[S, List[A]] =
    sas.foldRight(State.unit[S, List[A]](List[A]()))((f, acc) => f.map2(acc)(_ :: _))

  def sequenceViaFoldLeft[S, A](l: List[State[S, A]]): State[S, List[A]] =
    l.reverse.foldLeft(State.unit[S, List[A]](List[A]()))((acc, f) => f.map2(acc)(_ :: _))

  def sequence[S, A](sas: List[State[S, A]]): State[S, List[A]] = {
    def go(s: S, actions: List[State[S, A]], acc: List[A]): (List[A], S) =
      actions match {
        case Nil => (acc.reverse, s)
        case h :: t => h.run(s) match { case (a, s2) => go(s2, t, a :: acc) }
      }
    State((s: S) => go(s, sas, List()))
  }

  def modify[S](f: S => S): State[S, Unit] = for {
    s <- get // Gets the current state and assigns it to `s`.
    _ <- set(f(s)) // Sets the new state to `f` applied to `s`.
  } yield ()

  def get[S]: State[S, S] = State(s => (s, s))

  def set[S](s: S): State[S, Unit] = State(_ => ((), s))

}

sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int) {
  override def toString(): String = {
    return "Machine(locked: " + locked + ", candies: " + candies + " coins: " + coins + ")"
  }
}

object Candy {

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = {
    val stateListMapped: List[State[Machine, Unit]] = inputs.map(input => State.modify((s: Machine) => (input, s) match {
      case (_, Machine(_, 0, _)) => s
      case (Coin, Machine(false, _, _)) => s
      case (Turn, Machine(true, _, _)) => s
      case (Coin, Machine(true, candy, coin)) => Machine(false, candy, coin + 1)
      case (Turn, Machine(false, candy, coin)) => Machine(true, candy - 1, coin)
    }));
    for {
      _ <- sequence(stateListMapped)
      s <- get
    } yield (s.coins, s.candies)
    //    or in flatMap+map:
    //    sequence(stateListMapped) flatMap ( 
    //    		_ => get map (
    //    						s => (s.coins,s.candies)
    //    					 ) 
    //        ) 
  }

  def simulateMachine1(inputs: List[Input]): State[Machine, (Int, Int)] = {
    //val initState = State.unit[Machine, (Int, Int)]((s.coins, s.candies)) 
    State(
      s => ({
        val finalState = inputs.foldLeft(s)(
          (currState, input) => {
            (input, currState) match {
              //inserting coin into locked while there is some candy
              case (Coin, Machine(true, candies, coins)) if (currState.candies > 0) => Machine(false, candies, coins + 1)
              //turn the knob on an unlocked machine              
              case (Turn, Machine(false, candies, coins)) if (currState.candies > 0) => Machine(true, candies - 1, coins)
              //all the rest
              case _ => currState
            }
          })
        ((finalState.candies, finalState.coins), finalState)
      }))
  }

}
