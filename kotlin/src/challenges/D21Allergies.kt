package challenges

class D21Allergies : Puzzle(21) {

    val allFoods = mutableListOf<Pair<Set<String>, Set<String>>>()

    init {

        rawInput.split("\n").forEach {
            val ingredientsAndAllergies = it
                    .replace(",", "")
                    .replace(")", "")
                    .split(" (contains ")

            allFoods.add(Pair(
                    ingredientsAndAllergies[1].split(" ").filter { it.isNotBlank() }.toSet(),
                    ingredientsAndAllergies[0].split(" ").filter { it.isNotBlank() }.toSet()
            ))
        }
    }

    override fun part1() = determineOtherIngredients(getAllergyList()).toString()
    override fun part2() = getAllergyList().toSortedMap().values.map { it.first() }.joinToString(",")

    private fun determineOtherIngredients(allergyList: Map<String, Set<String>>): Any {
        val ingredients = allergyList.values.flatten()
        return allFoods.map { it.second.filterNot { ingredients.contains(it) }.size }.sum()
    }

    private fun getAllergyList(): Map<String, Set<String>> {
        var allergyToFoods = mutableMapOf<String, Set<String>>()

        allFoods.forEach { food ->
            food.first.forEach { allergy ->
                if (allergyToFoods.containsKey(allergy)) {
                    allergyToFoods.put(allergy, allergyToFoods.get(allergy)!!.intersect(food.second).toMutableSet())
                } else {
                    allergyToFoods.put(allergy, food.second)
                }
            }
        }

        while (!allergyToFoods.values.all { it.size == 1 }) {
            allergyToFoods.entries.filter { it.value.size == 1 }.forEach { entry ->
                allergyToFoods.filter { it != entry }.forEach {
                    allergyToFoods.put(it.key, it.value - entry.value.first())
                }
            }
        }

        return allergyToFoods
    }
}