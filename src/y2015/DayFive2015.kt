package y2015

import java.io.File

object DayFive2015 {

    fun solution() {
        val file = File("resources/2015/nicestrings.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        for (line in lines) {
            var repeatsWithGap = false
            var hasDouble = false
            val doubleStrings = mutableSetOf<String>()

            for ((i, char) in line.withIndex()) {
                if (i == 0) continue
                if (!hasDouble) {
                    if (i != 1 && char == line[i - 1] && char == line[i-2]) continue
                    val doubleString = line.substring(i - 1, i + 1)
                    if (!doubleStrings.add(doubleString)) {
                        hasDouble = true
                    }
                }

                if (i == 1) continue
                if (!repeatsWithGap && char == line[i - 2]) repeatsWithGap = true
            }
            if (repeatsWithGap && hasDouble) total++
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}