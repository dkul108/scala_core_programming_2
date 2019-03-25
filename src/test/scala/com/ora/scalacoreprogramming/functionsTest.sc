import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent._

val f = Future.apply{
  Thread.sleep(2000)
  4+10

}

f.map(x=>x*10)

val f2 = Future.apply{
  Thread.sleep(5000)
  4
}

f.map(x=>f2.map(y=> x+y))
f.flatMap(x=>f2.map(y=> x+y))

f.foreach((println))

val res44 = for(x <- f; y <- f2) yield (x+y)

res44 foreach println

for(x <- List(1,2,3,4); y <- List.empty[Int]) yield  (x+y)

val e1:Either[String, Int] = Right(50)
val e2:Either[String, Int] = Right(100)
val e3:Either[String, Int] = Left("Nope, wrong")

for(i <- e1;
    j <- e2;
    k <- e3) yield (i + j + k)

