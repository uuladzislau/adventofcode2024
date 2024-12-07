import kotlin.io.path.Path
import kotlin.io.path.readText

fun main() {
    val input = readInput("Day06")

    check(Map(input).traverse() == 4559)
}

private class Map(input: List<String>) {

    private var map = mutableMapOf<Pair<Int, Int>, Char>()
    private var currentPos = Pair(-1, -1)
    private var direction = Direction.UP

    private val boundaries = Pair(
        first = input.size - 1,
        second = input[input.size - 1].length - 1
    )

    private var visited = 1

    init {
        for (i in input.indices) {
            for (j in input[i].indices) {
                map[Pair(i, j)] = input[i][j]

                if (input[i][j] == '^') {
                    currentPos = Pair(i, j)
                }
            }
        }

        assert(currentPos == Pair(-1, -1), { "Start not found" })
    }

    private enum class Direction(val offset: Pair<Int, Int>) {
        UP(Pair(-1, 0)),
        DOWN(Pair(1, 0)),
        LEFT(Pair(0, -1)),
        RIGHT(Pair(0, 1)),
    }

    fun traverse(): Int {
        while (nextMove()) {

        }
        return visited
    }

    fun nextMove(): Boolean {
        val nextPos = getNextPos()

        if (isOutOfBounds(nextPos)) {
            return false
        }

        when (map[nextPos]) {
            '#' -> turnRight()
            'X', '.' -> moveTo(nextPos)
        }

        map[currentPos] = when (direction) {
            Direction.UP -> '^'
            Direction.RIGHT -> '>'
            Direction.DOWN -> 'V'
            Direction.LEFT -> '<'
        }

        return true
    }

    private fun getNextPos(): Pair<Int, Int> = Pair(
        first = currentPos.first + direction.offset.first,
        second = currentPos.second + direction.offset.second
    )

    private fun isOutOfBounds(pos: Pair<Int, Int>): Boolean {
        return (pos.first < 0 || pos.second < 0)
                || (boundaries.first < pos.first || boundaries.second < pos.second)
    }

    private fun moveTo(pos: Pair<Int, Int>) {
        map[currentPos] = 'X'
        if (map[pos] == '.') visited++
        currentPos = pos
    }

    private fun turnRight() {
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }
    }

    @Suppress("unused")
    fun printMap() {
        for (i in 0..boundaries.first) {
            for (j in 0..boundaries.second) {
                print(map[Pair(i, j)])
            }
            print("\n")
        }
        print("\n")
    }
}
