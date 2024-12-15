import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val testInput = parseInput("Day15_test")
    val input = parseInput("Day15")

    check(part1(testInput) == 10092)
    check(part1(input) == 1465523)
}

private fun part1(warehouse: Warehouse): Int {
    warehouse.letRobotMove()

    return warehouse.map.filterValues { it == 'O' }.keys.sumOf { it.first * 100 + it.second }
}

private fun parseInput(fileName: String): Warehouse {
    val (rawWarehouseMap, rawVelocities) = Path("src/$fileName.txt").readText().trim().split("\n\n")

    val map: WarehouseMap = buildMap {
        rawWarehouseMap.lines()
            .flatMapIndexed { i, line ->
                line.mapIndexed { j, el -> put(i to j, el) }
            }
    }.toMutableMap()

    val velocities = rawVelocities.lines()
        .flatMap { line ->
            line.map { token ->
                when (token) {
                    '<' -> Coordinate(0, -1)
                    '>' -> Coordinate(0, 1)
                    '^' -> Coordinate(-1, 0)
                    'v' -> Coordinate(1, 0)
                    else -> throw IllegalStateException("Unexpected token $token")
                }
            }
        }

    return Warehouse(map, velocities)
}

private data class Warehouse(val map: WarehouseMap, val directions: List<Coordinate>) {
    var loc: Coordinate = findStart()

    fun letRobotMove() {
        directions.forEach { direction ->
            val newLoc = loc + direction

            if (map.isWall(newLoc)) return@forEach

            // if there is a box, then try to move it first...

            if (map.isBox(newLoc)) {
                moveBox(newLoc, direction)
            }

            // and only then try to move robot itself

            if (map.isEmptySpace(newLoc)) {
                map[loc] = '.'
                map[newLoc] = '@'
                loc = newLoc
            }
        }
    }

    private fun findStart(): Coordinate {
        map.forEach { (coordinate, el) ->
            if (el == '@') return coordinate
        }
        throw IllegalStateException("Start not found")
    }

    private fun moveBox(boxLoc: Coordinate, direction: Coordinate) {
        val newBoxLoc = boxLoc + direction

        if (map.isWall(newBoxLoc)) {
            // we can't move the box :(
            return
        }

        if (map.isBox(newBoxLoc)) {
            moveBox(newBoxLoc, direction)
        }

        if (map.isEmptySpace(newBoxLoc)) {
            map[newBoxLoc] = 'O'
            map[boxLoc] = '.'
            return
        }

        // box is still there, can't move :(
    }
}

private typealias WarehouseMap = MutableMap<Coordinate, Char>

private fun WarehouseMap.isWall(c: Coordinate) = this[c] == '#'

private fun WarehouseMap.isEmptySpace(c: Coordinate) = this[c] == '.'

private fun WarehouseMap.isBox(c: Coordinate) = this[c] == 'O'
