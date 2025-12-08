package y2025

import java.io.File

object DayEight2025 {

    fun solution() {
        val file = File("resources/2025/playground.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0L

        val circuitPositions = mutableListOf<CircuitPos>()

        for (line in lines) {
            val split = line.split(",").map { it.toLong() }
            circuitPositions.add(CircuitPos(split.first(), split[1], split.last()))
        }

        val closestPairs = mutableMapOf<Pair<Int, Int>, Long>()

        var alreadyProcessed = 1
        for ((i, pos) in circuitPositions.withIndex()) {
            if (i == circuitPositions.size - 1) continue
            for (j in alreadyProcessed..<circuitPositions.size) {
                val dist = pos.distanceTo(circuitPositions[j])
                closestPairs[i to j] = dist
            }
            alreadyProcessed++
        }

        val topCircuits = closestPairs.toList().sortedBy { it.second }

        val circuits = mutableListOf<MutableSet<Int>>()
        var found = false

        for ((pos, _) in topCircuits) {
            if (found) continue
            val (pos1, pos2) = pos
            val pos1Info = circuits.indexOfFirst { it.contains(pos1) }
            val pos2Info = circuits.indexOfFirst { it.contains(pos2) }

            when {
                pos1Info == -1 && pos2Info == -1 -> circuits.add(mutableSetOf(pos1, pos2))
                pos1Info == -1 -> circuits[pos2Info].add(pos1)
                pos2Info == -1 -> circuits[pos1Info].add(pos2)
                pos1Info == pos2Info -> {}
                else -> {
                    circuits[pos1Info].addAll(circuits[pos2Info])
                    circuits.removeAt(pos2Info)
                }
            }

            if (circuits.size == 1 && circuits.first().size == 1000) {
                total = circuitPositions[pos1].x * circuitPositions[pos2].x
                found = true
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class CircuitPos(val x: Long, val y: Long, val z: Long) {
        fun distanceTo(other: CircuitPos): Long {
            val xDist = (this.x - other.x) * (this.x - other.x)
            val yDist = (this.y - other.y) * (this.y - other.y)
            val zDist = (this.z - other.z) * (this.z - other.z)
            return xDist + yDist + zDist
        }
    }
}