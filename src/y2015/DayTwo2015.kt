package y2015

import java.io.File

object DayTwo2015 {

    fun solution() {
        val file = File("resources/2015/dimensions.txt")
        val rows = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        for (row in rows) {
            val split = row.split("x").map { it.toInt() }
            total += BoxDimension(split.first(), split[1], split[2]).ribbonNeeded
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class BoxDimension(val w: Int, val b: Int, val h: Int) {
        val side1Area: Int = w * b
        val side2Area: Int = w * h
        val side3Area: Int = b * h
        val sortedSides = listOf(w, b, h).sorted()
        val sortedArea = listOf(side1Area, side2Area, side3Area).sorted()

        val area = w * b * h

        val wrappingNeeded: Int = sortedArea.first() * 3 + sortedArea[1] * 2 + sortedArea[2] * 2
        val ribbonNeeded: Int = area + sortedSides.first() * 2 + sortedSides[1] * 2
    }
}