package challenges

class D18OperationOrder : Puzzle(18) {

    val input = rawInput.split("\n")

    override fun part1() = input.map { calculate(it, evaluateStackNoPrecedence) }.sum().toString()
    override fun part2() = input.map { calculate(it, evaluateStackPlusPrecedence) }.sum().toString()

    private fun calculate(formula: String, evalFunc: (Stack<String>) -> Unit): Long {
        val operationStack = Stack<String>()
        val parts = formula.replace(" ", "").split("").filter { it.isNotBlank() }

        for (i in parts.size - 1 downTo 0) {
            when (parts[i]) {
                "(" -> evalFunc(operationStack)
                else -> operationStack.push(parts[i])
            }
        }
        evalFunc(operationStack)

        return operationStack.pop().toLong()
    }

    val evaluateStackNoPrecedence: (Stack<String>) -> Unit = { operationStack ->
        var res = operationStack.pop().toLong()

        loop@ while (operationStack.size() > 0) {
            when (operationStack.pop()) {
                "+" -> res += operationStack.pop().toLong()
                "*" -> res *= operationStack.pop().toLong()
                ")" -> break@loop
            }
        }

        operationStack.push(res.toString())
    }

    val evaluateStackPlusPrecedence: (Stack<String>) -> Unit = { operationStack ->
        val altStack = Stack<String>()

        loop@ while (operationStack.size() > 0) {
            val popped = operationStack.pop()
            when (popped) {
                "+" -> altStack.push((operationStack.pop().toLong() + altStack.pop().toLong()).toString())
                "*" -> altStack.push(operationStack.pop())
                ")" -> break@loop
                else -> altStack.push(popped)
            }
        }

        var res = 1L
        while (altStack.size() > 0) {
            res *= altStack.pop().toLong()
        }

        operationStack.push(res.toString())
    }
}