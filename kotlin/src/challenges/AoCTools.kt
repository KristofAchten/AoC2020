package challenges

class AoCTools {
    companion object {
        fun Array<Array<String>>.deepCopy() = Array(size) { get(it).clone() }

        fun create2DArray(parts: List<String>): Array<Array<String>> {
            return Array(parts.size) { i ->
                Array(parts.get(i).length) { j ->
                    parts.get(i)[j].toString()
                }
            }
        }
    }


}