package y2017

import java.io.File

object DayFour2017 {

    fun solution() {
        val file = File("resources/2017/passphrases.txt")
        val text = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        for (line in text) {
            val split = line.split(" ")
            val set = mutableSetOf<String>()

            var duplicate = false
            for (part in split) {
                if (duplicate) continue
                val sortedString = part.toSortedSet().joinToString("")
                if (!set.add(sortedString)) duplicate = true
            }
            if (!duplicate) total++
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}