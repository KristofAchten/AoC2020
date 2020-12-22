package challenges

import java.util.*
import kotlin.properties.Delegates

enum class Player { A, B }
data class GameResult(val player: Player, val score: Long)
class D22Cards : Puzzle(22) {

    var deck1: MutableList<Int> by Delegates.notNull()
    var deck2: MutableList<Int> by Delegates.notNull()

    init {
        val input = rawInput.split("\n\n")

        this.deck1 = input[0].split("\n").drop(1).map { it.toInt() }.toMutableList()
        this.deck2 = input[1].split("\n").drop(1).map { it.toInt() }.toMutableList()
    }

    override fun part1() = playGame(deck1, deck2, false).score.toString()
    override fun part2() = playGame(deck1, deck2, true).score.toString()

    private fun playGame(list1: List<Int>, list2: List<Int>, recursive: Boolean): GameResult {
        var history = mutableSetOf<Int>()
        var deck1 = list1.toMutableList()
        var deck2 = list2.toMutableList()

        while (deck1.isNotEmpty() && deck2.isNotEmpty()) {

            val hash = Objects.hash(deck1, deck2)
            if (recursive && history.contains(hash)) {
                return GameResult(Player.A, calcScore(deck1))
            }
            history.add(hash)

            val c1 = deck1.removeAt(0)
            val c2 = deck2.removeAt(0)

            val winner = if (recursive && deck1.size >= c1 && deck2.size >= c2) {
                playGame(deck1.subList(0, c1).toMutableList(), deck2.subList(0, c2).toMutableList(), true).player
            } else {
                if (c1 > c2) Player.A else Player.B
            }

            when (winner) {
                Player.A -> deck1.addAll(mutableListOf(c1, c2))
                Player.B -> deck2.addAll(mutableListOf(c2, c1))
            }
        }

        if (deck1.isEmpty()) {
            return GameResult(Player.B, calcScore(deck2))
        } else {
            return GameResult(Player.A, calcScore(deck1))
        }
    }

    private fun calcScore(list: MutableList<Int>): Long {
        list.reverse()
        var idx = 1
        return list.fold(0) { acc, i -> acc + i * idx++ }.toLong()
    }
}