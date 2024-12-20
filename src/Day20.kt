fun main() {
    val testInput = readInput("Day20_test")
    val input = readInput("Day20")

    part1(testInput).println()
}

private fun part1(map: Grid): Int {
    val start = map.find('S')
    val end = map.find('E')

    findPath(map, start, end).println()

    return 0
}

private fun findPath(map: Grid, start: Coordinate, end: Coordinate): Int {
    data class State(
        val loc: Coordinate,
        val score: Int,
        val cheated: Boolean
    )

    val visiting = mutableListOf<State>()

    val visited = mutableSetOf<Pair<Coordinate, Direction>>() // storing pairs of location + direction

    visiting.add(
        State(start, 0, false)
    )

    while (visiting.isNotEmpty()) {
        val (loc, score) = visiting.removeFirst()

        if (loc == end) return score

        for (direction in Direction.entries) {
            if (loc to direction in visited) continue

            visited += loc to direction

            val nextLoc = loc + direction.offset

            if (map[nextLoc] != '#') {
                visiting += State(nextLoc, score + 1, false)
            }
        }
    }

    error("Can't solve :(")
}
