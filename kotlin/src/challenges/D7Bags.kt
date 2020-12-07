package challenges

data class BagContent(val color: String, val count: Int)

class D7Bags : Puzzle(7) {
    val input = rawInput.split("\n")
    val bagDefs = mutableMapOf<String, List<BagContent>>()
    val shinyGold = "shiny gold"

    init {
        for (line in input) {
            val bagAndContent = line.split(" bags contain ")

            var contentList = mutableListOf<BagContent>()
            if (!bagAndContent[1].equals("no other bags.")) {
                val contentDefs = bagAndContent[1]
                        .replace(".", "")
                        .replace(", ", "")
                        .replace("bags", "bag")
                        .split(" bag")

                contentDefs.filter { !it.isBlank() }.forEach() { contentDef ->
                    val parts = contentDef.split(" ")
                    contentList.add(BagContent(parts[1] + " " + parts[2], parts[0].toInt()))
                }
            }

            bagDefs.put(bagAndContent[0], contentList)
        }
    }

    private fun containsBag(color: String, curColor: String, list: List<BagContent>): Boolean {
        if (color.equals(curColor) || list.any { it.color.equals(curColor) }) {
            return true
        }

        return list.any() { containsBag(color, it.color, bagDefs.get(it.color)!!) }
    }

    private fun countBags(color: String): Int {
        if (bagDefs.get(color)!!.isEmpty()) {
            return 0
        }

        return bagDefs.get(color)!!.map { it.count * (1 + countBags(it.color)) }.sum()
    }

    override fun part1() = bagDefs.filter { !it.key.equals(shinyGold) && containsBag(shinyGold, it.key, it.value) }.count().toString()
    override fun part2() = countBags(shinyGold).toString()
}

