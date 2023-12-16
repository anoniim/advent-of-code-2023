import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

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

abstract class Day(private val dayNumber: Int) {
    abstract fun part1(input: List<String>): Int
    abstract fun part2(input: List<String>): Int

    fun run(expectedTestResultPart1: Int, expectedTestResultPart2: Int) {
        // test if implementation meets criteria from the description, like:
        val dayString = getDayString(dayNumber)
        val testInput1 = readInput("Day${dayString}_test")
        val testInput2 = try { readInput("Day${dayString}_test2") } catch(e: Exception) { testInput1 }
        checkTest(part1(testInput1), expectedTestResultPart1)
        checkTest(part2(testInput2), expectedTestResultPart2)

        val input = readInput("Day$dayString")
        println("=== DAY $dayNumber ===")
        println("Part 1 result: ${part1(input)}")
        println("Part 2 result: ${part2(input)}")
    }

    private fun checkTest(result: Int, expected: Int) {
        check(result == expected) { "Test check failed, wrong result: $result" }
    }
}

fun getDayString(dayNumber: Int): String {
    return if (dayNumber / 10 == 0) {
        "0$dayNumber"
    } else {
        dayNumber.toString()
    }
}