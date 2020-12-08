package challenges

class D6CustomForms : Puzzle(6) {

    val input = rawInput.split("\n\n")

    override fun part1(): String {
        return input.map {
            it.replace("\n", "").toCharArray().toSet().count()
        }.sum().toString()
    }

    override fun part2(): String {
        return input.map { singleInput ->
            singleInput.split("\n")
                    .map { line -> line.toCharArray().toHashSet() }
                    .reduce { acc, curSet -> acc.retainAll(curSet); acc }.size
        }.sum().toString()
    }
}