package y2015

import java.io.File

object DayOne2015 {

    fun solution() {
        val file = File("resources/2015/directions.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0

        for ((i, char) in text.withIndex()) {
            if (char == '(') {
                total++
            } else if (char == ')') {
                total--
                if (total == -1) {
                    total = i + 1
                    break
                }
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}