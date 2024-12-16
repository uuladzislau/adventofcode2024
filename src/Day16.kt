fun main() {
    val testInput = readInput("Day16_test")
    val input = readInput("Day16")

    check(part1(testInput) == 11048)

    part1(input)

    // 98356 is too high
}

private data class Context(val direction: Coordinate, val visited: Set<Coordinate>, val scores: List<Int>)

private fun part1(maze: Grid): Int {
    val start: Coordinate = maze.find('S')

    val visiting = mutableListOf<Pair<Coordinate, Context>>()

    val visited = mutableSetOf<Coordinate>()

    visiting.add(start to Context((0 to 1), emptySet(), emptyList()))

    val solutions = mutableListOf<Int>()

    // printMaze(maze, emptySet())

    while (visiting.isNotEmpty()) {
        val (loc, prev) = visiting.removeFirst()

        if (loc in visited) {
            continue
        }

        if (maze[loc] == 'E') {
            solutions.add(prev.scores.sum())
            continue
        }

        if (maze[loc] == '#') {
            continue
        }

        val moves = arrayOf(
            prev.direction.plus90() to 1001,
            prev.direction to 1,
            prev.direction.minus90() to 1001,
        )

        for ((direction, price) in moves) {
            val destination = loc + direction

            if (maze[destination] == '#') continue

            val context = Context(direction, prev.visited + loc, prev.scores + price)

            visiting.add(destination to context)
        }

        visited.add(loc)
    }

    println("solutions: ${solutions.min()}")

    return solutions.min()
}

private fun printMaze(maze: Grid, visited: Set<Coordinate>) {
    println(
        buildString {
            for (i in maze.indices) {
                for (j in maze[i].indices) {
                    if (i to j in visited) {
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

private fun Coordinate.plus90(): Coordinate = when (this) {
    0 to 1 -> 1 to 0
    1 to 0 -> 0 to -1
    0 to -1 -> -1 to 0
    -1 to 0 -> 0 to 1
    else -> error("Can't figure out how to turn :(")
}

private fun Coordinate.minus90(): Coordinate = plus90().plus90().plus90()
