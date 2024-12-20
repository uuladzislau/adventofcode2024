import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

fun readRawInput(name: String) = Path("src/$name.txt").readText().trim()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Utility type representing a 2D point on a grid.
 */
typealias Coordinate = Pair<Int, Int>

fun Coordinate.within(grid: Grid): Boolean =
    (this.first >= 0 && this.second >= 0) && (this.first < grid.size && this.second < grid[0].length)

operator fun Coordinate.plus(other: Coordinate) = Coordinate(this.first + other.first, this.second + other.second)

operator fun Coordinate.minus(other: Coordinate) = Coordinate(this.first - other.first, this.second - other.second)

/**
 * Utility type representing a 2D grid.
 */
typealias Grid = List<String>

operator fun Grid.get(c: Coordinate): Char = this[c.first][c.second]

fun Grid.find(c: Char): Coordinate {
    for (i in indices) {
        for (j in this[i].indices) {
            if (this[i][j] == c) {
                return i to j
            }
        }
    }
    throw IllegalStateException("Can't find '$c' :(")
}

enum class Direction(val offset: Coordinate) {
    UP(-1 to 0),
    DOWN(1 to 0),
    LEFT(0 to -1),
    RIGHT(0 to 1);

    fun plus90(): Direction = when (this) {
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
        UP -> RIGHT
    }

    fun minus90(): Direction = plus90().plus90().plus90()
}
