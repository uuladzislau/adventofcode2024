fun main() {
    val input = readInput("Day04")

    check(part1(input) == 2593)

    check(part2(input) == 1950)
}

private fun part1(input: List<String>): Int {
    return input.flatMapIndexed { y, row ->
        row.mapIndexed { x, c ->
            if (c == 'X') {
                ALL_DIRECTIONS.count { vector ->
                    vectorFind("XMAS", x, y, vector, input)
                }
            } else 0
        }
    }.sum()
}

private fun part2(input: List<String>): Int {
    return input.flatMapIndexed { y, row ->
        row.mapIndexed { x, c ->
            if (c == 'A') {
                CORNERS
                    .map { (dx, dy) -> input.safeAt(x + dx, y + dy) }
                    .joinToString("") in setOf("MMSS", "MSSM", "SSMM", "SMMS")
            } else false
        }
    }.count { it }

}

private val CORNERS = listOf(-1 to -1, -1 to 1, 1 to 1, 1 to -1)

private val ALL_DIRECTIONS = listOf(
    -1 to -1, -1 to 0, -1 to 1,
    0 to -1, 0 to 1,
    1 to -1, 1 to 0, 1 to 1
)

private fun List<String>.safeAt(x: Int, y: Int): Char =
    if (y in indices && x in this[y].indices) this[y][x] else ' '

private tailrec fun vectorFind(target: String, x: Int, y: Int, vector: Pair<Int, Int>, input: List<String>): Boolean {
    return when {
        target.isEmpty() -> true
        target.first() != input.safeAt(x, y) -> false
        else -> vectorFind(target.substring(1), x + vector.first, y + vector.second, vector, input)
    }
}
