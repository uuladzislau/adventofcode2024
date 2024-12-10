fun main() {
    val testInput = readInput("Day09_test").first()
    val input = readInput("Day09").first()

    check(part1(testInput) == 1928L)
    check(part1(input) == 6349606724455)
}

private fun part1(input: String): Long {
    val blocks = buildBlocks(input)
    val remapped = remap(blocks)

    var checksum = 0L

    for (i in remapped.indices) {
        if (remapped[i] == null) break

        val blockId = remapped[i]!!

        checksum += i * blockId
    }

    return checksum
}

private fun buildBlocks(input: String): MutableList<Long?> {
    var id = 0L

    val blocks = mutableListOf<Long?>()

    input.forEachIndexed { i, digit ->
        val isFile = i % 2 == 0

        val value = digit.digitToInt()

        if (isFile) {
            for (j in 0 until value) {
                blocks.add(id)
            }
            id++
            return@forEachIndexed
        }

        for (j in 0 until value) {
            blocks.add(null)
        }
    }

    return blocks
}

private fun remap(blocks: MutableList<Long?>): MutableList<Long?> {
    var left = 0
    var right = blocks.size - 1

    while (left < right) {
        if (blocks[left] != null) {
            left++
            continue
        }

        if (blocks[right] == null) {
            right--
            continue
        }

        blocks[left] = blocks[right]
        blocks[right] = null

        left++
        right--
    }

    return blocks
}
