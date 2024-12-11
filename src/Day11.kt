fun main() {
    val testInput = "125 17".split(" ")
    val input = readInput("Day11").first().split(" ")

    check(observe(testInput, 25) == 55312L)

    check(observe(input, 25) == 218956L)
    check(observe(input, 75) == 259593838049805)
}

private fun observe(stones: List<String>, blinks: Int): Long {

    val cache = mutableMapOf<Pair<String, Int>, Long>()

    val leadingZeroes = """^0+""".toRegex()

    fun observe(stone: String, blinks: Int = 0, max: Int): Long {
        if (blinks == max) return 1

        val key = stone to blinks

        if (cache.contains(key)) {
            return cache.getValue(key)
        }

        val localResult: Long

        if (stone == "0") {
            localResult = observe("1", blinks + 1, max)
        } else if (stone.length % 2 == 0) {
            val middle = stone.length / 2

            val first = stone.substring(0 until middle)
            var second = stone.substring(middle until stone.length).replace(leadingZeroes, "")

            if (second == "") {
                second = "0"
            }

            localResult = observe(first, blinks + 1, max) + observe(second, blinks + 1, max)
        } else {
            localResult = observe("${stone.toLong() * 2024}", blinks + 1, max)
        }

        cache[key] = localResult
        return localResult
    }

    return stones.sumOf { observe(it, 0, blinks) }
}
