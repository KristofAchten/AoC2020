package challenges

import kotlin.math.ceil

class D5Boarding : Puzzle(5) {
    val input = rawInput.split("\n")

    override fun part1(): String {
        return input.map { determineSeatID(it) }.max().toString()
    }

    override fun part2(): String {
        var sortedRes = input.map { determineSeatID(it) }.toList().sorted()
        var curVal = sortedRes.get(0)

        for (i in 0 until sortedRes.size) {
            if (sortedRes.get(i) != curVal++) {
                break
            }
        }
        return (curVal - 1).toString()
    }

    fun determineSeatID(seq: String): Int {
        var row = Pair(0, 127)
        var col = Pair(0, 7)

        for (i in 0 until seq.length) {
            when (seq[i]) {
                'F' -> row = Pair(row.first, row.second - ceil((row.second - row.first) / 2.0).toInt())
                'B' -> row = Pair(row.first + ceil((row.second - row.first) / 2.0).toInt(), row.second)
                'L' -> col = Pair(col.first, col.second - ceil((col.second - col.first) / 2.0).toInt())
                'R' -> col = Pair(col.first + ceil((col.second - col.first) / 2.0).toInt(), col.second)
            }
        }
        return row.first * 8 + col.first
    }
}