import cats._
import cats.data._
import cats.implicits._

//Eq cats

1===1

(Some(1) : Option[Int]) =!= (Some(2) : Option[Int])

//Order(cats) vs Ord (scala)

1 compare 2

1.0 max 2.0

1 > 2


//Partial Order

1 tryCompare 2

1.0 tryCompare Double.NaN

def lt[A: PartialOrder](a1: A, a2: A): Boolean = a1 <= a2

lt(1,2)

//Cats Show

case class Person(name: String)
case class Car(model: String)


implicit val personShow = Show.show[Person](_.name)

Person("Alice").show

implicit val carShow = Show.fromToString[Car]

Car("cars")

Person("claudio")


//Type classes
//Sealed trait can be extended only in the same file as its declaration. alternativca a enum
sealed trait TrafficLight
object TrafficLight {
  def red: TrafficLight = Red
  def yellow: TrafficLight = Yellow
  def green: TrafficLight = Green
  case object Red extends TrafficLight
  case object Yellow extends TrafficLight
  case object Green extends TrafficLight
}

implicit val trafficLightEq: Eq[TrafficLight] = new Eq[TrafficLight] {
    def eqv(a1: TrafficLight, a2: TrafficLight): Boolean = a1 == a2
  }

TrafficLight.red === TrafficLight.yellow










