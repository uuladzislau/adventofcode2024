fun main() {
    val testInput = readInput("Day07_test").map(::Expression)
    val input = readInput("Day07").map(::Expression)

    check(part1(testInput) == 3749L)
    check(part1(input) == 2501605301465)

    check(part2(testInput) == 11387L)
    check(part2(input) == 44841372855953)
}

private fun part1(input: List<Expression>): Long = input.filter(Expression::resolvable).sumOf(Expression::target)

private fun part2(input: List<Expression>): Long = input.filter(Expression::resolvable2).sumOf(Expression::target)

private data class Expression(val target: Long, val operands: List<Long>) {
    constructor(input: String) : this(
        target = input.substringBefore(":").toLong(),
        operands = input.substringAfter(": ").split(" ").map { it.toLong() }
    )

    fun resolvable(): Boolean {
        val first = operands[0] + operands[1]
        val second = operands[0] * operands[1]

        if (operands.size == 2) {
            return target == first || target == second
        }

        val remaining = operands.drop(2)

        val e1 = Expression(target, listOf(first) + remaining)
        val e2 = Expression(target, listOf(second) + remaining)

        return e1.resolvable() || e2.resolvable()
    }

    fun resolvable2(): Boolean {
        val first = operands[0] + operands[1]
        val second = operands[0] * operands[1]
        val third = "${operands[0]}${operands[1]}".toLong()

        if (operands.size == 2) {
            return target == first || target == second || target == third
        }

        val remaining = operands.drop(2)

        val e1 = Expression(target, listOf(first) + remaining)
        val e2 = Expression(target, listOf(second) + remaining)
        val e3 = Expression(target, listOf(third) + remaining)

        return e1.resolvable2() || e2.resolvable2() || e3.resolvable2()
    }

}
