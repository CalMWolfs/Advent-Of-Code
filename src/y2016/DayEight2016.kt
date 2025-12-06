package y2016

import java.io.File
import java.util.Collections

object DayEight2016 {

    fun solution() {
        val file = File("resources/2016/instructions.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val array = MutableList(6) { MutableList(50) { 0 } }
        val height = array.size

        for (instruction in lines) {
            when {
                instruction.startsWith("rect") -> {
                    val (x, y) = instruction.removePrefix("rect ").split("x").map { it.toInt() }
                    for (i in 0..<x) {
                        for (j in 0..<y) {
                            array[j][i] = 1
                        }
                    }
                }

                instruction.startsWith("rotate row") -> {
                    val (y, amount) = instruction.removePrefix("rotate row y=").split(" by ").map { it.toInt() }
                    Collections.rotate(array[y], amount)
                }

                instruction.startsWith("rotate column") -> {
                    val (x, amount) = instruction.removePrefix("rotate column x=").split(" by ").map { it.toInt() }
                    for (b in 0..<amount) {
                        var last = array[height - 1][x]
                        for (i in 0..<height) {
                            val temp = array[i][x]
                            array[i][x] = last
                            last = temp
                        }
                    }
                }
            }
        }

        for (row in array) {
            total += row.count { it == 1 }
            println(row.toString().replace(",", "").replace("1", "#").replace("0", " "))
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}