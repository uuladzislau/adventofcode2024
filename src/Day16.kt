fun main() {
    val testInput = readInput("Day16_test")
    val input = readInput("Day16")

    check(part1(testInput) == 11048)
    check(part1(input) == 72428)
}

private fun part1(maze: Grid): Int {
    val start: Coordinate = maze.find('S')
    val end: Coordinate = maze.find('E')

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

        if (maze[nextLoc] != '#') {
            visiting += Triple(nextLoc, direction, score + 1)
        }

        visiting += Triple(loc, direction.plus90(), score + 1000)
        visiting += Triple(loc, direction.minus90(), score + 1000)
    }

    return -1
}

private fun Grid.find(c: Char): Coordinate {
    for (i in indices) {
        for (j in this[i].indices) {
            if (this[i][j] == c) {
                return i to j
            }
        }
    }
    throw IllegalStateException("Can't find '$c' :(")
}

private fun Coordinate.plus90(): Coordinate = when (this) {
    0 to 1 -> 1 to 0
    1 to 0 -> 0 to -1
    0 to -1 -> -1 to 0
    -1 to 0 -> 0 to 1
    else -> error("Can't figure out how to turn :(")
}

private fun Coordinate.minus90(): Coordinate = plus90().plus90().plus90()
