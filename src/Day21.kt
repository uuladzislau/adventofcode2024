import java.util.*

fun main() {
    val testInput = readInput("Day21_test")

    part1(testInput)
}

private fun part1(input: List<String>): Int {

    numericInputs.println()

    resolveInputs("029A")
//    resolveInputs("980A")
//    resolveInputs("179A")
//    resolveInputs("456A")
//    resolveInputs("379A")

    return 0
}

private fun resolveInputs(code: String): String {
    val numericKeypadInput = foo(code, numericInputs2)

    println("$code: $numericKeypadInput")

//    bar(code)

    val directionalKeypadInput1 = foo(numericKeypadInput, directionalInputs2)

    println("$code: $directionalKeypadInput1")

    val directionalKeypadInput2 = foo(directionalKeypadInput1, directionalInputs2)

    println("$code: $directionalKeypadInput2")

    // 1. <A^A>^^AvvvA, <A^A^>^AvvvA, <A^A^^>AvvvA.

    // my:
    // <vA<AA>>^AvAA<^A>Av<<A>>^AvA^A<vA>^Av<<A>^A>AAvA^Av<<A>A>^AAAvA<^A>A
    // target:
    // <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A

    return directionalKeypadInput2
}

fun foo(source: String, inputs: Map<Char, Map<Char, String>>): String = buildString {
    var current = 'A'

    for (char in source) {

        append(inputs[current]?.get(char))
//        println("$current to $char: ${inputs[current]?.get(char)}")
        append('A')
        current = char
    }
}

fun bar(source: String) {
    var current = 'A'

    for (char in source) {

        val test = numericInputs2[current]!!.getValue(char)

        var current2 = 'A'

        for (c1 in test) {

            val test2 = directionalInputs2[current2]?.get(c1)



            println("$char -> $test")

            current2 = c1
        }

//        println("$current to $char: $test")

        current = char
    }
}

private val numericKeypad: CharGrid = listOf(
    listOf('7', '8', '9'),
    listOf('4', '5', '6'),
    listOf('1', '2', '3'),
    listOf(null, '0', 'A'),
)

private val numericKeypadAdjacency = buildAdjacencyMatrix(numericKeypad)

private val numericInputs =
    numericKeypadAdjacency.mapValues { findInputs(numericKeypad, numericKeypadAdjacency, it.key) }

private val directionalKeypad: CharGrid = listOf(
    listOf(null, '^', 'A'),
    listOf('<', 'v', '>'),
)

private val directionalKeypadAdjacency = buildAdjacencyMatrix(directionalKeypad)

private val directionalInputs =
    directionalKeypadAdjacency.mapValues { findInputs(directionalKeypad, directionalKeypadAdjacency, it.key) }

private val numericInputs2 = mapOf(
    'A' to mapOf(
        'A' to "",
        '0' to "<",
        '1' to "^<<",
        '2' to "^<",
        '3' to "^",
        '4' to "^^<<",
        '5' to "^^<",
        '6' to "^^",
        '7' to "^^^<<",
        '8' to "^^^<",
        '9' to "^^^",
    ),
    '0' to mapOf(
        'A' to ">",
        '0' to "",
        '1' to "^<",
        '2' to "^",
        '3' to "^>",
        '4' to "^^<",
        '5' to "^^",
        '6' to "^^<",
        '7' to "^^^<",
        '8' to "^^^",
        '9' to "^^^>",
    ),
    '1' to mapOf(
        '0' to ">v",
        'A' to ">>v",
        '1' to "",
        '2' to ">",
        '3' to ">>",
        '4' to "^",
        '5' to "^>",
        '6' to "^>>",
        '7' to "^^",
        '8' to "^^>",
        '9' to "^^>>",
    ),
    '2' to mapOf(
        'A' to ">v",
        '0' to "v",
        '1' to "<",
        '2' to "",
        '3' to ">",
        '4' to "^<",
        '5' to "^",
        '6' to "^>",
        '7' to "^^<",
        '8' to "^^",
        '9' to ">^^",
    ),
    '3' to mapOf(
        '3' to "",
        '6' to "^",
        'A' to "v",
        '2' to "<",
        '9' to "^^",
        '5' to "^<",
        '0' to "<v",
        '1' to "<<",
        '4' to "<<^",
        '8' to "^^<",
        '7' to "^^<<",
    ),
    '4' to mapOf(
        '4' to "",
        '7' to "^",
        '1' to "v",
        '5' to ">",
        '8' to "^>",
        '2' to ">v",
        '6' to ">>",
        '9' to ">^",
        '3' to ">>v",
        '0' to ">vv",
        'A' to ">>vv",
    ),
    '5' to mapOf(
        '5' to "",
        '8' to "^",
        '2' to "v",
        '4' to "<",
        '6' to ">",
        '7' to "^<",
        '9' to "^>",
        '3' to ">v",
        '0' to "vv",
        '1' to "v<",
        'A' to "vv>",
    ),
    '6' to mapOf(
        '6' to "",
        '9' to "^",
        '3' to "v",
        '5' to "<",
        '8' to "^<",
        '2' to "<v",
        '4' to "<<",
        'A' to "vv",
        '7' to "<<^",
        '1' to "<<v",
        '0' to "vv<",
    ),
    '7' to mapOf(
        '7' to "",
        '4' to "v",
        '8' to ">",
        '1' to "vv",
        '5' to ">",
        '9' to ">>",
        '2' to "vv>",
        '6' to "v>>",
        '0' to ">vvv",
        '3' to ">>vv",
        'A' to ">>vvv",
    ),
    '8' to mapOf(
        '8' to "",
        '5' to "v",
        '7' to "<",
        '9' to ">",
        '2' to "v",
        '4' to "<",
        '6' to "v>",
        '1' to "vv<",
        '0' to "vvv",
        '3' to "vv>",
        'A' to "vvv>",
    ),
    '9' to mapOf(
        '9' to "",
        '6' to "v",
        '8' to "<",
        '3' to "vv",
        '5' to "v<",
        '7' to "<<",
        '2' to "<vv",
        '4' to "v<<",
        'A' to "vvv",
        '0' to "<vvv",
        '1' to "vv<<",
    ),
)

private val directionalInputs2 = mapOf(
    '^' to mapOf('^' to "", 'v' to "v", 'A' to ">", '<' to "v<", '>' to "v>"),
    'A' to mapOf('A' to "", '>' to "v", '^' to "<", 'v' to "<v", '<' to "v<<"),
    '<' to mapOf('<' to "", 'v' to ">", '^' to ">^", '>' to ">>", 'A' to ">>^"),
    'v' to mapOf('v' to "", '^' to "^", '<' to "<", '>' to ">", 'A' to ">^"),
    '>' to mapOf('>' to "", 'A' to "^", 'v' to "<", '^' to "<^", '<' to "<<"),
)

private fun buildAdjacencyMatrix(grid: CharGrid) = grid.indices.flatMap { i ->
    grid[i].indices.map { j ->
        val pos = i to j

        if (grid[pos] == null) return@map null

        val neighbours = Direction.entries.asSequence()
            .map { pos + it.offset }
            .filter { it.first in grid.indices && it.second in grid[it.first].indices }
            .map { grid[it] }
            .filterNotNull()
            .toSet()

        grid[pos]!! to neighbours
    }
}.filterNotNull().toMap()

private typealias CharGrid = List<List<Char?>>

private operator fun CharGrid.get(c: Coordinate): Char? = this[c.first][c.second]

private fun CharGrid.find(c: Char): Coordinate {
    for (i in indices) {
        for (j in this[i].indices) {
            if (this[i][j] == c) {
                return i to j
            }
        }
    }
    error("Can't find '$c' :(")
}

private fun findInputs(grid: CharGrid, graph: Map<Char, Set<Char>>, start: Char): Map<Char, String> {
    val distances = mutableMapOf<Char, String>().withDefault { "X".repeat(10000) }
    val priorityQueue = PriorityQueue<Pair<Char, String>>(compareBy { it.second.length }).apply { add(start to "") }

    distances[start] = ""

    while (priorityQueue.isNotEmpty()) {
        val (node, path) = priorityQueue.poll()

        val pos = grid.find(node)

        graph[node]?.forEach { adjacent ->
            val adjacentPos = grid.find(adjacent)

            val totalDist = path.length + 1
            if (totalDist < distances.getValue(adjacent).length) {
                distances[adjacent] = path + getDirection(pos, adjacentPos)
                priorityQueue.add(adjacent to path + getDirection(pos, adjacentPos))
            }
        }
    }

    return distances
}

private fun getDirection(from: Coordinate, to: Coordinate): Char = when (from - to) {
    0 to 1 -> '<'
    0 to -1 -> '>'
    1 to 0 -> '^'
    -1 to 0 -> 'v'
    else -> error("Can't resolve direction from $from")
}