package com.eleks.md5

import java.nio.ByteBuffer

object Hasher {
    val wordSize = 8
    val blockSize = 64 // 512 bits
}

class Hasher {
    import Hasher._

    var A : Int = 0x67452301
    var B : Int = 0xEFCDAB89
    var C : Int = 0x98BADCFE
    var D : Int = 0x10325476
    val T : Array[Int] = initTable()

    def hashChunk(chunk : Array[Byte]) : Unit = {

    }

    def alignArray(array : Array[Byte]) : Array[Byte] = {
        val length = array.length
        var padding = 56 - length % blockSize

        if(padding <= 0) {
            padding += blockSize
        }

        val alignedArray : Array[Byte] = new Array[Byte](length + padding)
        Array.copy(array, 0, alignedArray, 0, length)
        alignedArray(length) = 128.toByte //1000 0000
        alignedArray
    }

    def appendLength(array : Array[Byte], length : Long): Array[Byte] = {
        val sizeOfLong = 8 // 64 bits

        val oldLength = array.length
        val arrayWithLength = new Array[Byte](oldLength + sizeOfLong)
        Array.copy(array, 0, arrayWithLength, 0, oldLength)
        val lengthArray = ByteBuffer.allocate(sizeOfLong).putLong(length).array
        Array.copy(lengthArray, 0, arrayWithLength, oldLength, lengthArray.length)

        arrayWithLength
    }

    private def F(B : Int, C : Int, D : Int) : Int = (B & C) | (~B & D)
    private def G(B : Int, C : Int, D : Int) : Int = (B & D) | (C & ~D)
    private def H(B : Int, C : Int, D : Int) : Int = B ^ C ^ D
    private def I(B : Int, C : Int, D : Int) : Int = C ^ (B | ~D)

    private def initTable() : Array[Int] = {
        val table: Array[Int] = new Array[Int](64)
        for(index <- table.indices) {
            val i = index + 1
            table(index) = (4294967296L * Math.abs(Math.sin(i))).toLong.toInt
        }
        table
    }


}
