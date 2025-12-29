package y2017

import java.io.File

object DayFifteen2017 {

    fun solution() {

        val file = File("resources/2017/generators.txt")
        val text = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        var generatorAValue = text.first().split(" ").last().toLong()
        var generatorBValue = text.last().split(" ").last().toLong()

        val generatorAFactor = 16807
        val generatorBFactor = 48271

        for (i in 0..<5_000_000) {
            generatorAValue *= generatorAFactor
            generatorAValue %= Int.MAX_VALUE

            while (generatorAValue % 4 != 0L) {
                generatorAValue *= generatorAFactor
                generatorAValue %= Int.MAX_VALUE
            }

            generatorBValue *= generatorBFactor
            generatorBValue %= Int.MAX_VALUE

            while (generatorBValue % 8 != 0L) {
                generatorBValue *= generatorBFactor
                generatorBValue %= Int.MAX_VALUE
            }

            val generatorABinary = Integer.toBinaryString(generatorAValue.toInt())
            val generatorBBinary = Integer.toBinaryString(generatorBValue.toInt())
            if (generatorABinary.takeLast(16) == generatorBBinary.takeLast(16)) {
                total++
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}