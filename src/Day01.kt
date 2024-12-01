import kotlin.math.abs

fun main() {
    val input = readInput("Day01")

    check(part1(input) == 1603498)

    check(part2(input) == 25574739)
}

fun part1(input: List<String>): Int {
    val (leftInput, rightInput) = parseInput(input)

    val leftSorted = leftInput.sorted().toIntArray()
    val rightSorted = rightInput.sorted().toIntArray()

    return leftSorted
        .zip(rightSorted) { left, right -> abs(left - right) }
        .sum()
}

fun part2(input: List<String>): Int {
    val (leftInput, rightInput) = parseInput(input)

    val heatmap = rightInput.groupingBy { it }.eachCount()

    return leftInput.sumOf { it * heatmap.getOrDefault(it, 0) }
}

fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
    // Read input, convert to numeric format, and split into two lists.
    return input
        .map { it.split("   ") }
        .map { it[0].toInt() to it[1].toInt() }
        .unzip()
}
