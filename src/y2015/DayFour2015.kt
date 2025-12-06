package y2015

import java.security.MessageDigest

object DayFour2015 {

    fun solution() {
        val text = "ckczppom"
        val startTime = System.nanoTime()
        var total = 0

        while (true) {
            if ("$text$total".md5().startsWith("000000")) {
                break
            }

            total++
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.toHexString()
    }
}