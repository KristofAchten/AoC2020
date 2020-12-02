package challenges

import java.io.File
import java.util.stream.Collectors

abstract class Puzzle {
    val inputLocation = "input.txt"

    fun getInput(day: Int): String {
        val path = "src/challenges/day$day/$inputLocation"
        return File(path)
            .bufferedReader()
            .readLines()
            .stream()
            .collect(Collectors.joining("\n"))
    }

    open fun solve() = println("Solutions: part 1 -> ${part1()}, part 2 -> ${part2()}.")

    abstract fun part1() : String
    abstract fun part2() : String

}