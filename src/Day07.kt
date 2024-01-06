fun main() {
    Day7().run(
        6440,
        5905
    )
}

private class Day7 : Day(7) {

    override fun part1(input: List<String>): Int {
        // Find the rank of every hand in your set. What are the total winnings?
        return input.map(Hand::fromInput)
            .sorted()
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
    }

    override fun part2(input: List<String>): Int {
        // Using the new joker rule, find the rank of every hand in your set. What are the new total winnings?
        return -1
    }

    private class Hand(
        val cards: List<Card>,
        val bid: Int,
    ) : Comparable<Hand> {

        val handType = when {
            cards.isFiveOfAKind() -> HandType.FIVE_OF_A_KIND
            cards.isFourOfAKind() -> HandType.FOUR_OF_A_KIND
            cards.isFullHouse() -> HandType.FULL_HOUSE
            cards.isThreeOfAKind() -> HandType.THREE_OF_A_KIND
            cards.isTwoPairs() -> HandType.TWO_PAIRS
            cards.isOnePair() -> HandType.ONE_PAIR
            else -> HandType.HIGH_CARD
        }

        private fun List<Card>.isFiveOfAKind(): Boolean {
            return all { it == get(0) }
        }

        private fun List<Card>.isFourOfAKind(): Boolean {
            val sameAsFirst = getSameAsFirst()
            if (sameAsFirst.size == 4) return true
            val sameAsLast = getSameAsLast()
            return sameAsLast.size == 4
        }

        private fun List<Card>.isFullHouse(): Boolean {
            val sameAsFirst = getSameAsFirst()
            if (sameAsFirst.size == 3) return isTheRestPair(get(0))
            val sameAsSecond = getSameAsSecond()
            if (sameAsSecond.size == 3) return isTheRestPair(get(1))
            val sameAsLast = getSameAsLast()
            return sameAsLast.size == 3 && isTheRestPair(get(4))
        }

        private fun List<Card>.isTheRestPair(card: Card): Boolean {
            val rest = filter { it != card }
            return rest[0] == rest[1]
        }

        private fun List<Card>.isThreeOfAKind(): Boolean {
            val sameAsFirst = getSameAsFirst()
            if (sameAsFirst.size == 3) return true
            val sameAsSecond = getSameAsSecond()
            if (sameAsSecond.size == 3) return true
            val sameAsLast = getSameAsLast()
            return sameAsLast.size == 3
        }

        private fun List<Card>.isTwoPairs(): Boolean {
            val sameAsFirst = getSameAsFirst()
            if (sameAsFirst.size == 2) return hasTheRestPair(get(0))
            val sameAsSecond = getSameAsSecond()
            if (sameAsSecond.size == 2) return hasTheRestPair(get(1))
            val sameAsThird = getSameAsThird()
            if (sameAsThird.size == 2) return hasTheRestPair(get(3))
            val sameAsLast = getSameAsLast()
            return sameAsLast.size == 2 && hasTheRestPair(get(4))
        }

        private fun List<Card>.hasTheRestPair(card: Card): Boolean {
            val rest = filter { it != card }
            val sameAsFirst = rest.getSameAsFirst()
            if (sameAsFirst.size == 2) return true
            val sameAsSecond = rest.getSameAsSecond()
            return sameAsSecond.size == 2
        }

        private fun List<Card>.isOnePair(): Boolean {
            val sameAsFirst = getSameAsFirst()
            if (sameAsFirst.size == 2) return true
            val sameAsSecond = getSameAsSecond()
            if (sameAsSecond.size == 2) return true
            val sameAsThird = getSameAsThird()
            if (sameAsThird.size == 2) return true
            val sameAsLast = getSameAsLast()
            return sameAsLast.size == 2
        }

        private fun List<Card>.getSameAsLast() = filter { it == get(4) } // TODO simplify using repeat()

        private fun List<Card>.getSameAsThird() = filter { it == get(2) }

        private fun List<Card>.getSameAsSecond() = filter { it == get(1) }

        private fun List<Card>.getSameAsFirst() = filter { it == get(0) }

        override fun compareTo(other: Hand): Int {
            val handTypeComparison = other.handType.ordinal - handType.ordinal
            return if (handTypeComparison != 0) handTypeComparison else compareStrength(other)
        }

        private fun compareStrength(other: Hand): Int {
            cards.forEachIndexed { index, card ->
                if (card != other.cards[index]) return other.cards[index].ordinal - card.ordinal
            }
            return 0
        }

        companion object {
            fun fromInput(input: String): Hand {
                val split = input.split(" ")
                return Hand(parseCards(split[0]), split[1].toInt())
            }

            private fun parseCards(cardsString: String): List<Card> {
                return cardsString.map { cardValueOf(it) }
            }

            fun cardValueOf(char: Char): Card {
                return Card.entries.find { it.char == char } ?: throw Exception("Invalid card char: $char")
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
        CARD_2('2');
    }

    private enum class HandType {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIRS,
        ONE_PAIR,
        HIGH_CARD;
    }
}

