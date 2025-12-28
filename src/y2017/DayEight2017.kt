package y2017

import java.io.File
import kotlin.math.max

object DayEight2017 {

    fun solution() {
        val file = File("resources/2017/registers.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val registers = mutableListOf<RegisterInstruction>()

        lines.forEach {
            registers.add(RegisterInstruction(it))
        }

        val registerMap = mutableMapOf<String, Int>()

        for (register in registers) {
            val toCheckValue = registerMap[register.toCheck] ?: 0
            val success = when (register.operation) {
                ">" -> toCheckValue > register.toCheckAmount
                "<" -> toCheckValue < register.toCheckAmount
                "<=" -> toCheckValue <= register.toCheckAmount
                ">=" -> toCheckValue >= register.toCheckAmount
                "==" -> toCheckValue == register.toCheckAmount
                "!=" -> toCheckValue != register.toCheckAmount
                else -> error("unknown operation: ${register.operation}")
            }
            if (!success) continue
            if (register.increase) {
                registerMap[register.register] = (registerMap[register.register] ?: 0) + register.amount
            } else {
                registerMap[register.register] = (registerMap[register.register] ?: 0) - register.amount
            }
            total = max(total, registerMap[register.register] ?: 0)

            registerMap[register.register]
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class RegisterInstruction(private val string: String) {
        val register: String
        val increase: Boolean
        val amount: Int
        val toCheck: String
        val operation: String
        val toCheckAmount: Int

        init {
            val split = string.split(" ")
            register = split.first()
            increase = split[1] == "inc"
            amount = split[2].toInt()
            toCheck = split[4]
            operation = split[5]
            toCheckAmount = split.last().toInt()
        }
    }
}