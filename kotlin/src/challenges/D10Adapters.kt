package challenges

import kotlin.properties.Delegates

class D10Adapters : Puzzle(10) {
    var input: ArrayList<Long> by Delegates.notNull()
    var listOfDifs: List<Int> by Delegates.notNull()

    init {
        val sortedInputList = rawInput.split("\n").map { it.toLong() }.sorted()

        input = arrayListOf<Long>(0)
        input.addAll(sortedInputList)
        input.add(sortedInputList.max()!! + 3)

        listOfDifs = input.zipWithNext { a, b -> (b - a).toInt() }
    }


    override fun part1() = (listOfDifs.filter { it == 1 }.count() * listOfDifs.filter { it == 3 }.count()).toString()

    override fun part2() = ""
}