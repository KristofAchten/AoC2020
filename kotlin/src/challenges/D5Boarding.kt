package challenges

import java.lang.IllegalStateException
import java.lang.Integer.parseInt
import kotlin.math.ceil

class D5Boarding : Puzzle(5) {
    val ids = rawInput.split("\n").map { determineSeatID(it) }.toList().sorted()

    override fun part1() = ids.max().toString()
    override fun part2() = (ids.get(ids.indices.find { i -> ids[i] + 1 != ids[i + 1] }!!) + 1).toString()

    fun determineSeatID(seq: String): Int {
        val binSeq = seq.replace("(F|L)".toRegex(), "0").replace("(B|R)".toRegex(), "1")
        return parseInt(binSeq.substring(0, 7), 2) * 8 + parseInt(binSeq.substring(7), 2)
    }
}