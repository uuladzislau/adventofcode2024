import kotlin.math.pow

fun main() {
    val testInput = parseInput("Day17_test")
    val input = parseInput("Day17")

    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
    check(part1(input) == "2,1,0,1,7,2,5,0,3")
}

private fun part1(computer: Computer): String {
    val (registers, instructions) = computer

    var (a, b, c) = registers
    var instructionPointer = 0

    fun combo(operand: Int): Int = when (operand) {
        0, 1, 2, 3 -> operand
        4 -> a
        5 -> b
        6 -> c
        else -> error("Operand '$operand' is reserved and should not appear in valid programs")
    }

    val out = mutableListOf<Int>()

    while (instructionPointer < instructions.size) {
        val instruction = instructions[instructionPointer]
        val operand = instructions[instructionPointer + 1]

        when (instruction) {
            0 -> a = (a / 2.0.pow(combo(operand))).toInt()
            1 -> b = b.xor(operand)
            2 -> b = combo(operand) % 8
            3 -> instructionPointer = if (a == 0) instructionPointer + 2 else operand
            4 -> b = b.xor(c)
            5 -> out += combo(operand) % 8
            6 -> b = (a / 2.0.pow(combo(operand))).toInt()
            7 -> c = (a / 2.0.pow(combo(operand))).toInt()
            else -> error("Unknown instruction code $instruction")
        }

        // Instruction '3' (jnz) is changing the instruction pointer,
        // so in this case we are not increasing it here.
        if (instruction != 3) {
            instructionPointer += 2
        }
    }

    return out.joinToString(separator = ",")
}

private fun parseInput(fileName: String): Computer {
    // read input
    val input = readInput(fileName)

    // parse registers
    val registers = (0..2).map { input[it].substringAfter(": ").toInt() }.let { (a, b, c) -> Triple(a, b, c) }
    // parse instructions
    val instructions = input[4].substringAfter(": ").split(",").map(String::toInt)

    return registers to instructions
}

private typealias Computer = Pair<Registers, List<Int>>

private typealias Registers = Triple<Int, Int, Int>
