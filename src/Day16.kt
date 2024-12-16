fun main() {
    val testInput = readInput("Day16_test")
    val input = readInput("Day16")

    part1(testInput)
//    part1(input)
}

private fun part1(maze: Grid): Int {
    val start: Coordinate = maze.find('S')

    val deadEnds = mutableSetOf<Coordinate>()
    val visited = mutableSetOf<Coordinate>()

    val visiting = mutableListOf<Triple<Coordinate, Coordinate, Int>>()

    visiting.add(Triple(start, (0 to 1), 0))

    val solutions = mutableListOf<Int>()

    while (visiting.isNotEmpty()) {
        val (loc, prevDirection, score) = visiting.removeFirst()

        println("current score: $score")
        printMaze(maze, loc)

//        println("visiting: $loc")

        if (maze[loc] == 'E') {
            solutions.add(score)
            continue
        }

        if (maze[loc] == '#') {
            continue
        }

        if (visited.contains(loc)) {
            continue
        }

        val moves = arrayOf(
            plus90(prevDirection) to 1000,
            prevDirection to 1,
            minus90(prevDirection) to 1000,
        )

        for ((direction, price) in moves) {
            if (maze[loc + direction] == '#') continue

            visiting.add(Triple(loc + direction, direction, score + price))
        }

        visited.add(loc)
    }

    println("solutions: $solutions")

    return 0
}

private fun printMaze(maze:Grid, loc: Coordinate) {
    println(
        buildString {
            for (i in maze.indices) {
                for (j in maze[i].indices) {
                    if (loc == i to j) {
                        append('@')
                    } else {
                        append(maze[i to j])
                    }
                }
                append('\n')
            }
        }
    )
}

private fun Grid.find(c: Char): Coordinate {
    for (i in indices) {
        for (j in this[i].indices) {
            if (this[i][j] == c) {
                return i to j
            }
        }
    }
    throw IllegalStateException("Can't find '$c' :(")
}

private val directions = listOf(
    (0 to 1),  // EAST
    (1 to 0),  // SOUTH
    (0 to -1), // WEST
    (-1 to 0), // NORTH
)

private fun plus90(c: Coordinate): Coordinate {
    val direction = directions.indexOf(c)

    return directions[Math.floorMod(direction + 1, directions.size)]
}

private fun minus90(c: Coordinate): Coordinate {
    val direction = directions.indexOf(c)

    return directions[Math.floorMod(direction - 1, directions.size)]
}