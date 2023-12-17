import kotlin.math.pow

fun main() {
    Day4().run(
        13,
        -1
    )
}

private class Day4 : Day(4) {

    override fun part1(input: List<String>): Int {
        // How many points are they worth in total?
        return input.map(Card::fromInput)
            .map(Card::points)
            .sum()
    }

    override fun part2(input: List<String>): Int {
        //
        return -1
    }
}

private class Card(
    val winningNumbers: List<Int>,
    val numbersYouHave: List<Int>,
) {
    fun points(): Int {
        val matchingNumberCount = numbersYouHave.count { winningNumbers.contains(it) }
        return 2.0.pow(matchingNumberCount - 1).toInt()
    }

    companion object {
        fun fromInput(input: String) : Card {
            val scratchCardRegex = Regex("""(\d+)""")
            val inputSplit = input.split(":")[1].split("|")
            val winningNumbers = scratchCardRegex.findAll(inputSplit[0]).toList().map(MatchResult::value).map(String::toInt)
            val numbersYouHave = scratchCardRegex.findAll(inputSplit[1]).toList().map(MatchResult::value).map(String::toInt)
            return Card(winningNumbers, numbersYouHave)
        }
    }
}