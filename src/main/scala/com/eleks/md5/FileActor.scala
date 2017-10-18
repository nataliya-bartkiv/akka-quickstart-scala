package com.eleks.md5

import java.io._

import akka.actor.{Actor, Props}

object FileActor {
    def props(filename : String, chunkSize : Int) : Props = Props(new FileActor(filename, chunkSize))

    final case object ReadNextChunk
    final case class RespondNextChunk(chunk : Array[Byte])
    final case object RespondEndOfFile
}

class FileActor(filename : String, chunkSize : Int) extends Actor {

    import FileActor._

    val file: File = new File(filename)
    val fileStream: FileInputStream = new FileInputStream(filename)
    val bufferedStream: BufferedInputStream = new BufferedInputStream(fileStream)
    var bytesRead: Long = 0
    val bytesToRead: Long = file.length().toLong


    override def receive: Receive = {
        case ReadNextChunk =>
            val chunk = readChunk()
            if(chunk.isEmpty) {
                sender() ! RespondEndOfFile
                context.stop(self)
            } else {
                sender() ! RespondNextChunk(chunk.get)
            }
    }

    def readChunk() : Option[Array[Byte]] = {
        var currentChunkSize: Int = chunkSize
        val bytesLeft: Long = bytesToRead - bytesRead
        bytesLeft match {
            case bytes if bytes <= 0 =>
                return None
            case bytes if bytes < chunkSize =>
                currentChunkSize = bytes.toInt
            case _ =>
        }
        val buffer = new Array[Byte](currentChunkSize)
        bufferedStream.read(buffer, 0, currentChunkSize)
        bytesRead += currentChunkSize
        Some(buffer)
    }
}