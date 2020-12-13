package challenges

import kotlin.properties.Delegates

class D13Shuttle : Puzzle(13) {
    var timeStamp: Long by Delegates.notNull()
    var lines: List<String> by Delegates.notNull()

    init {
        val parts: List<String> = rawInput.split("\n")
        this.timeStamp = parts[0].toLong()
        this.lines = parts[1].split(",")
    }


    override fun part1() = determineMinimumWait().toString()
    override fun part2() = determineTimestampOfCorrectSchedule().toString()

    private fun determineMinimumWait(): Long {
        var curMin = Long.MAX_VALUE
        var curBestLine = "not found"
        for (line in lines.filterNot { it == "x" }) {
            val wait = line.toLong() - timeStamp % line.toLong()
            if (wait < curMin) {
                curMin = wait
                curBestLine = line
            }
        }

        return curBestLine.toLong() * curMin
    }

    private fun determineTimestampOfCorrectSchedule(): Long {
        var curMinWait = 0L
        var curInc = 1L

        for (curIdx in lines.indices) {
            if (lines[curIdx] != "x") {
                val line = lines[curIdx].toInt()
                while ((curMinWait + curIdx.toLong()) % line != 0L) {
                    curMinWait += curInc
                }

                curInc *= line
            }
        }

        return curMinWait
    }
}