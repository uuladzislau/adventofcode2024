fun main() {
    val testInput = readInput("Day22_test").map { it.toInt() }
    val input = readInput("Day22").map { it.toInt() }

    check(prune(100000000) == 16113920)
    check(mix(42, 15) == 37)

    check(part1(testInput) == 37327623L)
    check(part1(input) == 17005483322)

    part2(listOf(1, 2, 3, 2024))
//    part2(input) // 2077 is too high
}

private fun part1(secretNumbers: List<Int>): Long = secretNumbers.sumOf {
    generateSequence(it) { next -> nextSecretNumber(next) }
        .drop(1)
        .take(2000)
        .last().toLong()
}

private fun part2(secretNumbers: List<Int>) {
    val pricePerCustomer = secretNumbers.map { secretNumber ->
        (sequenceOf(secretNumber) + generateSequence(secretNumber) { nextSecretNumber(it) })
            .drop(1)
            .take(1999) // todo: take" 1999
            .map { it % 10 }
            .toList()
    }

    val patterns = mutableMapOf<String, Int>()

    for (prices in pricePerCustomer) {
        for (i in 4..prices.lastIndex) {
            val price1 = prices[i - 3] - prices[i - 4]
            val price2 = prices[i - 2] - prices[i - 3]
            val price3 = prices[i - 1] - prices[i - 2]
            val price4 = prices[i - 0] - prices[i - 1]

            val pattern = "$price1,$price2,$price3,$price4"
//
//            if (patterns.contains(pattern)) {
//                println("Windows: '$pattern', current price: ${prices[i]}")
//            }

            val hits = patterns.getOrDefault(pattern, 0)

            patterns[pattern] = hits + prices[i]
        }
    }

    patterns.values.max().println()
}

private fun nextSecretNumber(initialSecretNumber: Int): Int = initialSecretNumber
    .let { prune(mix(it, it * 64)) }
    .let { prune(mix(it, it / 32)) }
    .let { prune(mix(it, it * 2048)) }

private fun mix(secretNumber: Int, value: Int): Int = value xor secretNumber

private fun prune(secretNumber: Int): Int = secretNumber.mod(16777216)
