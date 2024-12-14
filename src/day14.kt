import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val testInput = parseInput("Day14_test", 7 to 11)
    val input = parseInput("Day14", 103 to 101)

    check(part1(testInput) == 12)
    check(part1(input) == 233709840)
}

private fun part1(robots: List<Robot>): Int {

    // number of robots per quadrant
    val localSafety = mutableMapOf(
        1 to 0,
        2 to 0,
        3 to 0,
        4 to 0
    )

    for (robot in robots) {
        robot.move(100)

        if (robot.inMiddle()) continue

        localSafety[robot.getQuadrant()] = localSafety.getValue(robot.getQuadrant()) + 1

    }

    return localSafety.values.reduce { acc, safetyIndex -> acc * safetyIndex }
}

private fun parseInput(fileName: String, max: Coordinate): List<Robot> {
    val input = Path("src/$fileName.txt")
        .readText()
        .trim()

    val regex = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()

    return regex.findAll(input)
        .map { match -> match.groupValues.slice(1..4).map { it.toInt() } }
        .map { Robot(it[1] to it [0], it[3] to it[2], max) }
        .toList()
}

private data class Robot(var pos: Coordinate, val vel: Coordinate, val max: Coordinate) {

    private val midI = max.first / 2
    private val midJ = max.second / 2

    fun move() {
        val (i, j) = pos + vel
        pos = Math.floorMod(i, max.first) to Math.floorMod(j, max.second)
    }

    fun move(times: Int) = repeat(times) { move() }

    fun inMiddle() = pos.first == midI || pos.second == midJ

    fun getQuadrant(): Int {
        if (pos.first in 0..<midI) {
            if (pos.second in 0..<midJ) {
                return 1
            }
            return 2
        }

        if (pos.second in 0..<midJ) {
            return 3
        }

        return 4
    }
}
