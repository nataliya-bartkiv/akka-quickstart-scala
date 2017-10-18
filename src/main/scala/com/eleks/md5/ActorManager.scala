package com.eleks.md5

import akka.actor.{Actor, ActorRef, Props}
import com.eleks.md5.ActorManager.StartFileHash

object ActorManager {
    final case class StartFileHash(path : String)
    final case class StartStringHash(inputString : String)
}

class ActorManager extends Actor {
    var fileActor : ActorRef = _
    var hashActor : ActorRef = _
    var fileRead : Boolean = false

    override def receive : Receive = {
        case StartFileHash(path) =>
            fileActor = context.actorOf(FileActor.props(path, 64))
            hashActor = context.actorOf(Props[HashActor])

            fileActor ! FileActor.ReadNextChunk

        case FileActor.RespondNextChunk(chunk) =>
            hashActor ! HashActor.HashNext(chunk)
            //println("Next chunk: " + chunk.map(_.toChar).mkString)
            if (!fileRead) {
                fileActor ! FileActor.ReadNextChunk
            }

        case FileActor.RespondEndOfFile =>
            fileRead = true
            hashActor ! HashActor.RespondHash
            //println("File read.")

        case HashActor.ReadHash(hash) =>
            println(s"Hash: ${hash}")
    }
}
