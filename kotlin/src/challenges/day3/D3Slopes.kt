package challenges.day3

import challenges.Puzzle

class D3Slopes : Puzzle(3) {
    val input = create2DArray(getInput().split('\n'))
    val tree = "#"

    private fun create2DArray(parts: List<String>): Array<Array<String>> {
        return Array(parts.size) { i ->
            Array(parts.get(i).length) { j ->
                parts.get(i)[j].toString()
            }
        }
    }

    override fun part1(): String {
        return determineTreesForSlope(3, 1).toString()
    }

    override fun part2(): String {
        val multTrees = determineTreesForSlope(1, 1) *
                determineTreesForSlope(3, 1) *
                determineTreesForSlope(5, 1) *
                determineTreesForSlope(7, 1) *
                determineTreesForSlope(1, 2)
        return multTrees.toString()
    }

    fun determineTreesForSlope(difX: Int, difY: Int) : Int {
        var curX = 0
        var curY = 0
        var trees = 0

        while (curY <= (input.size - difY)) {
            if (input[curY][curX] == tree) {
                trees++
            }
            curX = (curX + difX) % input[0].size
            curY += difY
        }

        return trees
    }
}