fun main() {
    val testInput = readInput("Day23_test")
    val input = readInput("Day23")

    check(part1(testInput) == 7)
    check(part1(input) == 1284)
}

private fun part1(input: List<String>): Int {

    val connections = buildMap<String, MutableSet<String>> {
        input.map { it.split("-") }.map { it[0] to it[1] }.forEach { (pc1, pc2) ->
            computeIfAbsent(pc1) { mutableSetOf() }.add(pc2)
            computeIfAbsent(pc2) { mutableSetOf() }.add(pc1)
        }
    }

    val triples = buildSet {
        for ((pc1, connectedTo) in connections) {
            for (pc2 in connectedTo) {
                for (pc3 in connections.getValue(pc2)) {
                    if (connections.getValue(pc3).contains(pc1)) add(setOf(pc1, pc2, pc3))
                }
            }
        }
    }

    return triples.count { it.any { connection -> connection.startsWith("t") } }
}
