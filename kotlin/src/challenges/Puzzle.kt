package challenges

import sun.misc.IOUtils
import java.io.File
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors
import kotlin.properties.Delegates


abstract class Puzzle(day: Int) {

    var rawInput : String by Delegates.notNull()
    var day : Int by Delegates.notNull()

    init {
        val challengeUrl = URL("https://adventofcode.com/2020/day/$day/input")
        val sessionKey = File("kotlin/src/sessionkey.txt").readText()
        val connection: HttpURLConnection = challengeUrl.openConnection() as HttpURLConnection

        connection.setRequestProperty("Cookie", "session=$sessionKey")

        this.rawInput = connection.inputStream.bufferedReader().readLines().stream().collect(Collectors.joining("\n"))
        this.day = day
    }


    open fun solve() = "Solutions day ${day}: part 1 -> `${part1()}`, part 2 -> `${part2()}`."

    abstract fun part1() : String
    abstract fun part2() : String

}