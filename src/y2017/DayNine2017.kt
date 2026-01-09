package y2017

import java.io.File

object DayNine2017 {

    fun solution() {
        val file = File("resources/2017/stream.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0

        var skippingNext = false
        var inTrash = false

        for (char in text) {
            if (skippingNext) {
                skippingNext = false
                continue
            }
            if (char == '!') {
                skippingNext = true
                continue
            }
            if (inTrash) {
                if (char == '>') {
                    inTrash = false
                } else {
                    total++
                }
            } else {
                if (char == '<') inTrash = true
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}