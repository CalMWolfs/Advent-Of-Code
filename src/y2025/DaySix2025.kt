package y2025

import java.io.File

object DaySix2025 {
    fun solution() {
        val file = File("resources/2025/trash.txt")
        val rows = file.readLines()
        val startTime = System.nanoTime()
        var total = 0L

        val operatorRow = rows.last().split("\\s+".toRegex())
        val startIndices = mutableListOf<Int>()
        for ((i, char) in rows.last().withIndex()) {
            if (char != ' ') startIndices.add(i)
        }

        val withoutOperator = rows.dropLast(1)

        for ((i, operator) in operatorRow.withIndex()) {
            var count = 0L
            val isAddition = operator == "+"
            if (!isAddition) count = 1L
            val startIndex = startIndices[i]
            val endIndex = if (i < operatorRow.size - 1) startIndices[i + 1] - 1 else withoutOperator.first().length
            for (j in startIndex..<endIndex) {
                val num = parseColumn(withoutOperator, j)
                if (isAddition) count += num else count *= num
            }

            total += count
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private fun parseColumn(rows: List<String>, index: Int): Long {
        var total = 0L
        for (element in rows) {
            val char = element[index]
            if (char.isDigit()) total = total * 10 + char.toString().toInt()
        }
        return total
    }
}