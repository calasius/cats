//Parametric polymorphism

def head[A](xs: List[A]) : A = xs(0)

head(1::2:: Nil)

case class Car(make: String)

head(Car("Civic")::Car("Peugeot")::Nil)

//Subtype polymorphism

def plus[A](a1:A, a2:A): A = ???

trait Plusf[A] {
  def plus(a2: A):A
}

def plusBySubType[A <: Plusf[A]](a1:A, a2:A): A = a1.plus(a2)

//Ad -hoc polymorphism

trait CanPlus[A] {
  def plus(a1:A, a2:A): A
}

def plus[A:CanPlus](a1:A, a2:A):A = implicitly[CanPlus[A]].plus(a1,a2)

case class Address(no: Int, street: String, city: String,
                   state: String, zip: String)

trait LabelMaker[T] {
  def toLabel(value: T): String
}

// adapter class
case class AddressLabelMaker() extends LabelMaker[Address] {
  def toLabel(address: Address) = {
    import address._
    "%d %s, %s, %s - %s".format(no, street, city, state, zip)
  }
}

//case classes no necesitan new son para tipos algebraicos y queda mas elegante que no tengan new.
AddressLabelMaker().toLabel(Address(100, "Monroe Street", "Denver", "CO", "80231"))

//Un mejor diseño

//def printLabel[T](t: T)(lm: LabelMaker[T]) = lm.toLabel(t)

object LabelMaker {
  implicit object AddressLabelMaker extends LabelMaker[Address] {
    def toLabel(address: Address): String = {
      import address._
      "%d %s, %s, %s - %s".format(no, street, city, state, zip)
    }
  }

  def printLabel[T: LabelMaker](t: T) = implicitly[LabelMaker[T]].toLabel(t)

  implicit class LabelMakerOps[T:LabelMaker](t:T) {
    def printLabel = implicitly[LabelMaker[T]].toLabel(t)
  }
}



//def printLabel[T](t: T)(implicit lm: LabelMaker[T]) = lm.toLabel(t)
import LabelMaker._
Address(100, "Monroe Street", "Denver", "CO", "80231").printLabel
printLabel(Address(100, "Monroe Street", "Denver", "CO", "80231"))



