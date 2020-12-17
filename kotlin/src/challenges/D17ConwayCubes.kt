package challenges

interface Point {
    fun getNeighbours(): List<Point>
}

data class Point3D(val x: Int, val y: Int, val z: Int) : Point {
    override fun getNeighbours(): List<Point> {
        val neighbours = mutableListOf<Point>()
        for (x in this.x - 1..this.x + 1)
            for (y in this.y - 1..this.y + 1)
                for (z in this.z - 1..this.z + 1)
                    neighbours.add(Point3D(x, y, z))
        return neighbours
    }
}

data class Point4D(val x: Int, val y: Int, val z: Int, val w: Int) : Point {
    override fun getNeighbours(): List<Point> {
        val neighbours = mutableListOf<Point>()
        for (x in this.x - 1..this.x + 1)
            for (y in this.y - 1..this.y + 1)
                for (z in this.z - 1..this.z + 1)
                    for (w in this.w - 1..this.w + 1)
                        neighbours.add(Point4D(x, y, z, w))
        return neighbours
    }
}

class D17ConwayCubes : Puzzle(17) {

    val input = AoCTools.create2DArray(rawInput.split("\n"))

    override fun part1() = runSimulation(parseInput3D(), 6).toString()
    override fun part2() = runSimulation(parseInput4D(), 6).toString()

    private fun runSimulation(input: MutableMap<Point, String>, steps: Int): Long {
        var curResult = input
        for (i in 1..steps) {
            curResult = runStep(curResult)
        }
        return curResult.filter { it.value == "#" }.count().toLong()
    }

    private fun runStep(curResult: MutableMap<Point, String>): MutableMap<Point, String> {
        val newResult = mutableMapOf<Point, String>()

        // Init
        curResult.forEach { res ->
            newResult.put(res.key, res.value)
            res.key.getNeighbours().forEach {
                if (!newResult.containsKey(it)) {
                    newResult.put(it, ".")
                }
            }
        }

        // Update
        newResult.forEach { res ->
            val activeNeighbors = res.key.getNeighbours().filter { curResult.containsKey(it) && curResult.get(it) == "#" && it != res.key }
            if (res.value == "#" && activeNeighbors.size != 2 && activeNeighbors.size != 3) {
                newResult.put(res.key, ".")
            } else if (res.value == "." && activeNeighbors.size == 3) {
                newResult.put(res.key, "#")
            }
        }

        return newResult
    }

    private fun parseInput3D(): MutableMap<Point, String> {
        val output = mutableMapOf<Point, String>()

        input.withIndex().forEach { r ->
            r.value.withIndex().forEach { c ->
                output.put(Point3D(c.index, r.index, 0), input.get(r.index).get(c.index))
            }
        }

        return output
    }

    private fun parseInput4D(): MutableMap<Point, String> {
        val output = mutableMapOf<Point, String>()

        input.withIndex().forEach { r ->
            r.value.withIndex().forEach { c ->
                output.put(Point4D(c.index, r.index, 0, 0), input.get(r.index).get(c.index))
            }
        }

        return output
    }
}