package com.eleks.weather

import akka.actor.{Actor, Props}

object WeatherManager {
    val addId = "f9db562b9e1f90b5b2999a9fa0f5a402"
    val url = "http://api.openweathermap.org/data/2.5/weather"
    val units = "metric"

    def props(city : String) : Props = Props(new WeatherManager(city))

    final case class UpdateTemperature(requestId : Long)
}

class WeatherManager(city : String) extends Actor {
    import WeatherManager._

    override def receive: Receive = {
        case UpdateTemperature(id) =>
            val currentTemperature = getTemperature()
            sender() ! CityWeather.RecordTemperature(id, currentTemperature)

        //case CityWeather.TemperatureRecorded =>
            //Do smth
    }

    def getTemperature(): Double = {
        val cityUrl = s"${url}?q=${city}&units=${units}&APPID=${addId}"
        val json = io.Source.fromURL(cityUrl).mkString.trim

        val pattern = """.*\"temp\":([\d.]+).*""".r
        val pattern(temperature) = json
        temperature.toDouble
    }
}
