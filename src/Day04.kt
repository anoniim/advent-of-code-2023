import kotlin.math.pow

fun main() {
    Day4().run(
        13,
        30
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
        // Including the original set of scratchcards, how many total scratchcards do you end up with?
        // FIXME slow but works
        val cards = input.map(Card::fromInput)
        val cardMap = mutableMapOf<Int, Int>()
        cards.forEach { card ->
            val previousCopies = cardMap[card.id] ?: 0
            cardMap[card.id] = previousCopies + 1
            repeat(cardMap[card.id] ?: 1) {
                repeat(card.matchingNumberCount()) {
                    val followingCardId = card.id + it + 1
                    val followingCopies = cardMap[followingCardId] ?: 0
                    cardMap[followingCardId] = followingCopies + 1
                }
            }
        }
        return cardMap.values.sum()
    }
}

private class Card(
    val id: Int,
    val winningNumbers: List<Int>,
    val numbersYouHave: List<Int>,
) {
    fun points(): Int {
        val matchingNumberCount = matchingNumberCount()
        return 2.0.pow(matchingNumberCount - 1).toInt()
    }

    fun matchingNumberCount() : Int {
        return numbersYouHave.count { winningNumbers.contains(it) }
    }

    companion object {
        fun fromInput(input: String): Card {
            val numberRegex = Regex("""(\d+)""")
            val inputSplit = input.split(":")
            val cardId = numberRegex.find(inputSplit[0])!!.value.toInt()
            val numberSplit = inputSplit[1].split("|")
            val winningNumbers = numberRegex.findAll(numberSplit[0]).toList().map(MatchResult::value).map(String::toInt)
            val numbersYouHave = numberRegex.findAll(numberSplit[1]).toList().map(MatchResult::value).map(String::toInt)
            return Card(cardId, winningNumbers, numbersYouHave)
        }
    }
}