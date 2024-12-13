import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val testInput = parseInput("Day13_test")
    val input = parseInput("Day13")

    check(part1(testInput) == 480)

    check(part1(input) == 36870)
}

private fun part1(machines: List<ClawMachine>) = machines.mapNotNull(ClawMachine::crack).sum()

private data class ClawMachine(val buttonA: Coordinate, val buttonB: Coordinate, val prize: Coordinate) {

    companion object {
        fun from(input: List<String>): ClawMachine {
            val regex = """X[+=](\d+), Y[+=](\d+)""".toRegex()

            val coordinates = input.flatMap {
                regex.findAll(it).map { match ->
                    Coordinate(match.groups[1]!!.value.toInt(), match.groups[2]!!.value.toInt())
                }
            }

            return ClawMachine(coordinates[0], coordinates[1], coordinates[2])
        }
    }

    /**
     * Find possible solutions to win a prize in the given claw machine.
     */
    fun crack(): Int? {
        val costs = buildSet {
            for (pressA in 100 downTo 1) {
                for (pressB in 100 downTo 1) {
                    val x = (buttonA.first * pressA) + (buttonB.first * pressB)
                    val y = (buttonA.second * pressA) + (buttonB.second * pressB)

                    if (x to y == prize) add(pressA * 3 + pressB)
                }
            }
        }

        if (costs.isEmpty()) {
            // there are no way to win this claw machine :(
            return null
        }

        return costs.minOf { it }
    }
}

private fun parseInput(fileName: String): List<ClawMachine> =
    Path("src/$fileName.txt")
        .readText()
        .trim()
        .split("\n\n")
        .map { it.lines() }
        .map(ClawMachine::from)
