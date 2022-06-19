// assume this is a foreign type that we cannot modify
// ie coming from dependency
case class Person(name: String)

// we can do it using extension methods
// https://docs.scala-lang.org/scala3/book/ca-extension-methods.html
extension (p: Person) def hello(): Unit = println(s"hello from ${p.name}")

object PersonExt:
  extension (p: Person) def helloScoped(): Unit = println(s"hello scoped from ${p.name}")

// we can use more powerfull concept: Type ClassDesc
// https://docs.scala-lang.org/scala3/book/ca-type-classes.html
trait Welcoming[T]:
  extension (p: T) def welcome(): Unit

given Welcoming[Person] with
  extension (p: Person) def welcome(): Unit = println(s"welcome from ${p.name}")

// having a type class allows us to abstract over types using type class behaviour:

def veryNiceWelcoming[T: Welcoming](w: T): Unit = 
  print("❤️, ") 
  w.welcome()

// prints:
// hello from Maciek
// welcome from Maciek
@main def day1(): Unit =
  val maciek = Person("Maciek")
  maciek.hello()

  // we can also hide extension methods inside object
  // in order to call `helloScoped` we have to import the object itself too
  import PersonExt._
  maciek.helloScoped()

  maciek.welcome()
  veryNiceWelcoming(maciek)
 

