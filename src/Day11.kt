fun main() {
    val testInput = readInput("Day11_test").first().split(" ")
    val input = readInput("Day11").first().split(" ")

    check(part1(testInput) == 55312)
    check(part1(input) == 218956)

    check(part2(input) == 259593838049805)
}

private fun part1(input: List<String>): Int {
    var afterObservation = input

    repeat(25) {
        afterObservation = observe(afterObservation)
    }

    return afterObservation.size
}

val leadingZeroes = """^0+""".toRegex()

private fun observe(stones: List<String>): List<String> = buildList {
    stones.forEach { stone ->
        if (stone == "0") {
            add("1")
            return@forEach
        }

        if (stone.length % 2 == 0) {
            val middle = stone.length / 2

            val first = stone.substring(0 until middle)
            var second = stone.substring(middle until stone.length).replace(leadingZeroes, "")

            if (second == "") {
                second = "0"
            }

            add(first)
            add(second)

            return@forEach
        }

        add("${stone.toLong() * 2024}")
    }
}

private fun part2(input: List<String>) = input.map(::foo).sum()

private val cache = mutableMapOf<Pair<String, Int>, Long>()

private fun foo(stone: String, level: Int = 0): Long {
    if (level == 75) return 1

    val key = stone to level

    if (cache.contains(key)) {
        return cache.getValue(key)
    }

    if (stone == "0") {
        val localResult = foo("1", level + 1)
        cache[key] = localResult
        return localResult
    }

    if (stone.length % 2 == 0) {
        val middle = stone.length / 2

        val first = stone.substring(0 until middle)
        var second = stone.substring(middle until stone.length).replace(leadingZeroes, "")

        if (second == "") {
            second = "0"
        }

        val localResult = foo(first, level + 1) + foo(second, level + 1)
        cache[key] = localResult
        return localResult
    }

    val localResult = foo("${stone.toLong() * 2024}", level + 1)
    cache[key] = localResult
    return localResult
}
