fun main() {
    Day7().run(
        -1,
        -1
    )
}

private class Day7 : Day(7) {

    override fun part1(input: List<String>): Int {
        // Find the rank of every hand in your set. What are the total winnings?
        input.map(Hand::fromInput)
        return -1
    }

    override fun part2(input: List<String>): Int {
        //
        return -1
    }

    private class Hand(
        val cards: List<Card>,
        val bid: Int,
    ) {
        companion object {
            fun fromInput(input: String) : Hand {
                val split = input.split(" ")
                return Hand(split[0])
            }
        }
    }

    private enum class Card(
        val char: Char,
    ) {
        CARD_A('A'),
        CARD_K('K'),
        CARD_Q('Q'),
        CARD_J('J'),
        CARD_T('T'),
        CARD_9('9'),
        CARD_8('8'),
        CARD_7('7'),
        CARD_6('6'),
        CARD_5('5'),
        CARD_4('4'),
        CARD_3('3'),
        CARD_2('2'),
    }
}
