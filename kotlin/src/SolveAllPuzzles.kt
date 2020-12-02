import challenges.Puzzle
import challenges.day1.D1ExpenseReport
import challenges.day2.D2Passwords


private val puzzles = mutableListOf<Puzzle>(
    D1ExpenseReport(), D2Passwords()
)

fun main() {
    for (p in puzzles) {
        p.solve();
    }
}