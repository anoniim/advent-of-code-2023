fun main() {
    Day3().run(
        4361,
        467835
    )
}

private class Day3 : Day(3) {

    override fun part1(input: List<String>): Int {
        // What is the sum of all of the part numbers in the engine schematic?
        val engine = EngineSchematic.fromInput(input)
        return engine.getPartNumbers().sumOf { it.number }
    }

    override fun part2(input: List<String>): Int {
        // What is the sum of all of the gear ratios in your engine schematic?
        val engine = EngineSchematic.fromInput(input)
        return engine.getGearRatios().sum()
    }
}

class PartNumber(val number: Int, val indexRange: IntRange)
class Symbol(val char: Char, val index: Int)

private class EngineSchematic(
    private val width: Int,
    private val allNumbers: List<PartNumber>,
    private val allSymbols: List<Symbol>,
) {

    fun getPartNumbers(): List<PartNumber> {
        return allNumbers.filter {
            val startIndex = it.indexRange.first
            val endIndex = it.indexRange.last
            startIndex.isAdjacentToSymbol() || endIndex.isAdjacentToSymbol()
        }
    }

    fun getGearRatios(): List<Int> {
        return allSymbols.filter { it.char == '*' }
            .map { symbol ->
                val adjacentPartNumbers = mutableListOf<Int>()
                allNumbers.forEach { partNumber ->
                    if (symbol.index.isAdjacentTo(partNumber.indexRange.first) || symbol.index.isAdjacentTo(partNumber.indexRange.last)) {
                        adjacentPartNumbers.add(partNumber.number)
                    }
                }
                // Return gear ratios for gears that only have 2 neighbors
                if (adjacentPartNumbers.size == 2) adjacentPartNumbers[0] * adjacentPartNumbers[1] else 0
            }
    }

    private fun Int.isAdjacentToSymbol(): Boolean {
        // Is this number adjacent to any symbol?
        return allSymbols.any { symbol ->
            isAdjacentTo(symbol.index)
        }
    }

    private fun Int.isAdjacentTo(otherIndex: Int) = (otherIndex - width) == this || (otherIndex + width) == this ||
            (if (this.isNotAtTheBeginningOfLine()) {
                (otherIndex - width - 1) == this || (otherIndex - 1) == this || (otherIndex + width - 1) == this
            } else false) ||
            (if (this.isNotAtTheEndOfLine()) {
                (otherIndex - width + 1) == this || (otherIndex + 1) == this || (otherIndex + width + 1) == this
            } else false)

    private fun Int.isNotAtTheBeginningOfLine(): Boolean {
        return this % width != 0
    }

    private fun Int.isNotAtTheEndOfLine(): Boolean {
        return this % width != width - 1
    }

    companion object {
        fun fromInput(input: List<String>): EngineSchematic {
            val width = input[0].length
            val fullInput = input.joinToString(separator = "")
            val numberRegex = Regex("""(\d)+""")
            val matchResults = numberRegex.findAll(fullInput)
            val numbers = matchResults.map { matchResult ->
                PartNumber(matchResult.value.toInt(), matchResult.range)
            }.toList()

            val symbols = fullInput.mapIndexed { index, char ->
                if (!char.isDigit() && char != '.') Symbol(char, index) else null
            }.filterNotNull()
            return EngineSchematic(width, numbers, symbols)
        }
    }
}


