fun main() {
    val input = readInput("Day06")

    check(Map(input).traverse() == 4559)
}

private class Map(input: List<String>) {

    private var map = mutableMapOf<Coordinate, Char>()
    private var currentPos = Coordinate(-1, -1)
    private var direction = Direction.UP

    private val boundaries = Pair(
        first = input.size - 1,
        second = input[input.size - 1].length - 1
    )

    private var visited = 1

    init {
        for (i in input.indices) {
            for (j in input[i].indices) {
                map[i to j] = input[i][j]

                if (input[i][j] == '^') {
                    currentPos = i to j
                }
            }
        }

        assert(currentPos == -1 to -1, { "Start not found" })
    }

    private enum class Direction(val offset: Coordinate) {
        UP(-1 to 0),
        DOWN(1 to 0),
        LEFT(0 to -1),
        RIGHT(0 to 1),
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

    private fun getNextPos() = Coordinate(
        first = currentPos.first + direction.offset.first,
        second = currentPos.second + direction.offset.second
    )

    private fun isOutOfBounds(pos: Coordinate): Boolean {
        return (pos.first < 0 || pos.second < 0)
                || (boundaries.first < pos.first || boundaries.second < pos.second)
    }

    private fun moveTo(pos: Coordinate) {
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
                print(map[Coordinate(i, j)])
            }
            print("\n")
        }
        print("\n")
    }
}
