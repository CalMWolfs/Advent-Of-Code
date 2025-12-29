package y2017

import java.io.File

object DayEighteen2017 {

    fun solution() {
        val file = File("resources/2017/assembly.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0L

        val register1 = mutableMapOf<String, Long>()
        val register2 = mutableMapOf<String, Long>()

        register1["p"] = 0
        register2["p"] = 1

        fun findValueOf(registers: Map<String, Long>, str: String): Long {
            if (str.last().isDigit()) return str.toLong()
            return registers[str] ?: 0L
        }

        var instructionNumberR1 = 0
        var instructionNumberR2 = 0

        val register1SendQueue = mutableListOf<Long>()
        val register2SendQueue = mutableListOf<Long>()

        var register1Waiting = false
        var register2Waiting = false

        while (!register1Waiting || !register2Waiting) {
            val currentRegister =  when {
                !register1Waiting -> 1
                register2SendQueue.isNotEmpty() -> 1
                else -> 2
            }
            val registers = if (currentRegister == 1) register1 else register2
            val instructionNumber = if (currentRegister == 1) instructionNumberR1 else instructionNumberR2
            val split = lines[instructionNumber].split(" ")

            when (split.first()) {
                "snd" -> {
                    if (currentRegister == 1) {
                        register1SendQueue.add(findValueOf(registers, split.last()))
                        register2Waiting = false
                        total++
                    } else {
                        register2SendQueue.add(findValueOf(registers, split.last()))
                        register1Waiting = false
                    }
                }
                "set" -> registers[split[1]] = findValueOf(registers, split.last())
                "add" -> registers[split[1]] = (registers[split[1]] ?: 0) + findValueOf(registers, split.last())
                "mul" -> registers[split[1]] = (registers[split[1]] ?: 0) * findValueOf(registers, split.last())
                "mod" -> registers[split[1]] = (registers[split[1]] ?: 0) % findValueOf(registers, split.last())
                "rcv" -> {
                    if (currentRegister == 1) {
                        if (register2SendQueue.isEmpty()) {
                            register1Waiting = true
                            continue
                        }
                        registers[split[1]] = register2SendQueue.removeFirst()
                    } else {
                        if (register1SendQueue.isEmpty()) {
                            register2Waiting = true
                            continue
                        }
                        registers[split[1]] = register1SendQueue.removeFirst()
                    }
                }
                "jgz" -> {
                    if (findValueOf(registers, split[1]) != 0L) {
                        if (currentRegister == 1) {
                            instructionNumberR1 += (findValueOf(registers, split.last()) - 1).toInt()
                        } else {
                            instructionNumberR2 += (findValueOf(registers, split.last()) - 1).toInt()
                        }
                    }
                }
            }
            if (currentRegister == 1) {
                instructionNumberR1++
            } else {
                instructionNumberR2++
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}