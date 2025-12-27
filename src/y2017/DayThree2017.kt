package y2017

import java.io.File
import kotlin.math.abs
import kotlin.math.min

object DayThree2017 {

    fun solution() {
        val file = File("resources/2017/spiral.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0
        val target = text.toInt()

        val grid = mutableMapOf<Pair<Int, Int>, Int>()
        grid[0 to 0] = 1

        var x = 0
        var y = 0

        var i = 1
        var currentIndex = 2

        var direction = 3

        while (true) {
            when (direction) {
                0 -> y--
                1 -> x--
                2 -> y++
                3 -> x++
            }
            val prev = (i - 2) * (i - 2)
            when (currentIndex) {
                prev + i - 1 -> direction = 1
                prev + (2 * i) - 2 -> direction = 2
                prev + (3 * i) - 3 -> direction = 3
                i * i + 1 -> {
                    i += 2
                    direction = 0
                }
            }

            val cellTotal = grid.findAdjacentSum(x to y)
            if (cellTotal >= target) {
                total = cellTotal
                break
            }
            grid[x to y] = cellTotal

            currentIndex++
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private fun Map<Pair<Int, Int>, Int>.findAdjacentSum(location: Pair<Int, Int>): Int {
        var sum = 0
        sum += this[location.first - 1 to location.second] ?: 0
        sum += this[location.first + 1 to location.second] ?: 0
        sum += this[location.first - 1 to location.second - 1] ?: 0
        sum += this[location.first - 1 to location.second + 1] ?: 0
        sum += this[location.first + 1 to location.second - 1] ?: 0
        sum += this[location.first + 1 to location.second + 1] ?: 0
        sum += this[location.first to location.second - 1] ?: 0
        sum += this[location.first to location.second + 1] ?: 0
        return sum
    }

}