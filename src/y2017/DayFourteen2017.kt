package y2017

import java.io.File

object DayFourteen2017 {

    fun solution() {
        val file = File("resources/2017/knothash.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0

        val matrix = Array(128) { BooleanArray(128) }

        for (i in 0..127) {
            val hash = DayTen2017.findKnotHash("$text-$i")
            var binary = ""
            for (char in hash) {
                binary += Integer.toBinaryString(Integer.parseInt(char.toString(), 16)).padStart(4, '0')
            }
            binary.forEachIndexed { index, c -> matrix[i][index] = c == '1' }
        }
        total = matrix.countRegions()

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private val directions = listOf(
        0 to 1,
        0 to -1,
        1 to 0,
        -1 to 0,
    )

    private fun Array<BooleanArray>.countRegions(): Int {
        val rows = this.size
        val cols = this[0].size

        var count = 0

        val visited = mutableSetOf<Pair<Int, Int>>()

        fun dfs(location: Pair<Int, Int>) {
            val stack = mutableListOf<Pair<Int, Int>>()
            stack.add(location)
            visited.add(location)

            while (stack.isNotEmpty()) {
                val current = stack.removeLast()
                for (dir in directions) {
                    val new = current.first + dir.first to current.second + dir.second
                    if (new.first in 0..<rows && new.second in 0..<cols && new !in visited && this[new.first][new.second]) {
                        stack.add(new)
                        visited.add(new)
                    }
                }
            }
        }

        for (i in 0 until rows) {
            for (j in 0 until cols) {
                if (this[i][j] && !visited.contains(i to j)) {
                    count++
                    dfs(i to j)
                }
            }
        }

        return count
    }
}