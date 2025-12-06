package y2016

import java.io.File
import kotlin.math.abs

object DayTwo2016 {

    fun solution() {
        val file = File("resources/2016/keypad.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = ""

        var xPos = -2
        var yPos = 0
        for (line in lines) {
            for (instruction in line) {
                when {
                    instruction == 'U' && (abs(xPos) + abs(yPos - 1) < 3) -> yPos = (yPos - 1).coerceAtLeast(-2)
                    instruction == 'L' && (abs(xPos - 1) + abs(yPos) < 3) -> xPos = (xPos - 1).coerceAtLeast(-2)
                    instruction == 'D' && (abs(xPos) + abs(yPos + 1) < 3) -> yPos = (yPos + 1).coerceAtMost(2)
                    instruction == 'R' && (abs(xPos + 1) + abs(yPos) < 3) -> xPos = (xPos + 1).coerceAtMost(2)
                }
            }

            val code = when {
                yPos == -2 && xPos == 0 -> "1"
                yPos == -1 && xPos == -1 -> "2"
                yPos == -1 && xPos == 0 -> "3"
                yPos == -1 && xPos == 1 -> "4"
                yPos == 0 && xPos == -2 -> "5"
                yPos == 0 && xPos == -1 -> "6"
                yPos == 0 && xPos == 0 -> "7"
                yPos == 0 && xPos == 1 -> "8"
                yPos == 0 && xPos == 2 -> "9"
                yPos == 1 && xPos == -1 -> "A"
                yPos == 1 && xPos == 0 -> "B"
                yPos == 1 && xPos == 1 -> "C"
                yPos == 2 && xPos == 0 -> "D"
                else -> throw Exception("$xPos, $yPos")
            }

            total += code
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}