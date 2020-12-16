package challenges

import kotlin.properties.Delegates

class D16Tickets : Puzzle(16) {

    val rules = mutableMapOf<String, (Int) -> Boolean>()
    val tickets = mutableListOf<List<Int>>()

    var ownTicket: List<Int> by Delegates.notNull()

    init {
        val parts = rawInput.split("\n\n")

        // Process rules
        parts[0].split("\n").forEach {
            val nameAndRanges = it.split(": ")
            val ranges = nameAndRanges[1].split(" or ")
            val firstRange = ranges[0].split("-").map { it.toInt() }
            val secondRange = ranges[1].split("-").map { it.toInt() }

            val check: (Int) -> Boolean = {
                (it >= firstRange[0] && it <= firstRange[1]) || (it >= secondRange[0] && it <= secondRange[1])
            }

            rules.put(nameAndRanges[0], check)
        }

        // Process own ticket
        this.ownTicket = parts[1].split("\n")[1].split(",").map { it.toInt() }

        // Process other tickets
        val curTickets = parts[2].split("\n")

        for (i in 1..curTickets.size - 1) {
            tickets.add(curTickets[i].split(",").map { it.toInt() })
        }
    }

    override fun part1() = tickets.flatten().filter { !matchesAnyRule(it) }.sum().toString()

    private fun matchesAnyRule(value: Int) = rules.any { it.value(value) }

    override fun part2(): String {
        val possibleCandidates = mutableMapOf<Int, ArrayList<String>>()
        val relevantTickets = tickets.filter { it.all { matchesAnyRule(it) } }

        // Initialize using own ticket
        ownTicket.withIndex().forEach { ticketPartValue ->
            rules.forEach {
                if (it.value(ticketPartValue.value)) {
                    if (possibleCandidates.containsKey(ticketPartValue.index)) {
                        possibleCandidates.get(ticketPartValue.index)!!.add(it.key)
                    } else {
                        possibleCandidates.put(ticketPartValue.index, arrayListOf(it.key))
                    }
                }
            }
        }

        // Reduce using relevant tickets
        relevantTickets.forEach {
            it.withIndex().forEach { curTicket ->
                rules.forEach {
                    if (!it.value(curTicket.value)) {
                        possibleCandidates.get(curTicket.index)!!.remove(it.key)
                    }
                }
            }
        }

        // Further reduce using already determined values
        while (!possibleCandidates.all { it.value.size == 1 }) {
            possibleCandidates.filter { it.value.size == 1 }.forEach { decided ->
                possibleCandidates.filter { it.value.size > 1 }.forEach { it.value.remove(decided.value.first()) }
            }
        }

        return possibleCandidates.filter { it.value.first().contains("departure") }
                .map { ownTicket[it.key].toLong() }
                .reduce { acc, value -> acc * value }
                .toString()
    }
}