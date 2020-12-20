package challenges

import kotlin.properties.Delegates

interface RegexPart
data class Literal(val literal: String) : RegexPart
data class Ref(val ref: Int) : RegexPart
data class Rule(val ruleParts: List<List<RegexPart>>)

class D19Regex : Puzzle(19) {

    val rules = mutableMapOf<Int, Rule>()
    var messages: List<String> by Delegates.notNull()

    init {
        val rulesAndMessages = rawInput.split("\n\n")

        rulesAndMessages[0].split("\n").forEach {
            val ruleParts = it.split(": ")
            rules.put(ruleParts[0].toInt(), stringToRule(ruleParts[1]))
        }

        this.messages = rulesAndMessages[1].split("\n").toList()
    }

    override fun part1(): String {
        val pat = getPattern(Ref(0)).toRegex()
        return messages.filter { it.matches(pat) }.count().toString()
    }

    override fun part2(): String {
        // Make infinite loops finite... 100 is just a number that worked.
        // Rule 0 = 8 11, so try all combinations and throw them in a big mf regex
        // Rule 8 = 42 | 42 8 = 42 42 42 42 ...
        // Rule 11 = 42 31 | 42 11 31 = 42(*x) 31(*x)
        // = 42(*x) 31(*y) where x > y

        val p42 = getPattern(Ref(42))
        val p31 = getPattern(Ref(31))
        val regex = (2..100).joinToString("|") { "($p42){${it},}($p31){${it - 1}}" }.toRegex()

        return messages.filter {  it.matches(regex) }.count().toString()
    }

    private fun stringToRule(input: String): Rule {
        val parts = input.split(" | ").filter { it.isNotBlank() }

        return Rule(parts.map {
            it.split(" ").filter { it.isNotBlank() }.map {
                if (it.startsWith('"')) {
                    Literal(it.substring(1, it.length - 1))
                } else {
                    Ref(it.toInt())
                }
            }.toList()
        }.toList())
    }

    private fun getPattern(ref: Ref): String {
        return rules.get(ref.ref)!!.ruleParts.joinToString("|", "(", ")") { branch ->
            branch.joinToString("") { item ->
                if (item is Ref) {
                    getPattern(item)
                } else {
                    (item as Literal).literal
                }
            }
        }
    }
}