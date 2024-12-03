fun main() {
    val input = readInput("Day03")

    check(part1(input) == 159833790)

    check(part2(input) == 89349241)
}

private fun part1(input: List<String>): Int {
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

private fun part2(input: List<String>): Int {

    val regex = Regex("(mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don't\\(\\))")

    val mapped: List<List<Operation>> = input.map { line ->
        regex.findAll(line)
            .map { matchResult -> matchResult.value }
            .map {
                when {
                    it.startsWith("mul") -> Mul.of(it)
                    it == "do()" -> Do()
                    it == "don't()" -> Dont()
                    else -> throw IllegalArgumentException("I don't know what to do with $it")
                }
            }
            .toList()
    }

    var shouldMultiply = true;

    return mapped.sumOf { line ->
        var sum = 0;
        for (operation in line) {
            when (operation) {
                is Mul -> if (shouldMultiply) sum += operation.a * operation.b
                is Do -> shouldMultiply = true
                is Dont -> shouldMultiply = false
            }
        }
        sum
    }
}

private class Do : Operation

private class Dont : Operation

private data class Mul(val a: Int, val b: Int) : Operation {
    companion object {
        fun of(s: String): Mul {
            val (a, b) = s.drop(4).dropLast(1).split(",").map { it.toInt() }
            return Mul(a, b)
        }
    }
}

private interface Operation
