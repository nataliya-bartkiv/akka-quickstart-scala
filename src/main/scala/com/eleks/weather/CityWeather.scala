package com.eleks.weather

import akka.actor.{Actor, ActorLogging, Props}

object CityWeather {
    def props(name : String) : Props = {
        Props(new CityWeather(name))
    }

    final case class RecordTemperature(requestId: Long, value : Double)
    final case class TemperatureRecorded(requestId: Long)

    final case class ReadTemperature(requestId: Long)
    final case class RespondTemperature(requestId : Long, value : Option[Double])

}

class CityWeather(name : String) extends Actor with ActorLogging {
    import CityWeather._

    var currentTemperature : Option[Double] = None
    override def preStart() : Unit = {
        println(s"${name} Actor started.")
    }

    override def postStop(): Unit = {
        println(s"${name} Actor stopped.")
    }

    override def receive: Receive = {
        case RecordTemperature(id, value) =>
            log.info(s"Recorded temperature reading ${value} with Request#${id}")
            currentTemperature = Some(value)
            sender() ! TemperatureRecorded(id)

        case ReadTemperature(id) =>
            sender() ! RespondTemperature(id, currentTemperature)
    }
}
