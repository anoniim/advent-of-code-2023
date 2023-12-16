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
        //
        return -1
    }
}