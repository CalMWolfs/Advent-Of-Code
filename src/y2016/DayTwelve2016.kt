package y2016

import java.io.File

object DayTwelve2016 {

    fun solution() {
        val file = File("resources/2016/assembly.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val registers = mutableMapOf<Char, Int>()
        val hasSeen = mutableSetOf<Int>()

        var instructionNumber = 0

        while (instructionNumber < lines.size) {
            val instruction = lines[instructionNumber]
            val split = instruction.split(" ")
            val lastChar = split.last()[0]
            when (split.first()) {
                "cpy" -> {
                    if (split[1][0].isDigit()) {
                        registers[lastChar] = split[1].toInt()
                    } else {
                        registers[lastChar] = registers[split[1][0]]!!
                    }
                }
                "inc" -> registers[lastChar] = (registers[lastChar]!!) + 1
                "dec" -> registers[lastChar] = (registers[lastChar]!!) - 1
                "jnz" -> {
                    val amount = split.last().toInt()
                    if (split[1][0].isDigit()) instructionNumber += amount - 1
                    val currentAmount = registers[split[1][0]] ?: 0
                    if (currentAmount != 0) instructionNumber += amount - 1
                }
            }
            instructionNumber++
            if (hasSeen.add(instructionNumber)) println("$instructionNumber: $instruction, regs: $registers")
        }

        total = registers['a'] ?: -1111111110

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}