fun main() {
    val testInput = readInput("Day11_test").first().split(" ")
    val input = readInput("Day11").first().split(" ")

    check(part1(testInput) == 55312)
    check(part1(input) == 218956)
}

private fun part1(input: List<String>): Int {
    var afterObservation = input

    repeat(25) {
        afterObservation = observe(afterObservation)
    }

    return afterObservation.size
}

private fun observe(stones: List<String>): List<String> = buildList {
    stones.forEach { stone ->
        if (stone == "0") {
            add("1")
            return@forEach
        }

        if (stone.length % 2 == 0) {
            val middle = stone.length / 2

            val leadingZeroes = """^0+""".toRegex()

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
