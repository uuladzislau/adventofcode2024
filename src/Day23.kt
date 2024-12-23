fun main() {
    val testInput = readInput("Day23_test")
    val input = readInput("Day23")

    check(part1(testInput) == 7)
    check(part1(input) == 1284)
}

private fun part1(input: List<String>): Int {

    val connections = buildMap<String, MutableSet<String>> {
        input.map { it.split("-") }.map { it[0] to it[1] }.forEach { (comp1, comp2) ->
            computeIfAbsent(comp1) { mutableSetOf() }.add(comp2)
            computeIfAbsent(comp2) { mutableSetOf() }.add(comp1)
        }
    }

    val triples = buildSet {
        for ((pc1, connectedTo) in connections) {
            for (pc2 in connectedTo) {
                val pc2Connections = connections.getValue(pc2).filter { it != pc1 }

                for (pc3 in pc2Connections) {
                    if (connections.getValue(pc3).filter { it != pc3 }.contains(pc1)) add(setOf(pc1, pc2, pc3))
                }
            }
        }
    }

    return triples.count { it.any { connection -> connection.startsWith("t") } }
}
