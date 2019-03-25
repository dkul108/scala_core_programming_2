1 :: Nil
List.apply(1, 2, 3, 4)
List(1, 2, 3, 4)

class Foo(x: Int) {
  def bar(y: Int) = x + y
}

val f = new Foo(20)
f.bar(10)


class Foo2(x: Int) {
  def ::(y: Int) = x + y
}


val f2 = new Foo2(20)
40 :: f2
f2.::(44)


def divideXByY(x: Int, y: Int): Either[String, Int] = {
  if (y == 0) Left("Dude, can't divide by 0")
  else Right(x / y)
}

println(divideXByY(1, 0))
println(divideXByY(1, 1))

divideXByY(1, 0) match {
  case Left(s) => println("Answer:" + s)
  case Right(i) => println("Answer: " + i)
}

divideXByY(1, 1) match {
  case Left(s) => println("Answer:" + s)
  case Right(i) => println("Answer (correct): " + i)
}

List.empty

List.iterate(0, 10)(i => i * 3)

val f3: String => Int = (x: String) => x.length
f3("Hello")

val f4 = (x: String) => x.size
f4("Hello world")

val f5: Int => Int = (x: Int) => x + 5
f5(15)

val f6: Int => Int = _ + 5

f6(20)


import scala.language.postfixOps

val f7: Int => Int = (5 +)
f7(25)


def f8(i: Int): Int => Int = {
  (x: Int) => x + i
}

val x = f8(5)
x.apply(4)


Vector(1, 2, 3).map(x)


val f23: (Int, Int, Int) => Int = (x: Int, y: Int, z: Int) => x + y + z


val z = f23.curried

val z2 = z.apply(2)

val z3 = z2.apply(4)

val z4 = z3.apply(5)

List(1, 2, 3, 4).map(z3)


val tupleFirst: ((String, Int)) => String = (t: (String, Int)) => t._1
val getFirst4Letters = (s: String) => s.substring(0, 4)

val newFunc: ((String, Int)) => String = getFirst4Letters.compose((tupleFirst))


newFunc.apply("Holyoooooo" -> 4)

Stream.from(1, 2)
  .map(x => x * 5)
  .take(4)
  .toVector

Some(10).map(x => x * 40)

val none = None:Option[Int]
none.map(x=>x+10)

Some(10).filter(x=>x>100)

List(1,2,3,4).filter(x=>x%2 == 0)

List(1,2,3,4).map(x=>x*2)
List(1,2,3,4).map(x=>List(-x, x, x*2)).flatten

List(1,2,3,4).flatMap(x=> List(-x, x, x+1))

//Some(10).flatMap(x=>x+11)


val lyrics = List("aa aaaaaaa aa", "bbbbbbb bbb bbb", "cccc cccc")
lyrics.map(s => s.split(""))
lyrics.flatMap(s => s.split(" ")).map(w=>w.toUpperCase()).groupBy(w=>w.head).mapValues(xs => xs.size)








