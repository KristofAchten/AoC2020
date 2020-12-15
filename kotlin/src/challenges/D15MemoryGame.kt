package challenges

class D15MemoryGame : Puzzle(15) {

    override fun part1() = runTo(2020).toString()
    override fun part2() = runTo(30000000).toString()

    private fun runTo(finalIndex: Int): Long {
        val seen = mutableMapOf<Long, Long>()
        rawInput.split(",").withIndex().forEach { seen.put(it.value.toLong(), it.index + 1L) }

        var curRun = seen.keys.size.toLong()
        var curNum = seen.keys.last()
        while (curRun < finalIndex) {
            var newNum: Long
            if (seen.containsKey(curNum)) {
                newNum = curRun - seen.get(curNum)!!
            } else {
                newNum = 0
            }
            seen.put(curNum, curRun++)
            curNum = newNum
        }
        return curNum
    }
}