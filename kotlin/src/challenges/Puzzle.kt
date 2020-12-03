package challenges

import java.io.File
import java.util.stream.Collectors

abstract class Puzzle(day: Int) {
    val inputLocation = "input.txt"
    val curDay = day

    fun getDay() = curDay

    fun getInput(): String {
        val path = "kotlin/src/challenges/day${getDay()}/$inputLocation"
        return File(path)
            .bufferedReader()
            .readLines()
            .stream()
            .collect(Collectors.joining("\n"))
    }

    open fun solve() = "Solutions day ${getDay()}: part 1 -> `${part1()}`, part 2 -> `${part2()}`."

    abstract fun part1() : String
    abstract fun part2() : String

}