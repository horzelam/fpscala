package fpinscala.ch6

import scala.collection.immutable.List

object E {
  def main(args: Array[String]) = {
    println("----excersise 6.1:")
    println("RNG.nonNegativeInt(new RNG.Simple(0))=" + RNG.nonNegativeInt(new RNG.Simple(0)));
    println("RNG.nonNegativeInt(new RNG.Simple(1))=" + RNG.nonNegativeInt(new RNG.Simple(1)));
    println("RNG.nonNegativeInt(new RNG.Simple(2))=" + RNG.nonNegativeInt(new RNG.Simple(2)));
    println("RNG.nonNegativeInt(new RNG.Simple(123123123))=" + RNG.nonNegativeInt(new RNG.Simple(123123123)));
    println("----excersise 6.1:")
    println("RNG.nonNegativeInt(new RNG.Simple(0))=" + RNG.double(new RNG.Simple(0)));
    println("RNG.nonNegativeInt(new RNG.Simple(1))=" + RNG.double(new RNG.Simple(1)));
    println("RNG.nonNegativeInt(new RNG.Simple(Int.MaxValue))=" + RNG.double(new RNG.Simple(Int.MaxValue)));
    println("RNG.nonNegativeInt(new RNG.Simple(Int.MinValue))=" + RNG.double(new RNG.Simple(Int.MinValue)));
    println("RNG.nonNegativeInt(new RNG.Simple(123123123))=" + RNG.double(new RNG.Simple(123123123)));
    println("----excersise 6.4:")
    println("RNG.ints(3)(RNG.Simple(0))=" + RNG.ints(3)(RNG.Simple(0)));
    println("----excersise 6.5:")
    println("RNG.randIntDoube(RNG.Simple(1))=" + RNG.randIntDoube(RNG.Simple(1)));
    println("----excersise 6.7:")
    println(" RNG.sequence(List(RNG.int,RNG.int,RNG.int))(RNG.Simple(1)) =" + RNG.sequence(List(RNG.int, RNG.int, RNG.int))(RNG.Simple(1)));
    println("----excersise 6.8:")
    println(" RNG.nonNegativeLessThan(10)(RNG.Simple(1)) =" + RNG.nonNegativeLessThan(10)(RNG.Simple(1001)));
    println("----excersise 6.9:")
    println(" RNG.nonNegativeLessThan(10)(RNG.Simple(1)) =" + RNG.map2ViaFlatMap(RNG.nonNegativeLessThan(10), RNG.doubleViaMap)("i:" + _ + ",d:" + _)(RNG.Simple(2123)));

    println("----excersise 6.11:")
    val machine = new Machine(true, 5, 10)
    val emptyMachine = new Machine(true, 0, 10)
    println("Initialized machine: " + machine)
    val inputs = (1 to 4).toList.flatMap(a => List(Coin, Turn)) //::: List(Turn,Turn,Turn,Coin,Coin,Coin,Coin,Coin) 

    println("Inputs: " + inputs.mkString(";"))
    println("Candy.simulateMachine(  inputs ).run(machine)                = " + Candy.simulateMachine(inputs)          .run(machine) + " ///// EXPECTED: (1,14)");
    println("Candy.simulateMachine(  List(Coin) ).run(machine)            = " + Candy.simulateMachine(List(Coin))      .run(machine) + " ///// EXPECTED: (5,11)");
    println("Candy.simulateMachine(  List(Coin, Turn) ).run(machine)      = " + Candy.simulateMachine(List(Coin, Turn)).run(machine) + " ///// EXPECTED: (4,11)");
    println("Candy.simulateMachine(  List(Turn, Turn) ).run(machine)      = " + Candy.simulateMachine(List(Turn, Turn)).run(machine) + " ///// EXPECTED: (5,10)");
    println("Candy.simulateMachine(  List(Turn, Coin) ).run(machine)      = " + Candy.simulateMachine(List(Turn, Coin)).run(machine) + " ///// EXPECTED: (5,11)");
    println("Candy.simulateMachine(  List(Coin, Turn) ).run(emptyMachine) = " + Candy.simulateMachine(List(Turn, Turn)).run(emptyMachine) + " ///// EXPECTED: (0,10)");
  }
}
