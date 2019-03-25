package com.ora.scalacoreprogramming

import org.scalatest.{FunSuite, Matchers}

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.{Failure, Success, Try}

class SealedTraitsSpec extends FunSuite with Matchers {
  test(
    """A trait is analogous to an interface in Java, any method with
      |  no concrete implementation is considered abstract. Any subsequent
      |  inheritance of a trait is added using the keyword \"with\""""
      .stripMargin) {

    trait Vehicle {
      def increaseSpeed(ms: Int): Vehicle

      def decreaseSpeed(ms: Int): Vehicle

      def currentSpeedMetersPerHour: Int
    }

    trait Fun
    trait FreshAir

    case class Bicycle(currentSpeedMetersPerHour: Int)
      extends Vehicle with Fun with FreshAir {

      override def increaseSpeed(ms: Int): Vehicle =
        this.copy(currentSpeedMetersPerHour + ms)

      override def decreaseSpeed(ms: Int): Vehicle =
        this.copy(currentSpeedMetersPerHour - ms)
    }


    Bicycle(1)
      .increaseSpeed(3)
      .decreaseSpeed(1)
      .currentSpeedMetersPerHour should be(3) //should is a ScalaTest
  }

  test(
    """Just like Java 8 interfaces, you can have concrete
      |  methods (known as default methods in Java). In the
      |  example below: currentSpeedMilesPerHour""".stripMargin) {

    trait Vehicle {
      def increaseSpeed(ms: Int): Vehicle

      def decreaseSpeed(ms: Int): Vehicle

      def currentSpeedMetersPerHour: Int

      final def currentSpeedMilesPerHour: Double =
        currentSpeedMetersPerHour * 0.000621371
    }

    class Bicycle(val currentSpeedMetersPerHour: Int) extends Vehicle {
      override def increaseSpeed(mh: Int): Vehicle =
        new Bicycle(currentSpeedMetersPerHour + mh)

      override def decreaseSpeed(mh: Int): Vehicle =
        new Bicycle(currentSpeedMetersPerHour - mh)
    }

    new Bicycle(4).currentSpeedMilesPerHour should be(0.002 +- .005)
  }

  test("""Traits are specifically called that just for mixing in functionality""") {
    trait Log {
      private val _log: ArrayBuffer[String] = ArrayBuffer[String]()

      def log(s: String): Unit = _log += s

      def entries: List[String] = _log.toList
    }

    val o = new Object with Log
    o.log("Sent one statement")
    o.log("Sent two statements")

    //should contain inOrder is ScalaTest, not Scala core

    o.entries should contain inOrder("Sent one statement", "Sent two statements")

    class Foo extends Log {
      def bar: Int = 3
    }

    val f = new Foo
    f.log("Hello")
    f.log("World")

    f.entries should contain inOrder("Hello", "World")
  }

  test(
    """A sealed trait is a trait that will have children,
      |  but it will define all it's children and no one else will have the
      |  ability to extend the number of children any further. All children
      |  must be produced within the same file. This will also create what
      |  is called a union type/sum type if you are familiar with Haskell, Elm, F#,
      |  and other pure functional languages. Let us create Node, Leaf,
      |  and Empty""".stripMargin) {

    val a: Node[Int] = Node(Leaf(1), Node(Leaf(2), Leaf(4)))
    a.left.asInstanceOf[Leaf[_]].value should be(1)
  }

  test(
    """Sealed traits are also a good idea for pattern matching
      |  exhaustiveness. The compiler will be able to recognize the subclasses
      |  of all sealed traits.""".stripMargin) {

    val a: Tree[Int] = Node(Leaf(1), Node(Leaf(2), Leaf(4)))

    val result = a match {
      case Empty => "Empty"
      case Leaf(x) => s"Leaf of value $x"
      case Node(Leaf(x), Leaf(y)) => s"Two leaves: $x, $y"
      case Node(l, r) => s"Node of $l and $r"
    }

    result should be("Node of Leaf(1) and Node(Leaf(2),Leaf(4))")
  }

  test(
    """You can also have sealed abstract classes, which will operate under
      |  the same rules, the subclasses must all be inside the same file,
      |  and the subclasses should be final. Why would you choose
      |  one over the other? You can multiple inherit a trait and mixin traits.
      |  Abstract classes offer stronger "is-a" relationships
      |  A popular sealed abstract class is Option[+T], Some[T], None, let's
      |  take a look at the API and try it out""".stripMargin) {

    val middleName: Option[String] = Some("Diego")
    middleName.getOrElse("No Middle Name") should be("Diego")

    val danMiddleName: Option[String] = None
    danMiddleName.getOrElse("No Middle Name") should be("No Middle Name")
  }



  test(
    """A popular sealed abstract class is Also List[A], ::,
      |  and Nil let's take a look at the API.""".stripMargin) {
    //val myList = new ::(1, Nil)
    val myList = (1 :: Nil)
    myList should be(List(1))
  }

  test(
    """Lab: (If there is time): Research Try or Either,
      |  research either of them in the Scala API,
      |  and put a sample in this test to verify your conclusions.  Notice
      |  that they may either be a sealed abstract class or a sealed trait.
      |  Given your knowledge of this you should be confident in how they work,
      |  even if you haven't used them before.""".stripMargin) {
    def divideXByY(x: Int, y: Int): Either[String, Int] = {
      if (y == 0) Left("Dude, can't divide by 0")
      else Right(x / y)
    }

    println(divideXByY(1,0))
    println(divideXByY(1,1))

    divideXByY(1, 0) match {
      case Left(s) => println("Answer:" + s)
      case Right(i) => print("Answer: " + i)
    }

    divideXByY(1, 0) should be(Left("Dude, can't divide by 0"))

    //////////////////////////////

//    def readTextFile(filename: String): Try[List[String]] = {
//      Try(Source.fromFile(filename).getLines.toList)
//    }
//
//    val filename = "/etc/passwd"
//
//    readTextFile(filename) match {
//      case Success(lines) => lines.foreach(println)
//      case Failure(f) => println(s"""Got exception: ${f.getMessage}""")
//    }
//
//    readTextFile(filename) should be(Failure(new java.io.FileNotFoundException("Foo.bar (No such file or directory")))


    val numeratort = 30
    val denominator = 10
    val t: Try [Int] = Try.apply(numeratort/denominator)
    val res = t.map(i => i * 2) match {
      case Success(answer) => println("Answer:" + answer)
      case Failure(e) => s"Exception  : ${e.getMessage}"
    }

    res should be ("Answer: 6")


    val et:Either[String,Int] = try {
      Right(numeratort/denominator)
    } catch {
      case e:Throwable => Left(e.getMessage)
    }

    val result2 = et.map(i=>i*8) match {
      case Right (answer ) => s"Answer: $answer"
      case Left(e) => s"Exception: $e.get"
    }



  }
}
