package challenges

import challenges.AoCTools.Companion.create2DArray
import challenges.AoCTools.Companion.deepCopy

class D11Seats : Puzzle(11) {
    val input = create2DArray(rawInput.split('\n'))

    val emptySeat = "L"
    val occupiedSeat = "#"
    val emptySpace = "."

    override fun part1() = runUntilStabilized(4,
            fun(input: Array<Array<String>>, x: Int, y: Int, c: String) = getNumOfDirectNeighboursWith(input, x, y, c)
    ).toString()

    override fun part2() = runUntilStabilized(5,
            fun(input: Array<Array<String>>, x: Int, y: Int, c: String) = getNumOfIndirectNeighboursWith(input, x, y, c)
    ).toString()

    private fun runUntilStabilized(tolerance: Int, findNeighbours: (Array<Array<String>>, Int, Int, String) -> Int): Int {
        var stable = false
        var curInput = input.deepCopy()
        val newInput = input.deepCopy()

        while (!stable) {
            stable = true

            for (y in newInput.indices) {
                for (x in newInput[y].indices) {
                    if (curInput[y][x] == emptySeat && findNeighbours(curInput, x, y, occupiedSeat) == 0) {
                        newInput[y][x] = occupiedSeat
                        stable = false
                    } else if (curInput[y][x] == occupiedSeat && findNeighbours(curInput, x, y, occupiedSeat) >= tolerance) {
                        newInput[y][x] = emptySeat
                        stable = false
                    }
                }
            }
            curInput = newInput.deepCopy()
        }

        return curInput.map { it.count { it == occupiedSeat } }.sum()
    }

    private fun getNumOfDirectNeighboursWith(curInput: Array<Array<String>>, x: Int, y: Int, type: String): Int {
        val neighbours = arrayListOf<String>()

        arrayListOf<Int>(1, -1).forEach {
            neighbours.add(getDirectNeighbours(curInput, x, y, it, 0))
            neighbours.add(getDirectNeighbours(curInput, x, y, 0, it))
            neighbours.add(getDirectNeighbours(curInput, x, y, it, it))
            neighbours.add(getDirectNeighbours(curInput, x, y, it, -it))
        }

        return neighbours.count { it == type }
    }

    private fun getDirectNeighbours(input: Array<Array<String>>, x: Int, y: Int, difX: Int, difY: Int): String {
        if (x + difX in 0..input[0].size - 1 && y + difY in 0..input.size - 1) {
            return input[y + difY][x + difX]
        }
        return ""
    }

    private fun getNumOfIndirectNeighboursWith(curInput: Array<Array<String>>, x: Int, y: Int, type: String): Int {
        val neighbours = arrayListOf<String>()

        arrayListOf<Int>(1, -1).forEach {
            neighbours.addAll(getIndirectNeighbours(curInput, x, y, it, 0))
            neighbours.addAll(getIndirectNeighbours(curInput, x, y, 0, it))
            neighbours.addAll(getIndirectNeighbours(curInput, x, y, it, it))
            neighbours.addAll(getIndirectNeighbours(curInput, x, y, it, -it))
        }

        return neighbours.count { it == type }
    }

    private fun getIndirectNeighbours(input: Array<Array<String>>, initX: Int, initY: Int, difX: Int, difY: Int): ArrayList<String> {
        val neighbours = arrayListOf<String>()
        var curX = initX
        var curY = initY

        while (curX + difX in 0..input[0].size - 1 && curY + difY in 0..input.size - 1) {
            curX += difX
            curY += difY

            if (input[curY][curX] != emptySpace) {
                neighbours.add(input[curY][curX])
                break
            }
        }

        return neighbours
    }
}