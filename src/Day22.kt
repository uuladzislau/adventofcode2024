fun main() {
    val testInput = readInput("Day22_test").map { it.toInt() }
    val input = readInput("Day22").map { it.toInt() }

    check(prune(100000000) == 16113920)
    check(mix(42, 15) == 37)

    check(part1(testInput) == 37327623L)
    check(part1(input) == 17005483322)
}

private fun part1(secretNumbers: List<Int>): Long = secretNumbers.map {
    var secretNumber = it
    repeat(2000, { secretNumber = nextSecretNumber(secretNumber) })
    secretNumber.toLong()
}.sum()

private fun nextSecretNumber(initialSecretNumber: Int): Int {
    val step1 = prune(mix(initialSecretNumber, initialSecretNumber * 64))

    val step2: Int = prune(mix(step1, step1 / 32))

    val step3 = prune(mix(step2, step2 * 2048))

    return step3
}

private fun mix(secretNumber: Int, value: Int): Int = value xor secretNumber

private fun prune(secretNumber: Int): Int = secretNumber.mod(16777216)