fun main() {
    val testInput = readInput("Day18_test")
    val input = readInput("Day18")

    check(part1(testInput, 6, 12) == 22)
    check(part1(input, 70, 1024) == 314)
}

private fun part1(input: List<String>, area: Int, wait: Int): Int {
    val corrupted: Set<Coordinate> =
        input
            .take(wait)
            .map { it.split(",") }
            .map { it[1].toInt() to it[0].toInt() }
            .toSet()

    val map = buildMap(corrupted, area)

    val start = 0 to 0
    val end = area to area

    val visiting = mutableListOf<Triple<Coordinate, Coordinate, Int>>()

    val visited = mutableSetOf<Pair<Coordinate, Coordinate>>() // storing pairs of location + direction

    visiting.add(
        Triple(start, (0 to 1), 0)
    )

    while (visiting.isNotEmpty()) {
        visiting.sortBy { it.third } // always prefer nodes with lower score, like in Dijkstra's

        val (loc, direction, score) = visiting.removeFirst()

        if (loc == end) return score

        if (loc to direction in visited) continue

        visited += loc to direction

        val nextLoc = loc + direction

        if (nextLoc.within(map) && map[nextLoc] != '#') {
            visiting += Triple(nextLoc, direction, score + 1)
        }

        visiting += Triple(loc, direction.plus90(), score)
        visiting += Triple(loc, direction.plus90().plus90(), score)
        visiting += Triple(loc, direction.plus90().plus90().plus90(), score)
    }

    return -1
}

private fun buildMap(corrupted: Set<Coordinate>, area: Int): Grid = (0..area).map { i ->
    (0..area)
        .map { j -> if (i to j in corrupted) '#' else '.' }
        .joinToString("")
}

private fun Coordinate.within(map: Grid): Boolean =
    (this.first >= 0 && this.second >= 0) &&
            (this.first < map.size && this.second < map.size)

private fun Coordinate.plus90(): Coordinate = when (this) {
    0 to 1 -> 1 to 0
    1 to 0 -> 0 to -1
    0 to -1 -> -1 to 0
    -1 to 0 -> 0 to 1
    else -> error("Can't figure out how to turn :(")
}
