package com.eleks.weather

import akka.actor.ActorSystem

import scala.io.StdIn

object WeatherApp {
    def main(args : Array[String]) : Unit = {
        val system = ActorSystem("weather")
        try {
            val supervisor = system.actorOf(WeatherSupervisor.props(), "weather-supervisor")
            StdIn.readLine()
        } finally {
            system.terminate()
        }
    }
}
