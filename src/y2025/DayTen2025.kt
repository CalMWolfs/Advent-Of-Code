package y2025

import java.io.File
import kotlin.math.min

object DayTen2025 {

    fun solution() {
        val file = File("resources/2025/manual.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        val machines = mutableListOf<Machine>()

        for ((i, line) in lines.withIndex()) {
            val machine = Machine(line)
            total += machine.optimalButtonPresses
            machines.add(machine)
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class Machine(private val input: String) {
        private var indicatorLights: List<Boolean>
        private var joltageRequirements: List<Int>
        private var buttonsIntArray: List<IntArray>

        var optimalButtonPresses: Int

        init {
            val split = input.split(" ")
            indicatorLights = split.first().replace("[", "").replace("]", "").replace(",", "").map { it == '#' }
            val buttons = split.drop(1).dropLast(1).map { fMap ->
                fMap.replace("(", "").replace(")", "").split(",").map { it.toInt() }.toSet()
            }.sortedByDescending { it.size }
            joltageRequirements = split.last().replace("{", "").replace("}", "").split(",").map { it.toInt() }

            buttonsIntArray = buttons.map { it.toIntArray() }

            optimalButtonPresses = Int.MAX_VALUE
            findOptimalButtonPresses()
        }

        private fun findOptimalButtonPresses() {
            val currentLights = MutableList(indicatorLights.size) { false }

            recursivelyApplyButtons(0, currentLights, buttonsIntArray)
        }

        private fun recursivelyApplyButtons(depth: Int, currentLights: MutableList<Boolean>, toTry: List<IntArray>) {
            for (button in toTry) {
                if (depth >= optimalButtonPresses - 1) return
                currentLights.applyButton(button)
                if (currentLights == indicatorLights) {
                    optimalButtonPresses = min(optimalButtonPresses, depth + 1)
                    currentLights.applyButton(button)
                    return
                }
                recursivelyApplyButtons(depth + 1, currentLights, toTry - setOf(button))
                currentLights.applyButton(button)
            }
        }

        private fun MutableList<Boolean>.applyButton(button: IntArray) {
            for (pressed in button) {
                this[pressed] = !this[pressed]
            }
        }
    }
}