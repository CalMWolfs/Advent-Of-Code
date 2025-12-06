package y2015

import java.io.File

object DayThree {

    fun solution() {
        val file = File("resources/2015/deliverydirections.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0

        var currentX = 0
        var currentY = 0

        var roboX = 0
        var roboY = 0

        val locations = mutableSetOf<Pair<Int, Int>>()

        for ((i, char) in text.withIndex()) {
            when (char) {
                '^' -> if (i % 2 == 0) currentY++ else roboY++
                'v' -> if (i % 2 == 0) currentY-- else roboY--
                '>' -> if (i % 2 == 0) currentX++ else roboX++
                '<' -> if (i % 2 == 0) currentX-- else roboX--
            }
            locations.add(currentX to currentY)
            locations.add(roboX to roboY)
        }
        total = locations.size

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}