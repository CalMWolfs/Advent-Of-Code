package y2015

import java.io.File

object DayTen2015 {

    fun solution() {
        val file = File("resources/2015/lookandsay.txt")
        var text = file.readText()
        val startTime = System.nanoTime()

        for (a in 0..<50) {
            var output = ""

            var left = 0
            for (i in text.indices) {
                if (text[i] == text[left]) continue
                output += "${i - left}${text[left]}"
                left = i
            }
            output += "${text.length - left}${text[left]}"

            text = output
            println("$a ${text.length}")
        }
        val total: Int = text.length

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}