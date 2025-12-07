package y2016

import java.io.File

object DayNine2016 {

    fun solution() {
        val file = File("resources/2016/compressed.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0L

        total += recurseSubstringAmount(text)

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private fun recurseSubstringAmount(subs: String): Long {
        var total = 0L
        var endValue = 0
        for ((i, char) in subs.withIndex()) {
            if (i < endValue) continue
            if (char == '(') {
                var j = i
                while (subs[j] != ')') {
                    j++
                }
                val (length, amount) = subs.substring(i + 1, j).split("x").map { it.toInt() }
                total += recurseSubstringAmount(subs.substring(j + 1, j + length + 1)) * amount
                endValue = j + 1 + length
            } else total++
        }
        return total
    }
}