package y2025

import java.io.File

object DayFour2025 {

    private var changed = true

    fun solution() {
        val file = File("resources/2025/minesweeper.txt")
        val rows = file.readLines().toMutableList()
        val startTime = System.nanoTime()
        var total = 0

        val adjacentMap: MutableMap<Int, MutableList<Int>> = mutableMapOf()
        val rowLength = rows.first().length

        while (changed) {
            changed = false
            for ((i, row) in rows.withIndex()) {
                var left = 0
                var currentCount = 0

                val currentRow = MutableList(rowLength) { 0 }
                while (left < rowLength - 1) {
                    if (row[left] == '@') currentCount++
                    if (left > 0) currentRow[left - 1] = currentCount
                    if (left > 1) {
                        if (row[left - 2] == '@') currentCount--
                    }
                    left++
                }
                if (row[left] == '@') currentCount++
                currentRow[left - 1] = currentCount
                if (row[left - 2] == '@') currentCount--
                currentRow[left] = currentCount
                adjacentMap[i] = currentRow
            }

            for (i in 0..<rowLength) {
                var currentCount = 0
                for (j in 0..<adjacentMap.size) {
                    currentCount += adjacentMap[j]?.get(i) ?: throw Exception("a")
                    if (j > 0 && currentCount < 5 && checkPosition(rows, j - 1, i)) total++
                    if (j > 1) currentCount -= adjacentMap[j - 2]?.get(i) ?: throw Exception("a")
                }
                if (currentCount < 5 && checkPosition(rows, adjacentMap.size - 1, i)) total++
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")

        for (line in rows) {
            println(line)
        }
    }

    private fun checkPosition(lines: MutableList<String>, row: Int, column: Int): Boolean {
        if (lines[row][column] == '@') {
            val line = lines[row]
            lines[row] = line.replaceRange(column, column + 1, " ")
            changed = true
            return true
        }
        return false
    }
}