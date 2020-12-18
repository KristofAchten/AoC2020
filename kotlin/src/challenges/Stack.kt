package challenges

class Stack<E> {
    private val elements = mutableListOf<E>()

    fun push(el: E) = elements.add(el)
    fun pop() = elements.removeAt(elements.size - 1)
    fun size() = elements.size
}