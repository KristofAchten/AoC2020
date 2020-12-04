package challenges.day4

import challenges.Puzzle

class D4Passports : Puzzle(4) {
    val input = getInput().split("\n\n")

    val validByr: (String) -> Boolean = { data -> data.toInt() >= 1920 && data.toInt() <= 2002 }
    val validIyr: (String) -> Boolean = { data -> data.toInt() >= 2010 && data.toInt() <= 2020 }
    val validEyr: (String) -> Boolean = { data -> data.toInt() >= 2020 && data.toInt() <= 2030 }
    val validHgt: (String) -> Boolean = { data ->
        if (data.length > 2) {
            val value = data.substring(0, data.length - 2).toInt()
            val unit = data.substring(data.length - 2)

            if (unit == "cm") value >= 150 && value <= 193
            else if (unit == "in") value >= 59 && value <= 76
            else false
        } else false
    }
    val validHcl: (String) -> Boolean = { data ->
        val value = data.substring(1)
        val unit = data[0].toString()
        val regex = "^[a-f0-9]*$".toRegex()

        unit == "#" && value.length == 6 && regex.matches(value)
    }
    val validEcl: (String) -> Boolean = { data -> listOf<String>("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(data) }
    val validPid: (String) -> Boolean = { data -> data.length == 9 }

    val reqFields = hashMapOf<String, (String) -> Boolean>(
            Pair("byr", validByr),
            Pair("iyr", validIyr),
            Pair("eyr", validEyr),
            Pair("hgt", validHgt),
            Pair("hcl", validHcl),
            Pair("ecl", validEcl),
            Pair("pid", validPid)
    )
    val kvSets = hashSetOf<HashMap<String, String>>()

    init {
        for (passport in input) {
            val passports = passport.replace("\n", " ").split(" ")
            val passportFields = hashMapOf<String, String>()

            for (pp in passports) {
                val passportParts = pp.split(":")
                passportFields.put(passportParts[0], passportParts[1])
            }

            kvSets.add(passportFields)
        }
    }

    override fun part1(): String {
        return countValidPassports(false).toString()
    }

    override fun part2(): String {
        return countValidPassports(true).toString()
    }

    private fun countValidPassports(rest: Boolean): Int {
        var validPassports = 0

        for (kvSet in kvSets) {
            validPassports++
            for (field in reqFields) {
                if (!kvSet.containsKey(field.key)) {
                    validPassports--
                    break
                } else if (rest) {
                    if (!kvSet.get(field.key)?.let { field.value(it) }!!) {
                        validPassports--
                        break
                    }
                }
            }
        }

        return validPassports
    }
}