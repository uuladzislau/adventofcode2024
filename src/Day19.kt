import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val testInput = parseInput("Day19_test")
    val input = parseInput("Day19")

    check(part1(testInput) == 6)

    check(part1(input) == 306)

    check(part2(input) == 604622004681855)
}

private fun part1(input: Pair<Set<String>, List<String>>): Int {
    val (patterns, designs) = input

    return designs.count { designPossible(patterns, it) > 0 }
}

private fun part2(input: Pair<Set<String>, List<String>>): Long {
    val (patterns, designs) = input

    return designs.sumOf { designPossible(patterns, it) }
}

private fun designPossible(patterns: Set<String>, design: String): Long {
    val cache: MutableMap<String, Long> = mutableMapOf("" to 1L)

    fun isPossible(towel: String): Long = cache.getOrPut(towel) {
        patterns
            .filter { towel.startsWith(it) }
            .sumOf { isPossible(towel.removePrefix(it)) }
    }

    return isPossible(design)
}

private fun parseInput(fileName: String): Pair<Set<String>, List<String>> {
    val input = Path("src/$fileName.txt").readText()

    val (patterns, designs) = input.split("\n\n")

    return patterns.split(", ").toSet() to designs.lines()
}
