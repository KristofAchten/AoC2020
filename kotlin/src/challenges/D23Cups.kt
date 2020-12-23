package challenges

class D23Cups : Puzzle(23) {

    val input = rawInput.split("").filter { it.isNotBlank() }.map { it.toInt() }

    override fun part1(): String {
        // Build linkedlist, but at level idx - 1
        val linkedList = IntArray(input.size) { it + 1 }
        input.forEachIndexed { index, i ->
            linkedList[i - 1] = input[(index + 1) % input.size] - 1
        }

        // Play
        val result = playRound(linkedList, 100)

        var strRes = ""
        var curNum = 0
        while (strRes.length < input.size - 1) {
            strRes += result[curNum] + 1
            curNum = result[curNum]
        }

        return strRes

    }

    override fun part2(): String {
        // Build linkedlist, but at level idx - 1 -- manually make it circular this time
        val linkedList = IntArray(1000000) { it + 1 }
        input.forEachIndexed { index, i ->
            linkedList[i - 1] = input.getOrElse(index + 1) { 10 } - 1
        }
        linkedList[linkedList.lastIndex] = input.first() - 1

        // Play
        val result = playRound(linkedList, 10000000)

        val v1 = result[0].toLong() + 1
        val v2 = result[result[0]].toLong() + 1
        return (v1 * v2).toString()
    }

    private fun playRound(linkedList: IntArray, turns: Int): IntArray {
        var curNum = input.first() - 1
        var size = linkedList.size

        repeat(turns) {
            // Get the three values to be shifted
            val v1 = linkedList[curNum]
            val v2 = linkedList[v1]
            val v3 = linkedList[v2]

            // The new current number is directly behind it
            val newCurNum = linkedList[v3]

            // Find insertion point
            var pointTo = (curNum + size - 1) % size
            while (setOf<Int>(v1, v2, v3).contains(pointTo)) {
                pointTo = (pointTo + size - 1) % size
            }

            // Set pointers
            linkedList[curNum] = linkedList[v3]
            linkedList[v3] = linkedList[pointTo]
            linkedList[pointTo] = v1

            // Update curNum
            curNum = newCurNum
        }
        return linkedList
    }
}