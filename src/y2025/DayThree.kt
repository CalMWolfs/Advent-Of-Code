package y2025

import java.io.File
import kotlin.math.pow

object DayThree {

    fun solution() {
        val file = File("resources/2025/batteries.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0L

        for (line in lines) {
            total += calculateBank(line)
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private fun calculateBank(line: String): Long {
        var bankTotal = 0L
        var leftMostIndex = 0
        for (d in 12 downTo 1) {
            var max = 0
            for (i in leftMostIndex..(line.length - d)) {
                val num = line[i].toString().toInt()
                if (max < num) {
                    max = num
                    leftMostIndex = i + 1
                }
            }
            bankTotal += max * 10.0.pow(d - 1).toLong()
        }
        return bankTotal
    }
}