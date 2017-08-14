sealed trait Json

final case class JsObject(get: Map[String, Json]) extends Json

final case class JsString(get: String) extends Json

final case class JsNumber(get: Double) extends Json


trait JsonWriter[A] {
  def write(value: A): Json
}

final case class Person(name: String, email: String)

object JsonWriterInstances {
  implicit val stringJsonWriter = new JsonWriter[String] {
    def write(value: String): Json =
      JsString(value)
  }

  implicit val personJsonWriter = new JsonWriter[Person] {
    def write(value: Person): Json =
      JsObject(Map("name" -> JsString(value.name),
        "email" -> JsString(value.email)
      ))
  }
}

object Json {
  def toJson[A:JsonWriter](value: A): Json =
    implicitly[JsonWriter[A]].write(value)
}

object JsonSyntax {
  implicit class JsonWriterOps[A:JsonWriter](value: A) {
    def toJson  = implicitly[JsonWriter[A]].write(value)
  }
}

import JsonWriterInstances._
import JsonSyntax._
Person("Dave", "dave@example.com").toJson


//Exercises

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val IntPrintable = new Printable[Int] {
    override def format(value: Int): String = {
      value.toString
    }
  }

  implicit val StringPrintable = new Printable[String] {
    override def format(value: String): String = {
      value.toString
    }
  }
}

object PrintableSyntax {
  implicit class PrintableOps[A:Printable](value:A) {
    def print = println(implicitly[Printable[A]].format(value));
    def format = implicitly[Printable[A]].format(value)
  }
}

import PrintableInstances._
import PrintableSyntax._

2.format
"hola mundo".format



