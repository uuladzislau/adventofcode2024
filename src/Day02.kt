fun main() {

    fun isStable(report: List<Int>): Boolean {
        // calculate the difference (delta) of two adjacent elements
        // so [1, 3, 4, 5, 8] -> [2, 1, 1, 3]
        val diffs = report.zipWithNext { a, b -> a - b}

        return diffs.all { it in -3..3} && (diffs.all { it > 0 } || diffs.all { it < 0 })
    }

    fun part1(input: List<String>): Int {
        return input
            .map { it.split(" ").map { it.toInt() } }
            .count(::isStable)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day02")

    check(part1(input) == 383)

    check(part2(input) == 1000)
}
