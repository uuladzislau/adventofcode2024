fun main() {

    fun isStable(report: List<Int>): Boolean {
        val increasing = report[0] < report[1];

        for (i in 0..<report.lastIndex) {
            val delta = report[i + 1] - report[i]

            if (increasing && (delta < 1 || delta > 3)) return false

            if (!increasing && (delta < -3 || delta > -1)) return false
        }

        return true
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
