fun main() {
    val input = readInput("Day03")

    check(part1(input) == 159833790)

    check(part2(input) == 89349241)
}

private fun part1(input: List<String>): Int {
    val regex = Regex("""mul\(\d{1,3},\d{1,3}\)""")

    return input.sumOf { line ->
        regex.findAll(line)
            .map { matchResult -> matchResult.value }
            .map { matchedValue ->
                val (a, b) =
                    matchedValue
                        .removeSurrounding("mul(", ")")
                        .split(",")
                        .map { it.toInt() }
                a * b
            }
            .sum()
    }
}

private fun part2(lines: List<String>): Int {
    val fullMemory = lines.joinToString("")

    val regex = Regex("""(mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\))""")

    var shouldMultiply = true
    var sum = 0

    regex.findAll(fullMemory).forEach { match ->
        when (match.value) {
            "do()" -> shouldMultiply = true
            "don't()" -> shouldMultiply = false
            else -> if (shouldMultiply) {
                val (a, b) = match.value.removeSurrounding("mul(", ")").split(",")
                sum += a.toInt() * b.toInt()
            }
        }
    }

    return sum
}
