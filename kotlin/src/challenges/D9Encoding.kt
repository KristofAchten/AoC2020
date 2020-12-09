package challenges

class D9Encoding : Puzzle(9) {

    val input = rawInput.split("\n").map { it.toLong() }
    val invalidNumber = findInvalidNumber(25)


    override fun part1() = invalidNumber.toString()
    override fun part2() = findContiguousSetAndSumMinMax(invalidNumber).toString()

    private fun findInvalidNumber(preambleSize: Int): Long {
        for (i in preambleSize..input.size) {
            if (!pairExists(input.get(i), input.subList(i - preambleSize, i))) {
                return input.get(i)
            }
        }

        throw IllegalStateException("Could not determine an invalid number!")
    }

    private fun pairExists(value: Long, subList: List<Long>) = subList.any {
        input.contains(value - it)
    }


    private fun findContiguousSetAndSumMinMax(invalidNumber: Long): Long {
        for (i in input.indices) {
            var sum = 0L
            var curIdx = i

            while (curIdx < input.size && sum < invalidNumber) {
                sum += input.get(curIdx++)
            }

            if (i != curIdx - 1 && sum == invalidNumber) {
                val subList = input.subList(i, curIdx - 1)
                return subList.min()!! + subList.max()!!
            }
        }

        throw IllegalStateException("Could not find contiguous set!")
    }
}