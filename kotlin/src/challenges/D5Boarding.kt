package challenges

class D5Boarding : Puzzle(5) {
    val ids = rawInput.split("\n").map {
        val binSeq = it.replace("(F|L)".toRegex(), "0").replace("(B|R)".toRegex(), "1")
        binSeq.substring(0, 7).toInt(2) * 8 + binSeq.substring(7).toInt(2)
    }.toList().sorted()

    override fun part1() = ids[ids.size - 1].toString()
    override fun part2() = (ids.get(ids.indices.find { i -> ids[i] + 1 != ids[i + 1] }!!) + 1).toString()
}