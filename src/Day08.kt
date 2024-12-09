fun main() {
    val input = readInput("Day08")

    part1(input).println()
}

private fun part1(input: List<String>): Int {
    val (maxi, maxj) = input.size to input[0].length

    fun inBounds(target: Crd): Boolean =
        (target.first >= 0 && target.second >= 0) && (target.first < maxi && target.second < maxj)

    val antennas = mutableMapOf<Char, MutableList<Crd>>()

    val distinctLocations = mutableSetOf<Crd>()

    for (i in 0..<maxi) {
        for (j in 0..<maxj) {
            if (input[i][j] == '.') continue

            val current = input[i][j]

            val currentLoc = Crd(i, j)

            antennas[current]?.forEach { otherLoc ->
                // otherLoc - location of another previously discovered antenna of the same type
                val distance = currentLoc - otherLoc

                val (antinodeA, antinodeB) = (currentLoc + distance) to (otherLoc - distance)

                if (inBounds(antinodeA)) distinctLocations.add(antinodeA)

                if (inBounds(antinodeB)) distinctLocations.add(antinodeB)
            }

            antennas.computeIfAbsent(current) { mutableListOf() }.add(currentLoc)
        }
    }

    return distinctLocations.size
}

private typealias Crd = Pair<Int, Int>

private operator fun Crd.plus(other: Crd): Crd = Crd(this.first + other.first, this.second + other.second)

private operator fun Crd.minus(other: Crd): Crd = Crd(this.first - other.first, this.second - other.second)
