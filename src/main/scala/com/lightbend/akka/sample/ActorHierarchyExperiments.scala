package com.lightbend.akka.sample

import akka.actor.{Actor, ActorSystem, Props}

import scala.io.StdIn

class ActorRefPrinter extends Actor {
    override def receive : Receive = {
        case "print" =>
            val secondRef = context.actorOf(Props.empty, "second-actor")
            println(s"Second: $secondRef")
    }
}

object ActorHierarchyExperiments extends App {
    val system = ActorSystem("testSystem")

    val firstRef = system.actorOf(Props[ActorRefPrinter], "first-actor")
    println(s"First: $firstRef")
    firstRef ! "print"

    println(">>> Press ENTER to exit <<<")
    try StdIn.readLine()
    finally system.terminate()
}