package y2017

import java.io.File

object DaySeven2017 {

    private var total: Int? = null

    fun solution() {
        val file = File("resources/2017/towers.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()

        val towers = mutableListOf<Tower>()

        for (line in lines) {
            towers.add(Tower(line))
        }

        var startingTower: Tower? = null

        for (tower in towers) {
            if (startingTower != null) continue
            if (tower.supporting.isEmpty()) continue
            if (towers.any { it.supporting.contains(tower.name) }) continue
            startingTower = tower
        }

        findWeight(towers, startingTower!!)

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class Tower(private val string: String) {
        var name: String
        var weight: Int
        var supporting: List<String>

        init {
            val split = string.replace(",", "").split(" ")
            name = split.first()
            weight = split[1].replace("(", "").replace(")", "").toInt()
            supporting = if (split.size > 3) {
                split.drop(3)
            } else listOf()
        }
    }

    private fun findWeight(towers: List<Tower>, start: Tower): Int {
        if (start.supporting.isEmpty()) return start.weight
        val weights = mutableListOf<Int>()
        for (support in start.supporting) {
            weights.add(findWeight(towers, towers.find { it.name == support }!!))
        }
        if (total == null && weights.toSet().size != 1) {
            var unique: Int? = null
            var other: Int? = null
            for (weight in weights) {
                if (weights.count { it == weight } == 1) {
                    unique = weight
                } else {
                    other = weight
                }
            }
            val indexOf = weights.indexOf(unique)
            val difference = other!! - unique!!
            total = towers.find { it.name == start.supporting[indexOf] }!!.weight + difference
        }
        return weights.sum() + start.weight
    }
}