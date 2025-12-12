package y2025

import java.io.File

object DayEleven2025 {

    fun solution() {
        val file = File("resources/2025/reactor.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val toProcess = mutableMapOf<String, MutableList<String>>()

        for (line in lines) {
            val split = line.split(": ")
            val left = split.first()
            val right = split.last().split(" ").toMutableList()

            toProcess[left] = right
        }

        val queue = mutableListOf<MutableSet<String>>()
        queue.add(mutableSetOf("you"))
        while (queue.isNotEmpty()) {
            val currentPath = queue.removeLast()
            val current = currentPath.last()
            val output = toProcess[current] ?: error("not in list: $current")
            for (o in output) {
                if (o == "out") {
                    total++
                } else {
                    val newList = currentPath + setOf(o)
                    queue.add(newList.toMutableSet())
                }
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}