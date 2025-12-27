package y2017

import java.io.File

object DayTwo2017 {

    fun solution() {
        val file = File("resources/2017/spreadsheet.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        for (line in lines) {
            val nums = line.split("\\s+".toRegex()).map { it.toInt() }

            for (i in nums) {
                for (j in nums) {
                    if (j >= i) continue
                    if (i % j == 0) {
                        total += i / j
                    }
                }
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}