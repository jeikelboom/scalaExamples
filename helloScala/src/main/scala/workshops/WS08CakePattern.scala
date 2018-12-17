package workshops

object WS08CakePattern {

  case class Animal(name: String, weight: Int )

  trait AnimalRepoComponent {
    val animalRepository = new AnimalRepository()
    class AnimalRepository {
      val animals = Map("fifi" -> Animal("fifi", 3))
      def all() = animals.toList.map(x => x._2)
      def findByNam(name: String) = animals.get(name)
    }


  }

  trait AnimalServiceComponent { this: AnimalRepoComponent =>
    val animalRepository : AnimalRepository
    val animalService = new AnimalService
    class AnimalService {
      def printAnimal(name: String): Unit =
        animalRepository.findByNam(name) match {
          case Some(animal) => println(animal)
          case _ => println("unknown animal")
        }
    }
  }

}
object client extends App {
  import workshops.WS08CakePattern._
  object service extends AnimalServiceComponent with AnimalRepoComponent
  service.animalService.printAnimal("fifi")
}
