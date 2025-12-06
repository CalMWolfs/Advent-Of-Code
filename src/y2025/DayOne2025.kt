package y2025

import java.io.File

object DayOne2025 {

    fun solution() {
        val rotationsFile = File("resources/2025/rotations.txt")
        val lines = rotationsFile.readLines()
        val start = System.nanoTime()
        var currentNumber = 50
        var currentCount = 0

        lines.forEach { line ->
            val firstChar = line.first()
            var number = line.substring(1).toInt()
            val extraTimes = number / 100
            currentCount += extraTimes
            number -= extraTimes * 100
            if (firstChar == 'L') {
                if (currentNumber != 0 && number >= currentNumber) currentCount++
                currentNumber -= number
            } else {
                if (currentNumber + number >= 100) currentCount++
                currentNumber += number
            }
            currentNumber = (currentNumber + 100) % 100
        }
        val totalNs = System.nanoTime() - start
        println("totalNS: $totalNs")
        println("current: $currentCount")
    }

}