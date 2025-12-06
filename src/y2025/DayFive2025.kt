package y2025

import java.io.File

object DayFive2025 {

    fun solution() {
        val file = File("resources/2025/ingredients.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0L

        val freshRanges = mutableListOf<LongRange>()

        for (line in lines) {
            if (line.isEmpty()) continue
            val split = line.split("-")
            if (split.size == 2) {
                val start = split.first().toLong()
                val end = split[1].toLong()
                freshRanges.add(LongRange(start, end))
            } /*else {
            val ingredientId = line.toLong()
            if (freshRanges.any { ingredientId in it }) total++
        }*/
        }

        freshRanges.sortBy { it.first }

        var currentHighest = 0L

        for (range in freshRanges) {
            if (range.first > currentHighest) {
                total += range.last - range.first + 1
                currentHighest = range.last
            } else {
                if (range.last < currentHighest) continue
                total += range.last - currentHighest
                currentHighest = range.last
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}