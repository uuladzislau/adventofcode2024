fun main() {
    val testInput = readInput("Day20_test")
    val input = readInput("Day20")

    part1(testInput).println()
}

private fun part1(map: Grid): Int {
    val start = map.find('S')
    val end = map.find('E')

    val (score, path) = findPath(map, start, end, 0)

    path.println()
    score.println()

    printPath(map, path)

    return 0
}

private fun findPath(map: Grid, start: Coordinate, end: Coordinate, cheatsLeft: Int): Pair<Int, Set<Triple<Coordinate, Direction, Int>>> {
    data class State(
        val from: Coordinate,
        val direction: Direction,
        val score: Int,
        val path: Set<Triple<Coordinate, Direction, Int>>
    )

    val visiting = mutableListOf<State>()

    val visited = mutableSetOf<Pair<Coordinate, Direction>>() // storing pairs of location + direction

    visiting.add(
        State(start, Direction.LEFT, 0, emptySet())
    )

    while (visiting.isNotEmpty()) {
        visiting.sortBy { it.score } // always prefer nodes with lower score, like in Dijkstra's

        val (loc, direction, score, path) = visiting.removeFirst()

        if (loc == end) return score to path

        if (loc to direction in visited) continue

        visited += loc to direction

        val nextLoc = loc + direction.offset

        val currentPath = path + Triple(loc, direction, score)

        if (map[nextLoc] != '#') {
            visiting += State(nextLoc, direction, score + 1, currentPath)
        }

        visiting += State(loc, direction.plus90(), score, path)
        visiting += State(loc, direction.minus90(), score, path)
    }

    error("Can't solve :(")
}

private fun printPath(map: Grid, path: Set<Triple<Coordinate, Direction, Int>>) {
    val pathMapping = path.map { it.first to it.second }.toMap()
    buildString {
        for (i in map.indices) {
            for (j in map[0].indices) {
                if (pathMapping[i to j] != null) {
                    append(
                        when (pathMapping.getValue(i to j)) {
                            Direction.UP -> '^'
                            Direction.DOWN -> 'v'
                            Direction.RIGHT -> '>'
                            Direction.LEFT -> '<'
                        }
                    )
                } else {
                    append(map[i to j])
                }
            }
            append('\n')
        }
    }.println()
}