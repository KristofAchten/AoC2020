import challenges.Puzzle
import challenges.day1.D1ExpenseReport
import challenges.day2.D2Passwords
import challenges.day3.D3Slopes


private val puzzles = mutableListOf<Puzzle>(
    D1ExpenseReport(), D2Passwords(), D3Slopes()
)

fun main() {
    for (p in puzzles) {
        p.solve();
    }
}