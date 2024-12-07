fun main() {
    val input = readInput("Day07").map(::Expression)

    check(part1(input) == 2501605301465)
}

private fun part1(input: List<Expression>): Long = input.filter(Expression::resolvable).sumOf(Expression::target)

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
}


