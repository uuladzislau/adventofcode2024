fun main() {
    val input = readRawInput("Day05")

    val (rawRules, rawUpdates) = input.split("\n\n")

    val rules = rawRules.lines().map { it.split("|") }.groupBy({ it[0] }, { it[1] })

    val updates = rawUpdates.lines().map { it.split(",") }

    check(part1(rules, updates) == 5129)
}

fun part1(rules: Map<String, List<String>>, updates: List<List<String>>): Int {
    return updates
        .filter { it.allInCorrectOrder(rules) }
        .sumOf { it[it.size / 2].toInt() }
}

private fun List<String>.allInCorrectOrder(rules: Map<String, List<String>>): Boolean {
    val positions = mapIndexed { i, item -> item to i }.toMap()
    return all { page ->
        if (rules[page] == null) true else rules.getValue(page).all { rule ->
            if (positions[rule] == null) true else positions.getValue(rule) > positions.getValue(page)
        }
    }
}
