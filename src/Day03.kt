import kotlin.time.Duration.Companion.seconds

fun main() {
    Day3().run(
        4361,
        -1
    )
}

private class Day3 : Day(3) {

    override fun part1(input: List<String>): Int {
        // What is the sum of all of the part numbers in the engine schematic?
        val engine = EngineSchematic.fromInput(input)
        return engine.getPartNumbers().sumOf { it.first }
    }

    override fun part2(input: List<String>): Int {
        //
        return -1
    }
}

typealias PartNumbers = List<Pair<Int, IntRange>> // Pair<number, range of indices>
typealias Symbols = List<Int> // List of indices

private class EngineSchematic(
    private val width: Int,
    private val allNumbers: PartNumbers,
    private val allSymbols: Symbols,
) {

    fun getPartNumbers(): PartNumbers {
        return allNumbers.filter {
            val startIndex = it.second.first
            val endIndex = it.second.last
            startIndex.isAdjacentToSymbol() || endIndex.isAdjacentToSymbol()
        }
    }

    private fun Int.isAdjacentToSymbol(): Boolean {
        // Is this number adjacent to any symbol?
        return allSymbols.any { symbolIndex ->
            (symbolIndex - width) == this || (symbolIndex + width) == this ||
                    (if (this.isNotAtTheBeginningOfLine()) {
                        (symbolIndex - width - 1) == this || (symbolIndex - 1) == this || (symbolIndex + width - 1) == this
                    } else false) ||
                    (if (this.isNotAtTheEndOfLine()) {
                        (symbolIndex - width + 1) == this || (symbolIndex + 1) == this || (symbolIndex + width + 1) == this
                    } else false)
        }
    }

    private fun Int.isNotAtTheBeginningOfLine(): Boolean {
        return this % width != 0
    }

    private fun Int.isNotAtTheEndOfLine(): Boolean {
        return this % width != width-1
    }

    companion object {
        fun fromInput(input: List<String>): EngineSchematic {
            val width = input[0].length
            val fullInput = input.joinToString(separator = "")
            val numberRegex = Regex("""(\d)+""")
            val matchResults = numberRegex.findAll(fullInput)
            val numbers = matchResults.map { matchResult ->
                Pair(matchResult.value.toInt(), matchResult.range)
            }.toList()

            val symbols = fullInput.mapIndexed { index, char ->
                if (!char.isDigit() && char != '.') index else null
            }.filterNotNull()
            return EngineSchematic(width, numbers, symbols)
        }
    }
}


