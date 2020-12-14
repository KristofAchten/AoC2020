package challenges

class D14Masking : Puzzle(14) {
    val input = rawInput.split("\n")

    override fun part1() = runAndSum(
            fun(regs: HashMap<Long, Long>, mask: String, idx: Long, value: Long): Unit {
                val maskedResult = applyMask(mask, value, 'X')
                regs.put(idx, maskedResult.toLong(2))
            }
    ).toString()

    override fun part2() = runAndSum(
            fun(regs: HashMap<Long, Long>, mask: String, idx: Long, value: Long): Unit {
                findAllAdresses(mask, idx).forEach {
                    regs.put(it, value)
                }
            }
    ).toString()

    private fun runAndSum(applyFunc: (HashMap<Long, Long>, String, Long, Long) -> Unit): Long {
        val regs = HashMap<Long, Long>()
        var mask = ""

        input.forEach { op ->
            if (op.startsWith("mask")) {
                mask = op.substring(7)
            } else {
                val parts = op.replace("mem[", "").replace("]", "").split(" = ")
                applyFunc(regs, mask, parts[0].toLong(), parts[1].toLong())
            }
        }

        return regs.values.sum()
    }

    private fun applyMask(mask: String, value: Long, matchOn: Char) = value.toString(2)
            .padStart(36, '0')
            .zip(mask) { sChar, mChar -> if (mChar == matchOn) sChar else mChar }
            .joinToString("")

    private fun findAllAdresses(mask: String, curval: Long): List<Long> {
        val first = applyMask(mask, curval, '0')
        val max = "".padStart(mask.count { it == 'X' }, '1').toLong(2)

        val list = (0..max).map { curVal ->
            val curIt = first.toCharArray()
            curVal.toString(2)
                    .padStart(mask.count { it == 'X' }, '0')
                    .toCharArray()
                    .forEach { curIt[curIt.indexOf('X')] = it }

            curIt.joinToString("")
        }

        return list.map { it.toLong(2) }
    }
}