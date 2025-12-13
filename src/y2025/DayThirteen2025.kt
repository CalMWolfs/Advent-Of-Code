package y2025

import java.io.File
import kotlin.math.max

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
                val board = DnaBoard(initialBoard)

                board.solveBoard()

                lineBuffer.clear()
            }
        }

        total = maxSwaps

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

    private var maxSwaps = Int.MIN_VALUE

    private data class DnaBoard(private val initialBoard: List<MutableList<TileColor>>) {
        val start = initialBoard.first()
        val end = initialBoard.last()
        val middleColumns = initialBoard.drop(1).dropLast(1).toMutableList()

        var swapsNeeded: Int
        var swaps: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>

        init {
            val solved = solveBoard()
            swapsNeeded = solved.first
            swaps = solved.second
            if (swapsNeeded == 11) println(initialBoard)
            maxSwaps = max(maxSwaps, swapsNeeded)
        }

        fun solveBoard(allowEnds: Boolean = true): Pair<Int, List<Pair<Pair<Int, Int>, Pair<Int, Int>>>> {
            val midColumnsAmt = if (!allowEnds) MID_COLUMNS_SIZE else MID_COLUMNS_SIZE + 2

            val dp = Array(midColumnsAmt) { IntArray(ROW_PERMUTATIONS.size) { UNREACHABLE } }
            val parent = Array(midColumnsAmt) { IntArray(ROW_PERMUTATIONS.size) { -1 } }

            val cost = Array(midColumnsAmt) { IntArray(ROW_PERMUTATIONS.size) }
            val swapMap = Array(midColumnsAmt) { Array(ROW_PERMUTATIONS.size) { emptyList<Pair<Int, Int>>() } }

            for (c in 0..<midColumnsAmt) {
                for (p in ROW_PERMUTATIONS.indices) {
                    val perm = ROW_PERMUTATIONS[p].map { middleColumns[c][it] }
                    val (cst, sw) = getMinimumColumnSwaps(middleColumns[c], perm)
                    cost[c][p] = cst
                    swapMap[c][p] = sw
                }
            }

            for (p in ROW_PERMUTATIONS.indices) {
                val perm = ROW_PERMUTATIONS[p].map { middleColumns[0][it] }
                if (canColumnsConnect(start, perm)) {
                    dp[0][p] = cost[0][p]
                }
            }

            for (c in 1..<midColumnsAmt) {
                for (p in ROW_PERMUTATIONS.indices) {
                    val cur = ROW_PERMUTATIONS[p].map { middleColumns[c][it] }
                    for (q in ROW_PERMUTATIONS.indices) {
                        if (dp[c - 1][q] == UNREACHABLE) continue
                        val prev = ROW_PERMUTATIONS[q].map { middleColumns[c - 1][it] }
                        if (canColumnsConnect(prev, cur)) {
                            val newCost = dp[c - 1][q] + cost[c][p]
                            if (newCost < dp[c][p]) {
                                dp[c][p] = newCost
                                parent[c][p] = q
                            }
                        }
                    }
                }
            }

            var best = UNREACHABLE
            var last = -1
            for (p in ROW_PERMUTATIONS.indices) {
                val perm = ROW_PERMUTATIONS[p].map { middleColumns.last()[it] }
                if (dp[midColumnsAmt - 1][p] < UNREACHABLE && canColumnsConnect(perm, end)) {
                    if (dp[midColumnsAmt - 1][p] < best) {
                        best = dp[midColumnsAmt - 1][p]
                        last = p
                    }
                }
            }

            val result = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            var col = midColumnsAmt - 1
            var cur = last

            while (col >= 0) {
                for ((a, b) in swapMap[col][cur]) {
                    result += (col + 1 to a) to (col + 1 to b)
                }
                cur = parent[col][cur]
                col--
            }

            return best to result
        }

        companion object {
            private const val MID_COLUMNS_SIZE = 7
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

            private fun getMinimumColumnSwaps(from: List<TileColor>, to: List<TileColor>): Pair<Int, List<Pair<Int, Int>>> {
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