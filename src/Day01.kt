fun main() {
    Day1().run(
        142,
        281
    )
}

private class Day1 : Day(1) {

    override fun part1(input: List<String>): Int {
        // On each line, the calibration value can be found by combining the first digit and the last digit (in that order) to form a single two-digit number
        var sum = 0
        input.forEach {
            val firstDigit = it.find(Char::isDigit)
            val lastDigit = it.findLast(Char::isDigit)
            sum += "$firstDigit$lastDigit".toInt()
        }
        return sum
    }

    override fun part2(input: List<String>): Int {
        // It looks like some of the digits are actually spelled out with letters: one, two, three, four, five, six, seven, eight, and nine also count as valid "digits"
        var sum = 0
        input.forEach {
            val firstDigit = it.getFirstDigit()
            val lastDigit = it.getLastDigit()
            sum += "$firstDigit$lastDigit".toInt()
        }
        return sum
    }

    private fun String.getFirstDigit(): Int {
        val firstDigit = find(Char::isDigit)
        val beforeFirstDigit = if (firstDigit != null) split(firstDigit).first() else this
        val firstDigitText = beforeFirstDigit.findAnyOf(numbers.keys)?.second ?: ""
        return numbers[firstDigitText] ?: firstDigit?.digitToInt() ?: -1
    }

    private fun String.getLastDigit(): Int {
        val lastDigit = findLast(Char::isDigit)
        val afterLastDigit = if (lastDigit != null) split(lastDigit).last() else this
        val lastDigitText = afterLastDigit.findLastAnyOf(numbers.keys)?.second ?: ""
        return numbers[lastDigitText] ?: lastDigit?.digitToInt() ?: -1
    }

    val numbers = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
}