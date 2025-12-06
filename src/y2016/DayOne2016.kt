package y2016

import java.io.File
import kotlin.math.abs

object DayOne2016 {

    fun solution() {
        val file = File("resources/2016/directions.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0

        val split = text.split(", ")

        var xChange = 0
        var yChange = 0

        var direction = 0

        val hasBeen = mutableSetOf<Pair<Int, Int>>()

        for (instruction in split) {
            if (instruction.first() == 'R') {
                direction++
            } else if (instruction.first() == 'L') {
                direction--
            }
            direction = (direction + 4) % 4
            val amount = instruction.substring(1).toInt()
            var found = false
            for (a in 0..< amount) {
                when (direction) {
                    0 -> xChange ++
                    1 -> yChange ++
                    2 -> xChange --
                    3 -> yChange --
                }
                if (!hasBeen.add(xChange to yChange)) {
                    found = true
                    break
                }
            }
            if (found) break
        }

        total = abs(xChange) + abs(yChange)

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}