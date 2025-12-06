package y2016

import java.io.File

object DayFour2016 {

    fun solution() {
        val file = File("resources/2016/roomcodes.txt")
        val lines = file.readLines()
        val startTime = System.nanoTime()
        var total = 0

        for (line in lines) {
            val room = RoomData(line)
            if (room.isValid) total += room.id
        }

        val totalNs = System.nanoTime() - startTime
        println("totalNS: $totalNs")
        println("total: $total")
    }

    private data class RoomData(private val roomName: String) {
        private val name: String
        val id: Int
        private val checkSum: String
        private val characters = mutableMapOf<Char, Int>()
        var isValid: Boolean

        init {
            val match = pattern.matcher(roomName)
            match.matches()
            name = match.group("name")/*.replace("-", "")*/
            id = match.group("id").toInt()
            checkSum = match.group("checkSum")

            for (char in name) {
                characters[char] = (characters[char] ?: 0) + 1
            }

            val sorted = characters.toList().sortedBy { it.first }.sortedByDescending { it.second }
            val topFive = sorted.take(5).map { it.first }.joinToString("")
            isValid = topFive == checkSum

            val rotationAmount = id % 26
            var offset = ""

            for (char in name) {
                if (char == '-') {
                    offset += ' '
                } else {
                    val charOffset = char + rotationAmount
                    offset += if (charOffset > 'z') charOffset - 26 else charOffset
                }
            }
            println("offset is: $offset at id: $id")
        }

        companion object {
            val pattern = "(?<name>.+)-(?<id>\\d+)\\[(?<checkSum>.+)]".toPattern()
        }
    }
}