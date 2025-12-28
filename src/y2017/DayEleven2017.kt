package y2017

import java.io.File
import kotlin.math.abs
import kotlin.math.max

object DayEleven2017 {

    fun solution() {
        val file = File("resources/2017/hexagons.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0

        var northSouth = 0
        var eastWest = 0

        val instructions = text.split(",")
        for (instruction in instructions) {
            when (instruction) {
                "n" -> northSouth += 2
                "s" -> northSouth -= 2
                "ne" -> {
                    northSouth++
                    eastWest++
                }
                "nw" -> {
                    northSouth++
                    eastWest--
                }
                "se" -> {
                    northSouth--
                    eastWest++
                }
                "sw" -> {
                    northSouth--
                    eastWest--
                }
            }
            total = max(total, abs(eastWest) + ((abs(northSouth) - abs(eastWest)) / 2).coerceAtLeast(0))
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}