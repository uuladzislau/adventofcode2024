fun main() {
    val testInput = readInput("Day12_test")
    val input = readInput("Day12")

    check(part1(testInput) == 140)
    check(part1(input) == 1352976)
}

private fun part1(gardenPlot: Grid): Int {
    val regions = mutableMapOf<Coordinate, Int>()

    var regionId: Int = -1

    gardenPlot.forEachIndexed { x, row ->
        for ((y, plant) in row.withIndex()) {
            if (regions.contains(x to y)) continue

            val toVisit = mutableListOf(x to y)
            regionId++

            while (toVisit.isNotEmpty()) {
                val current = toVisit.removeFirst()
                if (gardenPlot[current] == plant) {
                    regions[current] = regionId

                    toVisit +=
                        directions
                            .map { it + current }
                            .filter { gardenPlot.withinBoundaries(it) && (it !in regions) && (it !in toVisit) }
                }
            }
        }
    }

    return (0..regionId).sumOf { id ->
        val coordinates = regions.filter { it.value == id }.keys
        val area = coordinates.size

        val perimeter = coordinates.sumOf { coordinate ->
            val neighboursCount = directions.map { it + coordinate }.count { it in coordinates }
            4 - neighboursCount
        }

        area * perimeter
    }
}

private val directions = setOf<Coordinate>(
    Coordinate(-1, 0),  // Up
    Coordinate(1, 0),   // Down
    Coordinate(0, -1),  // Left
    Coordinate(0, 1),   // Right
)

private typealias Grid = List<String>

private fun Grid.withinBoundaries(c: Coordinate): Boolean =
    (c.first >= 0 && c.second >= 0) && (c.first < this.size && c.second < this[0].length)

private operator fun Grid.get(c: Coordinate): Char = this[c.first][c.second]

private operator fun Coordinate.plus(other: Coordinate) =
    Coordinate(this.first + other.first, this.second + other.second)

private operator fun Coordinate.minus(other: Coordinate) =
    Coordinate(this.first - other.first, this.second - other.second)
