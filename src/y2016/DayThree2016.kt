package y2016

import java.io.File

object DayThree2016 {

    fun solution() {
        val file = File("resources/2016/triangles.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val linesMap = lines.map { line ->
            line.trim().split("\\s+".toRegex()).map { it.toInt() }
        }

        for (i in linesMap.indices) {
            val by3 = i % 3

            when (by3) {
                0 -> if (Triangle(linesMap[i][0], linesMap[i + 1][0], linesMap[i + 2][0]).valid) total++
                1 -> if (Triangle(linesMap[i - 1][1], linesMap[i][1], linesMap[i + 1][1]).valid) total++
                2 -> if (Triangle(linesMap[i - 2][2], linesMap[i - 1][2], linesMap[i][2]).valid) total++
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class Triangle(val s1: Int, val s2: Int, val s3: Int) {
        val sorted = listOf(s1, s2, s3).sorted()

        val valid = sorted.first() + sorted[1] > sorted[2]
    }
}