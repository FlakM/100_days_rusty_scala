// enum might take parameteres here
enum Car:
  case FastCar(maxSpeed: Int)
  case OldCar(age: Int)

// this will emit a warning since the compiler is able to notice that
// there are cases on which this function will return exception
def testCar(car: Car): Unit = car match {
  case Car.FastCar(maxSpeed) =>
    println(s"maximal speed of fast car is $maxSpeed")
}

@main def enums(): Unit =
  val myCar = Car.FastCar(100)
  val ordinal = myCar.ordinal

  println(s"my fast car ordinal is $ordinal")
