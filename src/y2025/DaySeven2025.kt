package y2025

import java.io.File

object DaySeven2025 {

    fun solution() {
        val file = File("resources/2025/manifold.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0L

        val columns = mutableMapOf<Int, Long>()
        columns[lines.first().indexOf('S')] = 1

        for (line in lines.drop(1)) {
            if (!line.contains("^")) continue
            val toCheck = columns.toList()
            columns.clear()
            for ((column, amount) in toCheck) {
                when (line[column]) {
                    '.' -> {
                        columns[column] = (columns[column] ?: 0) + amount
                    }

                    '^' -> {
                        columns[column - 1] = (columns[column - 1] ?: 0) + amount
                        columns[column + 1] = (columns[column + 1] ?: 0) + amount
                    }

                    else -> println("found ${line[column]}")
                }
            }
        }

        total = columns.toList().sumOf { it.second }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}