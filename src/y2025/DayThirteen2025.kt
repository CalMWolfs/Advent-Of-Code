package y2025

import java.io.File

object DayThirteen2025 {

    fun solution() {
        val file = File("resources/2025/greenhouse.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val lineBuffer = mutableListOf<List<TileColor>>()

        for (line in lines) {
            if (line.isBlank()) continue
            lineBuffer.add(line.split(" ").map { TileColor.fromIndex(it.toInt()) })
            if (lineBuffer.size == 4) {
                val initialBoard = MutableList(9) { MutableList(4) { TileColor.BLUE } }
                for ((i, row) in lineBuffer.withIndex()) {
                    for ((j, num) in row.withIndex()) {
                        initialBoard[j][i] = num

                    }
                }
                DnaBoard(initialBoard)

                lineBuffer.clear()
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private val colorSet = setOf(1, 2, 3, 4)

    fun generateTests() {
        val file = File("resources/2025/greenhouse.txt")

        file.writeText("")
        for (i in 0..<500) {
            val testOutput = mutableListOf<List<Int>>()
            while (testOutput.size < 9) {
                testOutput.add(colorSet.shuffled())
            }
            for (j in colorSet.indices) {
                val line = StringBuilder()
                for (entry in testOutput) {
                    line.append(entry[j])
                    line.append(" ")
                }
                file.appendText("${line.trim()}\n")
            }
            file.appendText("\n")
        }
    }

    private data class DnaBoard(private val initialBoard: List<MutableList<TileColor>>) {
        var swapsNeeded: Int
        var swaps: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>

        init {
            val solved = solveBoard(false)
            swapsNeeded = solved.first
            swaps = solved.second
            println("without swapping ends: ${solved.first}")
            println("with swapping ends: ${solveBoard(true).first}")
            println()
        }

        private fun solveBoard(allowEnds: Boolean): Pair<Int, List<Pair<Pair<Int, Int>, Pair<Int, Int>>>> {
            val firstMutable = if (allowEnds) 0 else 1
            val lastMutable = if (allowEnds) initialBoard.lastIndex else initialBoard.lastIndex - 1
            val mutableCount = lastMutable - firstMutable + 1

            val dp = Array(mutableCount) { IntArray(ROW_PERMUTATIONS.size) { UNREACHABLE } }
            val parent = Array(mutableCount) { IntArray(ROW_PERMUTATIONS.size) { -1 } }

            val cost = Array(mutableCount) { IntArray(ROW_PERMUTATIONS.size) }
            val swapMap = Array(mutableCount) { Array(ROW_PERMUTATIONS.size) { emptyList<Pair<Int, Int>>() } }

            for (i in 0..<mutableCount) {
                val colIndex = firstMutable + i
                val column = initialBoard[colIndex]

                for (p in ROW_PERMUTATIONS.indices) {
                    val perm = ROW_PERMUTATIONS[p].map { column[it] }
                    val (cst, sw) = getMinimumColumnSwaps(column, perm)
                    cost[i][p] = cst
                    swapMap[i][p] = sw
                }
            }

            for (p in ROW_PERMUTATIONS.indices) {
                val perm = ROW_PERMUTATIONS[p].map { initialBoard[firstMutable][it] }
                if (!allowEnds && !canColumnsConnect(initialBoard[0], perm)) continue
                dp[0][p] = cost[0][p]
            }

            for (i in 1..<mutableCount) {
                for (p in ROW_PERMUTATIONS.indices) {
                    val cur = ROW_PERMUTATIONS[p].map { initialBoard[firstMutable + i][it] }
                    for (q in ROW_PERMUTATIONS.indices) {
                        if (dp[i - 1][q] == UNREACHABLE) continue
                        val prev = ROW_PERMUTATIONS[q].map { initialBoard[firstMutable + i - 1][it] }
                        if (canColumnsConnect(prev, cur)) {
                            val newCost = dp[i - 1][q] + cost[i][p]
                            if (newCost < dp[i][p]) {
                                dp[i][p] = newCost
                                parent[i][p] = q
                            }
                        }
                    }
                }
            }

            var best = UNREACHABLE
            var last = -1

            for (p in ROW_PERMUTATIONS.indices) {
                val perm = ROW_PERMUTATIONS[p].map { initialBoard[lastMutable][it] }
                if (!allowEnds && !canColumnsConnect(perm, initialBoard.last())) continue
                if (dp[mutableCount - 1][p] < best) {
                    best = dp[mutableCount - 1][p]
                    last = p
                }
            }

            val result = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            var i = mutableCount - 1
            var cur = last

            while (i >= 0) {
                val colIndex = firstMutable + i
                for ((a, b) in swapMap[i][cur]) {
                    result += (colIndex to a) to (colIndex to b)
                }
                cur = parent[i][cur]
                i--
            }

            return best to result
        }

        companion object {
            private const val ROWS = 4
            private const val UNREACHABLE = 1_000

            val ROW_PERMUTATIONS: List<List<Int>> by lazy {
                val perms = mutableListOf<List<Int>>()
                generateRowPermutations(perms, mutableListOf(0, 1, 2, 3), 0)
                perms
            }

            private fun generateRowPermutations(perms: MutableList<List<Int>>, a: MutableList<Int>, l: Int) {
                if (l == ROWS) perms += a.toList()
                else for (i in l..<ROWS) {
                    a[l] = a[i].also { a[i] = a[l] }
                    generateRowPermutations(perms, a, l + 1)
                    a[l] = a[i].also { a[i] = a[l] }
                }
            }

            private fun canColumnsConnect(a: List<TileColor>, b: List<TileColor>): Boolean {
                for (r in 0..<ROWS) {
                    val v = a[r]
                    if (b[r] == v) continue
                    if (r > 0 && b[r - 1] == v) continue
                    if (r < ROWS - 1 && b[r + 1] == v) continue
                    return false
                }
                return true
            }

            private fun getMinimumColumnSwaps(
                from: List<TileColor>,
                to: List<TileColor>
            ): Pair<Int, List<Pair<Int, Int>>> {
                val pos = IntArray(ROWS)
                for (i in 0..<ROWS) pos[from.indexOf(to[i])] = i

                val visited = BooleanArray(ROWS)
                val swaps = mutableListOf<Pair<Int, Int>>()
                var cost = 0

                for (i in 0..<ROWS) {
                    if (visited[i]) continue
                    var cur = i
                    val cycle = mutableListOf<Int>()
                    while (!visited[cur]) {
                        visited[cur] = true
                        cycle.add(cur)
                        cur = pos[cur]
                    }
                    if (cycle.size > 1) {
                        cost += cycle.size - 1
                        for (k in 1..<cycle.size) {
                            swaps += cycle[0] to cycle[k]
                        }
                    }
                }
                return cost to swaps
            }
        }
    }

    enum class TileColor {
        RED,
        BLUE,
        GREEN,
        YELLOW,
        ;

        companion object {
            fun fromIndex(index: Int): TileColor {
                return entries[index - 1]
            }
        }
    }
}