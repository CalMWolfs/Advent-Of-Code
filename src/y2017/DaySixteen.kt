package y2017

import java.io.File

object DaySixteen {

    fun solution() {
        val file = File("resources/2017/dancemoves.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = ""

        var programs = MutableList(16) { 'a' + it }

        val instructions = text.split(",")

        val previousSequences = mutableListOf<String>()

        for (i in 0..<1_000_000_000) {
            for (instruction in instructions) {
                when (instruction.first()) {
                    's' -> {
                        val amount = instruction.drop(1).toInt()
                        val endSubstring = programs.takeLast(amount)
                        programs = (endSubstring + programs.dropLast(amount)).toMutableList()
                    }

                    'x' -> {
                        val split = instruction.drop(1).split("/")
                        val first = split.first().toInt()
                        val second = split.last().toInt()
                        val temp = programs[first]
                        programs[first] = programs[second]
                        programs[second] = temp
                    }

                    'p' -> {
                        val first = instruction[1]
                        val second = instruction[3]
                        val indexOfFirst = programs.indexOf(first)
                        val indexOfSecond = programs.indexOf(second)
                        programs[indexOfFirst] = second
                        programs[indexOfSecond] = first
                    }
                }
            }
            val sequence = programs.joinToString("")
            if (previousSequences.contains(sequence)) {
                val offset = (1_000_000_000 % previousSequences.size) - 1
                total = previousSequences[offset]
                break
            }
            previousSequences.add(sequence)
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}