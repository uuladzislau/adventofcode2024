fun main() {
    val testInput = readInput("Day22_test").map { it.toInt() }
    val input = readInput("Day22").map { it.toInt() }

    check(prune(100000000) == 16113920)
    check(mix(42, 15) == 37)

    check(part1(testInput) == 37327623L)
    check(part1(input) == 17005483322)

    part2(listOf(123))
}

private fun part1(secretNumbers: List<Int>): Long = secretNumbers.sumOf {
    generateSequence(it) { next -> nextSecretNumber(next) }
        .drop(1)
        .take(2000)
        .last().toLong()
}

private fun part2(secretNumbers: List<Int>) {

    val priceChanges = secretNumbers.map { secretNumber ->
        val nextSecretNumbers = sequenceOf(secretNumber) + generateSequence(secretNumber) { nextSecretNumber(it) }
            .drop(1)
            .take(9) // todo: take" 1999

        secretNumber to nextSecretNumbers.map { it % 10 }.zipWithNext { a, b -> b - a }.toList()
    }

    priceChanges.println()


}

private fun nextSecretNumber(initialSecretNumber: Int): Int = initialSecretNumber
    .let { prune(mix(it, it * 64)) }
    .let { prune(mix(it, it / 32)) }
    .let { prune(mix(it, it * 2048)) }

private fun mix(secretNumber: Int, value: Int): Int = value xor secretNumber

private fun prune(secretNumber: Int): Int = secretNumber.mod(16777216)