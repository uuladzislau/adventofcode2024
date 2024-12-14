fun main() {
    val input = readInput("Day08")

    part1(input).println()
    part2(input).println()
}

private fun part1(input: List<String>): Int {
    val (maxi, maxj) = input.size to input[0].length

    fun inBounds(target: Coordinate): Boolean =
        (target.first >= 0 && target.second >= 0) && (target.first < maxi && target.second < maxj)

    val antennas = mutableMapOf<Char, MutableList<Coordinate>>()

    val distinctLocations = mutableSetOf<Coordinate>()

    for (i in 0..<maxi) {
        for (j in 0..<maxj) {
            if (input[i][j] == '.') continue

            val current = input[i][j]

            val currentLoc = Coordinate(i, j)

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

private fun part2(input: List<String>): Int {
    val (maxi, maxj) = input.size to input[0].length

    fun inBounds(target: Coordinate): Boolean =
        (target.first >= 0 && target.second >= 0) && (target.first < maxi && target.second < maxj)

    val antennas = mutableMapOf<Char, MutableList<Coordinate>>()

    val distinctLocations = mutableSetOf<Coordinate>()

    for (i in 0..<maxi) {
        for (j in 0..<maxj) {
            if (input[i][j] == '.') continue

            val current = input[i][j]

            val currentLoc = Coordinate(i, j)

            antennas[current]?.forEach { otherLoc ->
                // otherLoc - location of another previously discovered antenna of the same type
                val distance = currentLoc - otherLoc

                var antinodeA = currentLoc + distance
                var antinodeB = otherLoc - distance

                distinctLocations.add(currentLoc)
                distinctLocations.add(otherLoc)

                // keep creating antinodes while they're within map's boundaries
                while (inBounds(antinodeA) || inBounds(antinodeB)) {
                    if (inBounds(antinodeA)) distinctLocations.add(antinodeA)
                    if (inBounds(antinodeB)) distinctLocations.add(antinodeB)

                    antinodeA += distance
                    antinodeB -= distance
                }
            }

            antennas.computeIfAbsent(current) { mutableListOf() }.add(currentLoc)
        }
    }

    return distinctLocations.size
}
