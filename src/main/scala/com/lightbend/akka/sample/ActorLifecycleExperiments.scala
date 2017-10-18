package com.lightbend.akka.sample

import akka.actor.{Actor, ActorSystem, Props}

object ActorLifecycleExperiments extends App {
    val system = ActorSystem("LifecycleTest")
    val parent = system.actorOf(Props[Parent], "parent")
    parent ! "stop"
}


class Parent extends Actor {
    override def preStart(): Unit = {
        println("Parent started")
        context.actorOf(Props[Child], "child")
    }

    override def postStop(): Unit = {
        println("Parent stopped")
    }

    override def receive: Receive = {
        case "stop" => context.stop(self)
    }
}

class Child extends Actor {
    override def preStart(): Unit = {
        println("Child started")
    }

    override def postStop(): Unit = {
        println("Child stopped")
    }

    override def receive: Receive = {
        Actor.emptyBehavior
    }
}