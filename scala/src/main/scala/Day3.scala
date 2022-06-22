case class PersonSimple(simpleName: String)

case class PersonWithVocation(name: String, vocation: String)

given Conversion[PersonWithVocation, PersonSimple] with
  def apply(p: PersonWithVocation): PersonSimple = PersonSimple(p.name)

@main def day3(): Unit =
  val me = PersonWithVocation("Maciek", "coder")

  // this will perform implicit conversion
  val simple: PersonSimple = me

  // this is a weird fuckery
  // since type 'PersonWithVocation' doesn't have a member called
  // 'simpleName' scala compiler is able to find valid conversions
  val simpleName = me.simpleName

  assert(simple.simpleName == simpleName)
