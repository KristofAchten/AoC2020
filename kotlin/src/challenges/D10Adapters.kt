package challenges

import kotlin.properties.Delegates

class D10Adapters : Puzzle(10) {
    var input: ArrayList<Long> by Delegates.notNull()

    init {
        val sortedInputList = rawInput.split("\n").map { it.toLong() }.sorted()

        input = arrayListOf<Long>(0)
        input.addAll(sortedInputList)
        input.add(sortedInputList.max()!! + 3)
    }


    override fun part1(): String {
        val listOfDifs = input.zipWithNext { a, b -> (b - a).toInt() }
        return (listOfDifs.filter { it == 1 }.count() * listOfDifs.filter { it == 3 }.count()).toString()
    }

    override fun part2() = countPaths().toString()

    fun countPaths(): Long {
        val cache = arrayListOf<Long>(1, 1)

        for (i in 2 until input.size) {
            val toAdd = listOf<Int>(1, 2, 3).map { j ->
                if (i > j - 1 && input[i] - input[i - j] <= 3) cache[i - j] else 0
            }.sum().toLong()

            cache.add(toAdd)
        }

        return cache[input.size - 1]
    }
}