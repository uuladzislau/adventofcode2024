fun main() {
    val input = readInput("Day08")

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
        val antennas = mutableMapOf<Char, MutableList<Crd>>()

        val distinctLocations = mutableSetOf<Crd>()

        for (i in 0..mapEnd.first) {
            for (j in 0..mapEnd.second) {
                val current = Crd(i, j)
                val element = map.getValue(current)

                if (!element.isAntenna()) continue

                antennas[element]?.forEach { other ->
                    val distance = current - other

                    val (antinodeA, antinodeB) = (current + distance) to (other - distance)

                    if (!outOfBounds(antinodeA)) {
                        distinctLocations.add(antinodeA)
                    }

                    if (!outOfBounds(antinodeB)) {
                        distinctLocations.add(antinodeB)
                    }
                }

                antennas.computeIfAbsent(element) { mutableListOf() }.add(current)
            }
        }

        return distinctLocations.size
    }

    private fun outOfBounds(target: Crd): Boolean =
        (target.first < mapStart.first || target.second < mapStart.second)
                || (mapEnd.first < target.first || mapEnd.second < target.second)

    private fun Char.isAntenna(): Boolean = this != '.' && this != '#'

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
