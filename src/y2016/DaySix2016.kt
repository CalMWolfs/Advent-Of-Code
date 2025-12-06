package y2016

import java.io.File

object DaySix2016 {

    fun solution() {
        val file = File("resources/2016/signals.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var output = ""

        val tally = mutableMapOf<Char, Int>()
        val width = lines.first().length

        for (i in 0..<width) {
            tally.clear()
            for (j in lines.indices) {
                tally[lines[j][i]] = (tally[lines[j][i]] ?: 0) + 1
            }
            val sorted = tally.toList().sortedBy { it.second }
            output += sorted.first().first
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $output")
    }
}