object Species extends Enumeration {
  val Lion = Value(1, "lion")
  val Cat = Value(2,"cat")
  val Mouse = Value(3, "mouse")
}

class Animal(species: Species.Value)
case class Pet(species: Species.Value, name: String) extends Animal(species)
case class Wild(species: Species.Value, dangerous: Boolean) extends Animal(species)

val fifi = Pet(Species.Cat, "fifi")
val brutus = Wild(Species.Lion, true)
val mickey = Wild(Species.Mouse, false)

def animalName(a: Animal): Option[String] = {
  a match {
    case Pet(_, name) => Some(name)
    case _ => None
  }
}

val x = animalName(fifi)
val y = animalName(brutus)

case class Cage(nr:Int, inhabitant: Option[Animal])

def whoIsInTheCage(cage: Cage): Option[String] = {
  cage.inhabitant.flatMap(animalName)
}

val who = whoIsInTheCage(Cage(1,Some(fifi)))
val noone = whoIsInTheCage(Cage(2, None))
val nonam = whoIsInTheCage(Cage(2, Some(brutus)))

def whoIsInTheCage2(cage: Cage) = for {
  inhab <- cage.inhabitant
  somename <- animalName(inhab)
} yield somename

val xxx = whoIsInTheCage2(Cage(1,Some(fifi)))


