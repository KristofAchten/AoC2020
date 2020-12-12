package challenges

import kotlin.math.abs

data class MovementInstruction(val op: Char, val weight: Int)
enum class Direction(val xDif: Int, val yDif: Int) {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0)
}

class D12Ferrying : Puzzle(12) {

    val input = rawInput.split("\n").map { MovementInstruction(it[0], it.substring(1).toInt()) }

    override fun part1(): String {
        return getManhattanDistance().toString()
    }

    private fun getManhattanDistance(): Int {
        var curX = 0
        var curY = 0
        var curDir = Direction.EAST

        for (instruction in input) {
            when (instruction.op) {
                'N' -> curY += Direction.NORTH.yDif * instruction.weight
                'S' -> curY += Direction.SOUTH.yDif * instruction.weight
                'E' -> curX += Direction.EAST.xDif * instruction.weight
                'W' -> curX += Direction.WEST.xDif * instruction.weight
                'L' -> curDir = determineDir(curDir, instruction)
                'R' -> curDir = determineDir(curDir, instruction)
                'F' -> {
                    curX += curDir.xDif * instruction.weight
                    curY += curDir.yDif * instruction.weight
                }
            }
        }
        return abs(curX) + abs(curY)
    }

    private fun getManhattanDistanceWaypoint(): Int {
        var wayPointX = 10
        var wayPointY = -1
        var shipX = 0
        var shipY = 0

        for (instruction in input) {
            when (instruction.op) {
                'N' -> wayPointY += Direction.NORTH.yDif * instruction.weight
                'S' -> wayPointY += Direction.SOUTH.yDif * instruction.weight
                'E' -> wayPointX += Direction.EAST.xDif * instruction.weight
                'W' -> wayPointX += Direction.WEST.xDif * instruction.weight
                'L' -> repeat(instruction.weight / 90) {
                    val tmp = wayPointX
                    wayPointX = wayPointY
                    wayPointY = -tmp
                }
                'R' ->  repeat(instruction.weight / 90) {
                    val tmp = wayPointX
                    wayPointX = -wayPointY
                    wayPointY = tmp
                }
                'F' -> {
                    shipX += wayPointX * instruction.weight
                    shipY += wayPointY * instruction.weight
                }
            }
        }
        return abs(shipX) + abs(shipY)
    }

    private fun determineDir(curDir: Direction, instruction: MovementInstruction): Direction {
        val turn = (instruction.weight / 90)

        return if (instruction.op == 'L') {
            Direction.values()[(4 - turn + curDir.ordinal) % 4]
        } else {
            Direction.values()[(curDir.ordinal + turn) % 4]
        }
    }

    override fun part2(): String {
        return getManhattanDistanceWaypoint().toString()
    }
}