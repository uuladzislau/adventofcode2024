fun main() {
    val input = readRawInput("Day05")

    val (rawRules, rawUpdates) = input.split("\n\n")

    val rules = rawRules.lines().map { it.split("|") }.groupBy({ it[0] }, { it[1] })

    val updates = rawUpdates.lines().map { it.split(",") }

    check(part1(rules, updates) == 5129)
    check(part2(rules, updates) == 4077)
}

private fun part1(rules: Map<String, List<String>>, updates: List<List<String>>): Int {
    return updates
        .filter { it.allInCorrectOrder(rules) }
        .sumOf { it[it.size / 2].toInt() }
}

private fun part2(rules: Map<String, List<String>>, updates: List<List<String>>): Int {
    return updates
        .filter { !it.allInCorrectOrder(rules) }
        .map {
            var mutable = it
            do {
                mutable = mutable.order(rules)
            } while (!mutable.allInCorrectOrder(rules))
            mutable
        }
        .sumOf { it[it.size / 2].toInt() }
}

private fun List<String>.order(rules: Map<String, List<String>>): List<String> {
    val positions = this.mapIndexed { i, item -> item to i }.toMap()

    for (rule in rules) {
        if (positions[rule.key] == null) continue

        for (nextPage in rule.value) {
            if (positions[nextPage] == null) continue

            val pagePosition = positions.getValue(rule.key)
            val nextPagePosition = positions.getValue(nextPage)

            if (pagePosition > nextPagePosition) {
                val reordered = this.toMutableList()
                reordered[nextPagePosition] = rule.key
                reordered[pagePosition] = nextPage
                return reordered.toList()
            }
        }
    }

    return this
}

private fun List<String>.allInCorrectOrder(rules: Map<String, List<String>>): Boolean {
    val positions = mapIndexed { i, item -> item to i }.toMap()
    return all { page ->
        if (rules[page] == null) true else rules.getValue(page).all { rule ->
            if (positions[rule] == null) true else positions.getValue(rule) > positions.getValue(page)
        }
    }
}
