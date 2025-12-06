package y2016

import java.io.File

object DaySeven2016 {

    fun solution() {
        val file = File("resources/2016/ips.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val splitLines = lines.map { it.split("[\\[\\]]".toRegex()) }
        for (line in splitLines) {
            val abas = mutableSetOf<String>()

            // outside
            for ((i, part) in line.withIndex()) {
                if (i % 2 != 0) continue
                for ((j, char) in part.withIndex()) {
                    if (j < 2) continue
                    if (part[j - 2] == char && part[j - 1] != char) {
                        abas.add("${part[j-1]}$char${part[j-1]}")
                    }
                }
            }

            if (abas.isEmpty()) continue
            var found = false

            // inside
            for ((i, part) in line.withIndex()) {
                if (found) continue
                if (i % 2 == 0) continue
                for ((j, char) in part.withIndex()) {
                    if (j < 2) continue
                    if (part[j - 2] == char && part[j - 1] != char) {
                        if ("${char}${part[j-1]}$char" in abas) {
                            total++
                            found = true
                            break
                        }
                    }
                }
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}