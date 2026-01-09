package y2017

import java.io.File

object DayFive2017 {

    fun solution() {
        val file = File("resources/2017/jumps.txt")
        val text = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        var currentIndex = 0
        val instructions = text.map { it.toInt() }.toMutableList()

        while (true) {
            if (currentIndex >= instructions.size) break
            val currentInstruction = instructions[currentIndex]
            instructions[currentIndex] = if (currentInstruction >= 3) {
                currentInstruction - 1
            } else {
                currentInstruction + 1
            }
            currentIndex += currentInstruction
            total++
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}