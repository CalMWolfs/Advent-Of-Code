package y2017

import java.io.File

object DayThirteen2017 {

    fun solution() {
        val file = File("resources/2017/recordings.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val recordings = mutableListOf<Recording>()

        lines.forEach { recordings.add(Recording(it)) }

        var i = 0
        while (true) {
            i++
            var passed = true
            for (recording in recordings) {
                var currentSpot = (i + recording.depth) % (recording.range * 2 - 2)
                if (currentSpot > recording.range) currentSpot -= recording.range
                if (currentSpot == 0) {
                    passed = false
                    break
                }
            }
            if (!passed) continue
            total = i
            break
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class Recording(private val string: String) {
        val depth: Int
        val range: Int

        init {
            val split = string.split(": ")
            depth = split.first().toInt()
            range = split.last().toInt()
        }
    }

}