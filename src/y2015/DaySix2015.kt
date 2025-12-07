package y2015

import java.io.File

object DaySix2015 {

    fun solution() {
        val file = File("resources/2015/lights.txt")
        val instructions = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val lightsArray = MutableList(1000) { MutableList(1000) { 0 } }

        for (instruction in instructions) {
            val split = instruction.split(" ")
            val firstCorner = split[split.size - 3]
            val secondCorner = split.last()
            val (fX, fY) = firstCorner.split(",").map { it.toInt() }
            val (sX, sY) = secondCorner.split(",").map { it.toInt() }

            when {
                instruction.startsWith("turn on") -> {
                    for (i in fX..sX) {
                        for (j in fY..sY) lightsArray[i][j]++
                    }
                }

                instruction.startsWith("turn off") -> {
                    for (i in fX..sX) {
                        for (j in fY..sY) lightsArray[i][j] = (lightsArray[i][j] - 1).coerceAtLeast(0)
                    }
                }

                instruction.startsWith("toggle") -> {
                    for (i in fX..sX) {
                        for (j in fY..sY) lightsArray[i][j] += 2
                    }
                }
            }
        }
        for (lightRow in lightsArray) {
            total += lightRow.sumOf { it }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}