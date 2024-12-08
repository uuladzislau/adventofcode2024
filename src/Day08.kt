import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = readInput("Day08_test")

    AntennasMap(input).traverse().println()
}

private class AntennasMap(input: List<String>) : Iterable<Crd> {

    private val map = mutableMapOf<Crd, Char>()

    private val mapStart = Crd(0, 0)

    private val mapEnd = Crd(
        i = input.size - 1,
        j = input[input.size - 1].length - 1
    )

    init {
        for (i in input.indices) {
            for (j in input[i].indices) {
                map[Crd(i, j)] = input[i][j]
            }
        }
    }

    fun traverse(): Int {
        forEach {
            val el = this[it]
            if (el.isAntenna()) {
                // println("Antenna found at $it")
                findAntinodes(it)
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
            val from = Crd(max(start.i - offset, 0), max(start.j - offset, 0))
            // bottom right corner
            val to = Crd(min(start.i + offset, mapEnd.i), min(start.j + offset, mapEnd.j))

            // println("Searching in range $from -> $to")

            for (i in from.i..to.i) {
                // println("Checking ($i, ${from.j}) --> ${get(i, from.j)}")
                if (this[i, from.j].isAntenna(antenna)) {
                    insertAntinode(start, Crd(i, from.j))
                }

                if (this[i, to.j].isAntenna(antenna)) {
                    insertAntinode(start, Crd(i, to.j))
                }
            }

            for (j in from.j..to.j) {
                if (this[from.i, j].isAntenna(antenna)) {
                    insertAntinode(start, Crd(from.i, j))
                }
                if (this[to.i, j].isAntenna(antenna)) {
                    insertAntinode(start, Crd(to.i, j))
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
        (target.i < mapStart.i || target.j < mapStart.j)
                || (mapEnd.i < target.i || mapEnd.j < target.j)

    private fun Char.isAntenna(): Boolean = this != '.' && this != '#'

    private fun Char.isAntenna(antennaType: Char): Boolean = this != '.' && this != '#' && this == antennaType

    operator fun get(c: Crd) = map.getValue(c)

    operator fun get(x: Int, y: Int) = get(Crd(x, y))

    override fun iterator(): Iterator<Crd> = traverseArea(Crd(0, 0), mapEnd)

    private fun traverseArea(from: Crd, to: Crd): Iterator<Crd> = iterator {
        for (i in from.i..to.i) {
            for (j in from.j..to.j) {
                yield(Crd(i, j))
            }
        }
    }


    override fun toString(): String {
        return buildString {
            for (i in mapStart.i..mapEnd.i) {
                for (j in mapStart.j..mapEnd.j) {
                    append(map[Crd(i, j)])
                }
                append("\n")
            }
        }
    }
}


/**
 * Helper class representing a 2D point.
 */
private data class Crd(val i: Int, val j: Int) {
    operator fun plus(other: Crd): Crd = Crd(i = this.i + other.i, j = this.j + other.j)
    operator fun minus(other: Crd): Crd = Crd(i = this.i - other.i, j = this.j - other.j)
}
