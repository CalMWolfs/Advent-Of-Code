package y2017

import java.io.File

object DayOne2017 {

    fun solution() {
        val file = File("resources/2017/sequence.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0
        val size = text.length
        val half = size / 2

        for ((i, char) in text.withIndex()) {
            val otherIndex = (i + size + half) % size
            if (char == text[otherIndex]) {
                total += char.digitToInt()
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

}