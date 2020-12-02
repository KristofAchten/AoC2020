import challenges.Puzzle
import challenges.day1.D1ExpenseReport


private val puzzles = mutableListOf<Puzzle>(
    D1ExpenseReport()
)

fun main() {
    for (p in puzzles) {
        p.solve();
    }
}