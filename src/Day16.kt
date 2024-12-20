fun main() {
    val testInput = readInput("Day16_test")
    val input = readInput("Day16")

    check(part1(testInput) == 11048)
    check(part1(input) == 72428)
}

private fun part1(maze: Grid): Int {
    val start: Coordinate = maze.find('S')
    val end: Coordinate = maze.find('E')

    val visiting = mutableListOf<Triple<Coordinate, Direction, Int>>()

    val visited = mutableSetOf<Pair<Coordinate, Direction>>() // storing pairs of location + direction

    visiting.add(
        Triple(start, Direction.LEFT, 0)
    )

    while (visiting.isNotEmpty()) {
        visiting.sortBy { it.third } // always prefer nodes with lower score, like in Dijkstra's

        val (loc, direction, score) = visiting.removeFirst()

        if (loc == end) return score

        if (loc to direction in visited) continue

        visited += loc to direction

        val nextLoc = loc + direction.offset

        if (maze[nextLoc] != '#') {
            visiting += Triple(nextLoc, direction, score + 1)
        }

        visiting += Triple(loc, direction.plus90(), score + 1000)
        visiting += Triple(loc, direction.minus90(), score + 1000)
    }

    return -1
}
