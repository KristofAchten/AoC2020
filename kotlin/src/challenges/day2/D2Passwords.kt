package challenges.day2

import challenges.Puzzle

class D2Passwords : Puzzle(2) {
    val input = getInput().split('\n')


    override fun part1(): String {
        return findValidPwds(
                fun(min: Int, max: Int, c: Char, s: String): Int {
                    val pattern = Regex(c.toString())
                    val cnt = pattern.findAll(s).count()

                    return if (cnt >= min && cnt <= max) 1 else 0
                }
        );
    }

    override fun part2(): String {
        return findValidPwds(
                fun(min: Int, max: Int, c: Char, s: String): Int {
                    val shiftMin = min - 1
                    val shiftMax = max - 1

                    return if ((shiftMin < s.length && s[shiftMin] == c)
                                    .xor(shiftMax < s.length && s[shiftMax] == c)) 1 else 0
                }
        );
    }


    private fun findValidPwds(compFunc: (Int, Int, Char, String) -> Int): String {
        var validPwds = 0
        for (def in input) {
            val parts = def.replace(":", "").split(" ")
            val minMax = parts[0].split("-")

            val min = minMax[0].toInt()
            val max = minMax[1].toInt()
            val chr = parts[1].single()
            val str = parts[2]

            validPwds += compFunc(min, max, chr, str)
        }

        return validPwds.toString()
    }
}