fun main() {
    val input = readRawInput("Day05")

    check(part1(input) == 5129)
}

private fun part1(input: String): Int {

    val (rawRules, rawUpdates) = input.split("\n\n")

    val rules = rawRules.lines().map { it.split("|") }.map { Pair(it[0].toInt(), it[1].toInt()) }
    val updates = rawUpdates.lines().map { line -> line.split(",").map { it.toInt() } }

    val rulesMap = mutableMapOf<Int, MutableSet<Int>>()

    rules.forEach { rule ->
        val entry = rulesMap[rule.first]

        if (entry != null) {
            entry.add(rule.second)
        } else {
            rulesMap[rule.first] = mutableSetOf(rule.second)
        }
    }


    return updates.sumOf { update ->
        val positions = mutableMapOf<Int, Int>()

        update.forEachIndexed { position, page ->
            positions[page] = position
        }

        var correctOrder = true

        println("")

        update.forEachIndexed { position, page ->
            val rulesForPage = rulesMap[page]

            if (rulesForPage != null) {
                for (r in rulesForPage) {
                    if (positions.contains(r)) {
                        if (positions[r]!! < position) {
                            correctOrder = false
                            break
                        }
                    }
                }
            }
        }

        if (correctOrder) {
            // return middle page
            return@sumOf update[update.size / 2]
        }

        return@sumOf 0
    }
}
