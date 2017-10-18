package com.eleks.md5

import java.security.MessageDigest

import akka.actor.Actor

object HashActor {
    final case class HashNext(chunk : Array[Byte])
    final case object RespondHashedChunk
    final case object RespondHash
    final case class ReadHash(hash : String)
}

class HashActor extends Actor {
    import HashActor._
    val  messageDigest: MessageDigest = MessageDigest.getInstance("MD5")
    var hash : Array[Byte] = _

    override def receive: Receive = {
        case HashNext(chunk) =>
            messageDigest.update(chunk)
            sender() ! RespondHashedChunk

        case RespondHash =>
            hash = messageDigest.digest()

            //To String
            val hexString = new StringBuilder()
            for(i <- hash.indices) {
                if ((0xff & hash(i)) < 0x10)
                    hexString.append("0" + Integer.toHexString(0xFF & hash(i)))
                else
                    hexString.append(Integer.toHexString(0xFF & hash(i)))
            }

            sender() ! ReadHash(hexString.toString())
            context.stop(self)
    }
}
