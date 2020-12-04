package challenges

import kotlin.properties.Delegates

class D4Passports : Puzzle(4) {
    val input = rawInput.split("\n\n")

    val validByr: (String) -> Boolean = { data -> data.toInt() >= 1920 && data.toInt() <= 2002 }
    val validIyr: (String) -> Boolean = { data -> data.toInt() >= 2010 && data.toInt() <= 2020 }
    val validEyr: (String) -> Boolean = { data -> data.toInt() >= 2020 && data.toInt() <= 2030 }
    val validHcl: (String) -> Boolean = { data -> data[0] == '#' && "^([a-f0-9]{6})*$".toRegex().matches(data.substring(1)) }
    val validEcl: (String) -> Boolean = { data -> listOf<String>("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(data) }
    val validPid: (String) -> Boolean = { data -> data.length == 9 }
    val validHgt: (String) -> Boolean = { data ->
        if (data.length > 2) {
            val value = data.substring(0, data.length - 2).toInt()
            val unit = data.substring(data.length - 2)

            if (unit == "cm") value >= 150 && value <= 193
            else if (unit == "in") value >= 59 && value <= 76
            else false
        } else false
    }

    val reqFields = hashMapOf<String, (String) -> Boolean>(
            Pair("byr", validByr),
            Pair("iyr", validIyr),
            Pair("eyr", validEyr),
            Pair("hgt", validHgt),
            Pair("hcl", validHcl),
            Pair("ecl", validEcl),
            Pair("pid", validPid)
    )
    var kvSets: Set<Map<String, String>> by Delegates.notNull()

    init {
        this.kvSets = input.map() {
            it.replace("\n", " ")
                    .split(" ")
                    .map() { Pair(it.split(":")[0], it.split(":")[1]) }
                    .toMap()
        }.toSet()
    }

    override fun part1(): String {
        return countValidPassports(false).toString()
    }

    override fun part2(): String {
        return countValidPassports(true).toString()
    }

    private fun countValidPassports(checkRestrictions: Boolean): Int {
        return kvSets.map { kvSet ->
            reqFields.filter { field ->
                kvSet.containsKey(field.key) && (!checkRestrictions || field.value(kvSet.getValue(field.key)))
            }.count() == reqFields.size
        }.filter { it }.count()
    }
}
