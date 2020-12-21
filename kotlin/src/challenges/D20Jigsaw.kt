package challenges

import kotlin.properties.Delegates


enum class Position() { TOP, RIGHT, BOTTOM, LEFT }
data class Tile(val id: Int, var part: MutableList<MutableList<String>>) {

    var adjacentTop: Tile? = null
    var adjacentBottom: Tile? = null
    var adjacentLeft: Tile? = null
    var adjacentRight: Tile? = null

    fun getBordersInCurrentOrientation(): List<List<String>> {
        val borders = mutableListOf<List<String>>()

        // Specific order to facilitate rotation:
        //  ----->
        //  ^    |
        //  |    |
        //  |    v
        //  <-----
        borders.add(part.first().toList())
        borders.add(part.map { it.last() })
        borders.add(part.last().toList().reversed())
        borders.add(part.map { it.first() }.reversed())

        return borders
    }

    fun flipXaxis() {
        part = part.map { it.reversed().toMutableList() }.toMutableList()
    }

    fun flipYaxis() {
        part = part.reversed().toMutableList()
    }

    fun rotate() {
        for (i in 0 until 5) {
            for (j in i until 9 - i) {
                val temp = part[i][j]
                part[i][j] = part.get(9 - j).get(i)
                part[9 - j][i] = part[9 - i][9 - j]
                part[9 - i][9 - j] = part[j][9 - i]
                part[j][9 - i] = temp
            }
        }
    }

    fun setAdjacentTile(pos: Position, tile: Tile) {
        when (pos) {
            Position.TOP -> adjacentTop = tile
            Position.BOTTOM -> adjacentBottom = tile
            Position.LEFT -> adjacentLeft = tile
            Position.RIGHT -> adjacentRight = tile
        }
    }

    fun getAdjacentTile(pos: Position): Tile? {
        when (pos) {
            Position.TOP -> return adjacentTop
            Position.BOTTOM -> return adjacentBottom
            Position.LEFT -> return adjacentLeft
            Position.RIGHT -> return adjacentRight
        }
    }

    fun getPartWithoutBorders(): List<List<String>> {
        val res = part.map { it.toMutableList() }.toMutableList()

        res.removeAt(0)
        res.removeAt(res.size - 1)
        res.forEach {
            it.removeAt(0)
            it.removeAt(it.size - 1)
        }

        return res
    }
}

class D20Jigsaw : Puzzle(20) {

    val tiles = mutableListOf<Tile>()
    var imageTiles: List<List<Tile>> by Delegates.notNull()

    init {
        val parts = rawInput.split("\n\n")

        parts.forEach {
            val idAndGrid = it.split(":\n").filter { it.isNotBlank() }
            tiles.add(Tile(idAndGrid[0].substring(5).toInt(), AoCTools.create2DMutableMatrix(
                    idAndGrid[1].split("\n").filter { it.isNotBlank() })))
        }

        this.imageTiles = buildImage()
    }

    override fun part1() = (
            imageTiles.first().first().id.toLong()
                    * imageTiles.first().last().id.toLong()
                    * imageTiles.last().first().id.toLong()
                    * imageTiles.last().last().id.toLong())
            .toString()

    override fun part2(): String = determineWaterRoughness(Tile(-1, stitchImageTogether())).toString()

    private fun buildImage(): List<List<Tile>> {
        val tilesMap = createTilesMap()

        val todo = tiles.toMutableList()
        val toHandle = mutableListOf<Tile>(tiles.first())

        while (!toHandle.isEmpty()) {

            val current = toHandle.removeAt(0)
            val borders = current.getBordersInCurrentOrientation()

            todo.filter { it != current }.forEach { tile ->
                val bordersToCheck = tile.getBordersInCurrentOrientation()

                bordersToCheck.forEach { border ->
                    var process = false
                    var invert = false

                    var oldIdx: Int? = null
                    var idx: Int? = null
                    if (border in borders) {
                        oldIdx = bordersToCheck.indexOf(border)
                        idx = borders.indexOf(border)

                        process = true
                        invert = true
                    } else if (border.reversed() in borders) {
                        oldIdx = bordersToCheck.indexOf(border)
                        idx = borders.indexOf(border.reversed())

                        process = true
                    }

                    if (process) {
                        if (current.getAdjacentTile(Position.values()[idx!!]) == null) {

                            // Rotate as many times as is necessary
                            val inv = (oldIdx!! + 2) % 4
                            for (i in 0..(idx - inv + 4) % 4 - 1) {
                                tile.rotate()
                            }

                            // Invert, cleverly use orientation of borders
                            if (invert) {
                                if (idx == Position.TOP.ordinal || idx == Position.BOTTOM.ordinal) {
                                    tile.flipXaxis()
                                } else {
                                    tile.flipYaxis()
                                }
                            }

                            // Update tiles in both directions
                            tile.setAdjacentTile(Position.values()[(idx + 2) % 4], current)
                            current.setAdjacentTile(Position.values()[idx], tile)

                            // Handle newly updated tile
                            toHandle.add(tile)
                        }
                    }
                }
            }
            todo.remove(current)
        }


        val finalImage = mutableListOf<MutableList<Tile>>()

        // Find top left corner and start building from it
        var curYTile = tilesMap.values.find {
            it.adjacentTop == null && it.adjacentLeft == null
        }

        var curXTile = curYTile
        for (j in 0..11) {
            val row = mutableListOf<Tile>()
            for (i in 0..11) {
                row.add(curXTile!!)
                curXTile = curXTile.getAdjacentTile(Position.RIGHT)
            }
            finalImage.add(row)
            curYTile = curYTile!!.getAdjacentTile(Position.BOTTOM)
            curXTile = curYTile
        }

        return finalImage
    }

    private fun createTilesMap() = tiles.map { Pair(it.id, it) }.toMap()

    private fun stitchImageTogether(): MutableList<MutableList<String>> {
        var image = mutableListOf<MutableList<String>>()
        imageTiles.forEach {
            for (l in 0..7) {
                var line = mutableListOf<String>()
                for (j in 0..it.size - 1) {
                    line.addAll(it[j].getPartWithoutBorders()[l])
                }
                image.add(line)
            }
        }
        return image
    }

    private fun determineWaterRoughness(image: Tile) = image.part.map { it.count { it == "#" } }.sum() - countSeaMonsters(image) * 15

    private fun countSeaMonsters(image: Tile): Int {
        var cnt = 0
        for (y in 0..image.part.size - 2) {
            for (x in 0..image.part.size - 19) {
                if (image.part[x + 18][y] == "#" && image.part[x][y + 1] == "#" && image.part[x + 5][y + 1] == "#"
                        && image.part[x + 6][y + 1] == "#" && image.part[x + 11][y + 1] == "#" && image.part[x + 12][y + 1] == "#"
                        && image.part[x + 17][y + 1] == "#" && image.part[x + 18][y + 1] == "#" && image.part[x + 19][y + 1] == "#"
                        && image.part[x + 1][y + 2] == "#" && image.part[x + 4][y + 2] == "#" && image.part[x + 7][y + 2] == "#"
                        && image.part[x + 10][y + 2] == "#" && image.part[x + 13][y + 2] == "#" && image.part[x + 16][y + 2] == "#") {
                    cnt++
                }
            }
        }

        return cnt
    }
}