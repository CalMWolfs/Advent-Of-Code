package y2017

import java.io.File

object DaySix2017 {

    fun solution() {
        val file = File("resources/2017/memorybanks.txt")
        val text = file.readText()
        val startTime = System.nanoTime()
        var total = 0

        val currentBanks = text.split("\\s+".toRegex()).map { it.toInt() }.toMutableList()
        val banksAmount = currentBanks.size

        val haveSeen = mutableSetOf<List<Int>>()

        while (true) {
            val largest = currentBanks.max()
            val largestIndex = currentBanks.indexOf(largest)
            currentBanks[largestIndex] = 0
            var currentBank = largestIndex

            for (i in 0..<largest) {
                currentBank = (currentBank + 1 + banksAmount) % banksAmount
                currentBanks[currentBank]++
            }
            total++
            if (!haveSeen.add(currentBanks.toList())) {
                total -= haveSeen.indexOf(currentBanks.toList()) + 1
                break
            }
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }
}