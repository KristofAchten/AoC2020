package challenges.day1

import challenges.Puzzle

class D1ExpenseReport : Puzzle() {
    val input = getInput(1).split('\n')

    override fun part1(): String = findAndMultiply2Components(2020).toString()

    override fun part2(): String = findAndMultiply3Components(2020).toString()

    fun findAndMultiply2Components(totalVal: Int): Int {
        for (i in input) {
            if (input.contains((totalVal - i.toInt()).toString())) {
                return i.toInt() * (totalVal - i.toInt())
            }
        }
        return 0
    }

    fun findAndMultiply3Components(totalVal: Int): Int {
        for (i in 0..input.size - 1) {
            for (j in i + 1..input.size - 1) {
                for (x in j + 1..input.size - 1) {
                    if (input[i].toInt() + input[j].toInt() + input[x].toInt() == totalVal) {
                        return input[i].toInt() * input[j].toInt() * input[x].toInt()
                    }
                }
            }
        }
        return 0
    }
}