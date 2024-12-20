import kotlin.math.abs

fun main() {
    val input = readInput("Day20")

    // Part 1
    check(solve(map = input, cheatDuration =  2) == 1490)
    // Part 2
    check(solve(map = input, cheatDuration = 20) == 1011325)
}

private fun solve(map: Grid, cheatDuration: Int): Int {
    val start = map.find('S')
    val end = map.find('E')

    // Path is the list of all nodes visited from start to end.
    val path = findPath(map, start, end)

    // Outer loop (i) iterates from first visited node until the penultimate (second to the last) node.
    return (0 until path.lastIndex - 1).sumOf { i ->
        // Inner loop (j) iterates from i + 1 node until the very last node in the path.
        (i + 1 until path.size).count { j ->
            // We calculate manhattan distance between nodes i and j.
            val distance = manhattanDistance(path[i], path[j])

            // If the distance > threshold (which is the cheat duration), then shortcut is not possible.
            if (distance > cheatDuration) return@count false

            val savedTime = j - i - distance

            savedTime >= 100
        }
    }
}

private fun findPath(map: Grid, start: Coordinate, end: Coordinate): List<Coordinate> {
    var loc = start

    val path = mutableListOf(loc)

    val visited = mutableSetOf<Coordinate>()

    while (loc != end) {
        visited += loc

        val direction = Direction.entries.first {
            val nextLoc = loc + it.offset
            nextLoc !in visited && map[nextLoc] != '#'
        }

        loc += direction.offset

        path += loc
    }

    return path
}

private fun manhattanDistance(c1: Coordinate, c2: Coordinate): Int =
    abs(c1.first - c2.first) + abs(c1.second - c2.second)
