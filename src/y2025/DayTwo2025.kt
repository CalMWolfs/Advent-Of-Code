package y2025

import java.io.File

object DayTwo2025 {

    fun solution() {
        val productsFile = File("resources/2025/products.txt")
        val line = productsFile.readText()
        val startTime = System.nanoTime()
        val idPairs = line.split(",")
        var total = 0L

        for (idPair in idPairs) {
            val (start, end) = idPair.split("-").map { it.toLong() }

            for (num in start..end) {
                val asString = num.toString()
                val length = asString.length

                if (asString.findRepeatsOfLen(1)) total += num
                else if (length % 2 == 0 && asString.findRepeatsOfLen(2)) total += num
                else if (length % 3 == 0 && asString.findRepeatsOfLen(3)) total += num
                else if (length % 5 == 0 && asString.findRepeatsOfLen(5)) total += num
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private fun String.findRepeatsOfLen(k: Int): Boolean {
        if (this.length == k) return false
        val repeatedString = this.substring(0, k)
        var left = 0
        while (left != this.length - k) {
            left += k
            if (repeatedString != this.substring(left, left + k)) return false
        }
        return true
    }
}