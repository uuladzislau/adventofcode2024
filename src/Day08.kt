import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("Day08_test")

    AntennasMap(input).traverse().println()
}

private class AntennasMap(input: List<String>) {

    private val map = mutableMapOf<Crd, Char>()

    private val mapStart = Crd(0, 0)

    private val mapEnd = Crd(
        first = input.size - 1,
        second = input[0].length - 1
    )

    init {
        for (i in input.indices) {
            for (j in input[i].indices) {
                map[Crd(i, j)] = input[i][j]
            }
        }
    }

    fun traverse(): Int {
        for (i in 0..mapEnd.first) {
            for (j in 0..mapEnd.second) {
                val coordinate = Crd(i, j)
                val element = map.getValue(coordinate)

                if (!element.isAntenna()) continue

                findAntinodes(coordinate)
            }
        }
        println()
        antennasMap.keys.size.println()
        return antinodesInserted
    }

    private fun findAntinodes(start: Crd) {
        val antenna = this[start]

        // println("Looking for antinode '$antenna' starting from $start")

        var keepSearching = true

        var offset = 1

        while (keepSearching) {
            // top left corner
            val from = Crd(max(start.first - offset, 0), max(start.second - offset, 0))
            // bottom right corner
            val to = Crd(min(start.first + offset, mapEnd.second), min(start.first + offset, mapEnd.second))

            // println("Searching in range $from -> $to")

            for (i in from.first..to.second) {
                // println("Checking ($i, ${from.j}) --> ${get(i, from.j)}")
                if (this[i, from.second].isAntenna(antenna)) {
                    insertAntinode(start, Crd(i, from.second))
                }

                if (this[i, to.second].isAntenna(antenna)) {
                    insertAntinode(start, Crd(i, to.second))
                }
            }

            for (j in from.second..to.second) {
                if (this[from.first, j].isAntenna(antenna)) {
                    insertAntinode(start, Crd(from.first, j))
                }
                if (this[to.first, j].isAntenna(antenna)) {
                    insertAntinode(start, Crd(to.first, j))
                }
            }

            if (from == Crd(0, 0) && to == mapEnd) {
                // println("Reached boundaries of the map; stop searching")
                keepSearching = false
            }

            offset++
        }

    }

    var antinodesInserted = 0

    private fun insertAntinode(a: Crd, b: Crd) {
        val target = a + (a - b)

        if (outOfBounds(target)) {
            // println("Can't insert antinode at $target: coordinate is out of bounds")
            return
        }

        // println("inserting antinode for $a and $b at ${a + (a - b)}")

        antennasMap.getOrPut(target, { mutableSetOf() }).add(Pair(a, b))

        if (map[target] == '.') {
            map[target] = '#'
            antinodesInserted++
        }
    }

    private val antennasMap = mutableMapOf<Crd, MutableSet<Pair<Crd, Crd>>>()


    private fun outOfBounds(target: Crd): Boolean =
        (target.first < mapStart.first || target.second < mapStart.second)
                || (mapEnd.first < target.first || mapEnd.second < target.second)

    private fun Char.isAntenna(): Boolean = this != '.' && this != '#'

    private fun Char.isAntenna(antennaType: Char): Boolean = this != '.' && this != '#' && this == antennaType

    operator fun get(c: Crd) = map.getValue(c)

    operator fun get(x: Int, y: Int) = get(Crd(x, y))

    override fun toString(): String {
        return buildString {
            for (i in mapStart.first..mapEnd.first) {
                for (j in mapStart.second..mapEnd.second) {
                    append(map[Crd(i, j)])
                }
                append("\n")
            }
        }
    }
}

private typealias Crd = Pair<Int, Int>

private operator fun Crd.plus(other: Crd): Crd = Crd(this.first + other.first, this.second + other.second)
private operator fun Crd.minus(other: Crd): Crd = Crd(this.first - other.first, this.second - other.second)

