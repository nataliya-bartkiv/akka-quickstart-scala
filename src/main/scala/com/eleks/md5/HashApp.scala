package com.eleks.md5

object HashApp {
    def main(args: Array[String]) : Unit = {
        //val system = ActorSystem("Hasher")
        //val manager = system.actorOf(Props[ActorManager])
        //manager ! ActorManager.StartFileHash("/home/natalie/Downloads/users.txt")
        //manager ! ActorManager.StartFileHash("/home/natalie/Downloads/purchases.txt")
        //manager ! ActorManager.StartFileHash("/home/natalie/Downloads/ideaIC-2017.2.4.tar.gz")
        //manager ! ActorManager.StartFileHash("/home/natalie/Downloads/Downloads.tar.gz")

        val hasher = new Hasher()
        val one = "1234567890"
        val two = "12345678901234567890123456789012345678901234567890123456789"
        val three = "1234567890123456789012345678901234567890123456789012345678901234"
        val four = "1234567890123456789012345678901234567890123456789012345678901234" +
                "1234567890123456789012345678901234567890123456789012345678901234" +
                "1234567890123456789012345678901234567890123456789012345678901234" +
                "1234567890123456789012345678901234567890123456789012345678901234" +
                "1234567890123456789012345678901234567890123456789012345678901234" +
                "1234567890123456789012345678901234567890123456789012345678901234"


        //hasher.alignArray(one.getBytes)
        //hasher.appendLength(hasher.alignArray(four.getBytes), four.length)
    }
}
