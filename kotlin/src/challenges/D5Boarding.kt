package challenges

import java.lang.IllegalStateException
import java.lang.Integer.parseInt
import kotlin.math.ceil

class D5Boarding : Puzzle(5) {
    val input = rawInput.split("\n")

    override fun part1() = input.map { determineSeatID(it) }.max().toString()

    override fun part2(): String {
        val res = input.map { determineSeatID(it) }.toList().sorted()
        return (res.get(res.indices.find { i -> res[i] + 1 != res[i + 1] }!!) + 1).toString()
    }

    fun determineSeatID(seq: String): Int {
        val binSeq = seq.replace("F", "0").replace("L", "0").replace("B", "1").replace("R", "1")
        return parseInt(binSeq.substring(0, 7), 2) * 8 + parseInt(binSeq.substring(7), 2)
    }
}