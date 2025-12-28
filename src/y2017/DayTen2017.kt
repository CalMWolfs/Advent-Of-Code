package y2017

import java.io.File

object DayTen2017 {

    fun solution() {
        val file = File("resources/2017/twists.txt")
        val text = file.readText()
        val startTime = System.nanoTime()

        val total = findKnotHash(text)

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun findKnotHash(input: String): String {
        var total = ""

        val twists = input.map { it.code }.toMutableList()
        twists.addAll(listOf(17, 31, 73, 47, 23))

        val listSize = 256

        val indexList = MutableList(listSize) { it }

        var currentIndex = 0
        var skipSize = 0

        for (u in 0..<64) {
            for (twist in twists) {
                for (i in 0..<twist / 2) {
                    val temp = indexList[(currentIndex + i) % listSize]
                    indexList[(currentIndex + i) % listSize] = indexList[(currentIndex + twist - i - 1) % listSize]
                    indexList[(currentIndex + twist - i - 1) % listSize] = temp
                }

                currentIndex += twist + skipSize
                currentIndex %= listSize
                skipSize++
            }
        }

        val sparseHashes = mutableListOf<Int>()

        for (i in 0..<16) {
            val nums = indexList.subList(i * 16, (i + 1) * 16)
            var sum = 0
            for (num in nums) {
                sum = sum xor num
            }
            sparseHashes.add(sum)
        }

        for (sparse in sparseHashes) {
            total += sparse.toHexString().takeLast(2)
        }
        return total
    }
}