package challenges

data class Point2D(val x: Int, val y: Int) : Point {
    override fun getNeighbours(): List<Point> {
        val neighbours = mutableListOf<Point>()
        for (x in this.x - 1..this.x + 1)
            for (y in this.y - 1..this.y + 1)
                neighbours.add(Point2D(x, y))

        neighbours.removeAll(
                listOf(
                        Point2D(this.x, this.y),
                        Point2D(this.x - 1, this.y + 1),
                        Point2D(this.x + 1, this.y - 1)
                )
        )

        return neighbours
    }
}

class D24HexagonLayout : Puzzle(24) {

    val initialTiles = decorate(rawInput.split("\n"))

    override fun part1() = initialTiles.size.toString()
    override fun part2() = age(initialTiles, 100).size.toString()

    private fun decorate(input: List<String>): Set<Point2D> {
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
            Point2D(x, y)
        }

        val result = hashSetOf<Point2D>()
        ops.forEach {
            if (result.contains(it)) {
                result.remove(it)
            } else {
                result.add(it)
            }
        }

        return result
    }

    private fun age(initialTiles: Set<Point2D>, turns: Int): Set<Point2D> {
        var curTiles = initialTiles.toMutableSet()

        repeat(turns) {
            curTiles = curTiles
                    .map { it.getNeighbours() }
                    .flatten()
                    .map { it as Point2D }
                    .groupingBy { it }
                    .eachCount()
                    .filter { (it.key in curTiles && it.value == 1) || it.value == 2 }
                    .map { it.key }
                    .toMutableSet()
        }

        return curTiles
    }
}