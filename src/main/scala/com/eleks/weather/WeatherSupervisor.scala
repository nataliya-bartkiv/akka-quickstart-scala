package com.eleks.weather

import akka.actor.{Actor, ActorLogging, Props}

object WeatherSupervisor {
    def props() : Props = Props(new WeatherSupervisor)
}

class WeatherSupervisor extends Actor with ActorLogging {
    override def preStart() : Unit = {
        log.info("Weather Application started")
    }

    override def postStop(): Unit = {
        log.info("Weather Application stopped")
    }

    override def receive: Receive = Actor.emptyBehavior
}
