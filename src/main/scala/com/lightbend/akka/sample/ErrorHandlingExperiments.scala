package com.lightbend.akka.sample

import akka.actor.{Actor, ActorSystem, Props}

object ErrorHandlingExperiments extends App {
    val actorSystem = ActorSystem("ErrorHandling")
    val parent = actorSystem.actorOf(Props[Supervisor], "parent")
    parent ! "fail"
}

class Supervisor extends Actor {
    //After a failure  the supervised actor is stopped and immediately restarted
    val child = context.actorOf(Props[Supervised], "child")

    override def receive: Receive = {
        case "fail" => child ! "fail"
    }
}

class Supervised extends Actor {
    override def preStart(): Unit = {
        println("Child started")
    }

    override def postStop() : Unit = {
        println("Child stops")
    }

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
        println(s"Child recovers from the failure: ${reason.getMessage}")
    }

    override def receive: Receive = {
        case "fail" =>

            println("Child fails :c")
            throw new Exception("I'm dead")
    }
}