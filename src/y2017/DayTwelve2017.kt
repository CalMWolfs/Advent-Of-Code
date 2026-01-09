package y2017

import java.io.File

object DayTwelve2017 {

    fun solution() {
        val file = File("resources/2017/pipes.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val pipes = mutableListOf<Pipe>()

        lines.forEach { pipes.add(Pipe(it)) }

        val unionSet = mutableListOf<MutableSet<Int>>()

        for (pipe in pipes) {
            for (connection in pipe.connections) {
                val currentPos = unionSet.indexOfFirst { it.contains(pipe.number) }
                val connectionPos = unionSet.indexOfFirst { it.contains(connection) }

                when {
                    currentPos == -1 && connectionPos == -1 -> unionSet.add(mutableSetOf(pipe.number, connection))
                    currentPos == -1 -> unionSet[connectionPos].add(pipe.number)
                    connectionPos == -1 -> unionSet[currentPos].add(connection)
                    currentPos == connectionPos -> {}
                    else -> {
                        unionSet[currentPos].addAll(unionSet[connectionPos])
                        unionSet.removeAt(connectionPos)
                    }
                }
            }
        }
        total = unionSet.size

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class Pipe(private val string: String) {
        val number: Int
        val connections: List<Int>

        init {
            val split = string.split(" <-> ")
            number = split.first().toInt()
            connections = split.last().split(", ").map { it.toInt() }
        }
    }
}