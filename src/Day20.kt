import kotlin.math.abs

fun main() {
    val testInput = readInput("Day20_test")
    val input = readInput("Day20")

    check(part1(input) == 1490)
}

private fun part1(map: Grid): Int {
    val start = map.find('S')
    val end = map.find('E')

    val path = findPath(map, start, end)

    var count = 0

    for (i in 0..<path.lastIndex - 1) {
        for (j in i + 1..<path.size) {
            val c1 = path[i]
            val c2 = path[j]
            val distance = manhattanDistance(c1, c2)


            if (distance <= 2 && j - i - distance >= 100) {
                count++
            }
        }
    }

    return count
}

private fun findPath(map: Grid, start: Coordinate, end: Coordinate): List<Coordinate> {

    val visiting = mutableListOf<Coordinate>()

    val visited = mutableSetOf<Coordinate>()

    visiting += start

    while (visiting.isNotEmpty()) {
        val loc = visiting.removeFirst()

        if (loc == end) return visited.toList()

        if (loc in visited) continue

        visited += loc

        for (direction in Direction.entries) {
            val nextLoc = loc + direction.offset

            if (nextLoc.within(map) && nextLoc !in visited && map[nextLoc] != '#') {
                visiting += nextLoc
            }
        }
    }

    error("Solution not found :(")
}

private fun manhattanDistance(c1: Coordinate, c2: Coordinate): Int =
    abs(c1.first - c2.first) + abs(c1.second - c2.second)
