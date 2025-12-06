package y2016

import java.security.MessageDigest

object DayFive2016 {

    fun solution() {
        val text = "uqwqemis"
        val startTime = System.nanoTime()
        val password = MutableList(10) { "" }

        var i = 0
        while (true) {
            val combined = "$text$i"
            val hash = combined.md5()

            if (hash.startsWith("00000")) {
                val posToPutIn = hash[5]
                if (posToPutIn.isDigit()) {
                    val index = posToPutIn.toString().toInt()
                    if (password[index] == "") {
                        password[index] = hash[6].toString()
                        println("added: ${hash[6]} at pos: $index")
                        if (password.all { it != "" }) {
                            val totalNs = System.nanoTime() - startTime
                            println("totalNS: $totalNs")
                            println("total: $password")
                            return
                        }
                    }
                }
            }
            i++
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        val digest = md.digest(this.toByteArray())
        return digest.toHexString()
    }
}