package challenges

enum class ReturnType {LOOP, SUCCESS}

data class Instruction(var operation: String, val argument: Int, var ran: Boolean)
data class ProgramResult(val resultType: ReturnType, val resultAcc: Int)

class D8Halting : Puzzle(8) {

    val input = rawInput.split("\n")
    val runList = input.map { Instruction(it.split(' ')[0], it.split(' ')[1].toInt(), false) }.toList()


    override fun part1() = runProgram().resultAcc.toString()
    override fun part2() = findTerminatingSolution().toString()

    private fun runProgram(): ProgramResult {
        var acc = 0
        var ctr = 0

        while (true) {
            val instruction = runList.get(ctr)
            if (instruction.ran) {
                return ProgramResult(ReturnType.LOOP, acc)
            }
            when (instruction.operation) {
                "acc" -> {
                    acc += instruction.argument; ctr++
                }
                "jmp" -> ctr += instruction.argument;
                "nop" -> ctr++
            }

            if (ctr < 0 || ctr >= runList.size) {
                return ProgramResult(ReturnType.SUCCESS, acc)
            }

            instruction.ran = true
        }
    }

    private fun resetInstructions() {
        runList.forEach { it.ran = false }
    }

    private fun findTerminatingSolution(): Int {
        runList.forEach {instruction ->
            resetInstructions()

            var oldOp = instruction.operation
            when (oldOp) {
                "jmp" -> instruction.operation = "nop"
                "nop" -> instruction.operation = "jmp"
            }

            val result = runProgram()

            if (result.resultType == ReturnType.SUCCESS) {
                return result.resultAcc
            }

            instruction.operation = oldOp
        }
        throw IllegalStateException("Could not fix the given program so that it terminates.")
    }
}