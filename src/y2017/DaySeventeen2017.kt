package y2017

import java.io.File

object DaySeventeen2017 {

    fun solution() {
        val file = File("resources/2017/spinlock.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0

        val jumpAmount = text.toInt()

        var currentPosition = 0

        for (i in 1..50_000_000) {
            currentPosition += jumpAmount
            currentPosition %= i
            currentPosition++
            if (currentPosition == 1) total = i
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}