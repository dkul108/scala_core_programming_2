1 :: Nil
List.apply(1,2,3,4)
List(1,2,3,4)

class Foo(x:Int) {
  def bar(y:Int) = x + y
}

val f = new Foo(20)
f.bar(10)


class Foo2(x:Int) {
  def ::(y:Int) = x + y
}


val f2 = new Foo2(20)
40 :: f2
f2.::(44)


def divideXByY(x: Int, y: Int): Either[String, Int] = {
  if (y == 0) Left("Dude, can't divide by 0")
  else Right(x / y)
}

println(divideXByY(1,0))
println(divideXByY(1,1))

divideXByY(1, 0) match {
  case Left(s) => println("Answer:" + s)
  case Right(i) => println("Answer: " + i)
}

divideXByY(1, 1) match {
  case Left(s) => println("Answer:" + s)
  case Right(i) => println("Answer (correct): " + i)
}

List.empty

List.iterate(0, 10) (i => i * 3)
