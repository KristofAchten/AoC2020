package challenges

data class HexPoint2D(val x: Int, val y: Int) : Point {
    override fun getNeighbours(): List<Point> {
        val neighbours = mutableListOf<Point>()
        for (x in this.x - 1..this.x + 1)
            for (y in this.y - 1..this.y + 1)
                neighbours.add(HexPoint2D(x, y))

        neighbours.removeAll(
                listOf(
                        HexPoint2D(this.x, this.y),
                        HexPoint2D(this.x - 1, this.y + 1),
                        HexPoint2D(this.x + 1, this.y - 1)
                )
        )

        return neighbours
    }
}

class D24HexagonLayout : Puzzle(24) {

    val initialTiles = decorate(rawInput.split("\n"))

    override fun part1() = initialTiles.size.toString()
    override fun part2() = age(initialTiles, 100).size.toString()

    private fun decorate(input: List<String>): Set<HexPoint2D> {
        val ops = input.map { line ->
            var x = 0;
            var y = 0;
            "(e|se|sw|w|nw|ne)".toRegex().findAll(line).forEach { m ->
                when (m.value) {
                    "e", "se" -> x++
                    "w", "nw" -> x--
                }
                when (m.value) {
                    "se", "sw" -> y++
                    "ne", "nw" -> y--
                }
            }
            HexPoint2D(x, y)
        }

        val result = hashSetOf<HexPoint2D>()
        ops.forEach {
            if (result.contains(it)) {
                result.remove(it)
            } else {
                result.add(it)
            }
        }

        return result
    }

    private fun age(initialTiles: Set<HexPoint2D>, turns: Int): Set<HexPoint2D> {
        var curTiles = initialTiles.toMutableSet()

        repeat(turns) {
            curTiles = curTiles
                    .map { it.getNeighbours() }
                    .flatten()
                    .map { it as HexPoint2D }
                    .groupingBy { it }
                    .eachCount()
                    .filter { (it.key in curTiles && it.value == 1) || it.value == 2 }
                    .map { it.key }
                    .toMutableSet()
        }

        return curTiles
    }
}