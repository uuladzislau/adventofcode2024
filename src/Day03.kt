fun main() {
    fun part1(input: List<String>): Int {
        val regex = Regex("mul\\(\\d{1,3},\\d{1,3}\\)")

        return input.sumOf { line ->
            regex.findAll(line)
                .map { matchResult -> matchResult.value }
                .map { matchedValue ->
                    // At this point we have a string like "mul(123,456)"
                    // We drop first 4 characters, then drop last character, then split by comma
                    // So we get: "mul(123,456)" -> "123,456)" -> "123,456" -> ["123", "456"]
                    // And then we map each numeric element to integer
                    val (a, b) = matchedValue.drop(4).dropLast(1).split(",").map { it.toInt() }
                    a * b
                }
                .sum()
        }
    }

    val input = readInput("Day03")

    check(part1(input) == 159833790)

}