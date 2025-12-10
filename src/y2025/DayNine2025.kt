package y2025

import java.io.File
import java.util.BitSet
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object DayNine2025 {

    fun solution() {
        val file = File("resources/2025/theatre.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0L

        val coordinates = mutableListOf<Pair<Int, Int>>()

        var maximumX = 0
        var maximumY = 0

        for (line in lines) {
            val (x, y) = line.split(",").map { it.toInt() }
            coordinates.add(x to y)
            maximumX = max(maximumX, x)
            maximumY = max(maximumY, y)
        }

        val matrix = MutableList(maximumY + 1) { BitSet(maximumX + 1) }
        println("made sets")

        for ((i, coordinate) in coordinates.withIndex()) {
            if (i % 25 == 0) {
                println("done: $i p1")
            }

            val prev = if (i == 0) coordinates.last() else coordinates[i - 1]
            val (x, y) = coordinate
            val (prevX, prevY) = prev
            matrix[y][x] = true

            when {
                x == prevX -> {
                    val minY = min(y, prevY)
                    val maxY = max(y, prevY)
                    for (j in minY..maxY) {
                        matrix[j][x] = true
                    }
                }
                y == prevY -> {
                    val minX = min(x, prevX)
                    val maxX = max(x, prevX)
                    for (j in minX..maxX) {
                        matrix[y][j] = true
                    }
                }
                else -> error("???? $coordinate, $prev")
            }
        }
        println("done making outlines")

        for ((i, row) in matrix.withIndex()) {
            if (i % 5000 == 0) {
                println("done: $i p2")
            }

            var inside = false
            for (j in 0..<maximumX) {
                when {
                    !inside && !row.get(j) -> {}
                    inside && !row.get(j) -> row[j] = true
                    inside && row.get(j) && !row[j + 1] -> inside = false
                    !inside && row.get(j) && !row[j + 1] -> inside = true
                }
            }
        }
        println("done filling")

        var largestArea = 0L
        var alreadyProcessed = 1
        for ((i, coordinate) in coordinates.withIndex()) {
            if (i % 25 == 0) {
                println("done: $i p3")
            }

            val (x, y) = coordinate
            if (i == lines.size - 1) continue
            for (j in alreadyProcessed..<lines.size) {
                val (secondX, secondY) = coordinates[j]

                val minX = min(x, secondX)
                val minY = min(y, secondY)
                val maxX = max(x, secondX)
                val maxY = max(y, secondY)

                var isValid = true

                val area = (abs(secondX - x) + 1).toLong() * (abs(secondY - y) + 1).toLong()

                if (area < largestArea) continue

                for (ii in minY..maxY) {
                    if (!isValid) break
                    if (!matrix[ii][minX]) isValid = false
                    if (!matrix[ii][maxX]) isValid = false

                }
                for (jj in minX..maxX) {
                    if (!isValid) break
                    if (!matrix[minY][jj]) isValid = false
                    if (!matrix[maxY][jj]) isValid = false
                }

                if (isValid) {
                    val prevArea = largestArea
                    largestArea = max(largestArea, area)
                    if (largestArea != prevArea) {
                        println("new largest area: $area $i, $j")
                    }
                }
            }
            alreadyProcessed++
        }

        total = largestArea

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}