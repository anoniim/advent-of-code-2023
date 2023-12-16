fun main() {
    Day2().run(
        8,
        2286
    )
}

private class Day2 : Day(2) {

    override fun part1(input: List<String>): Int {
        // Determine which games would have been possible if the bag had been loaded with only 12 red cubes, 13 green cubes, and 14 blue cubes. What is the sum of the IDs of those games?
        val requiredRed = 12
        val requiredGreen = 13
        val requiredBlue = 14
        var sum = 0
        input.map(Game::fromInput).forEach { game ->
            if (game.sets.containAtLeast(requiredRed, requiredGreen, requiredBlue)) sum += game.id
        }
        return sum
    }

    private fun List<CubeSet>.containAtLeast(requiredRed: Int, requiredGreen: Int, requiredBlue: Int): Boolean {
        return all { set ->
            requiredRed >= set.red &&
            requiredGreen >= set.green &&
            requiredBlue >= set.blue
        }
    }

    override fun part2(input: List<String>): Int {
        // For each game, find the minimum set of cubes that must have been present. What is the sum of the power of these sets?
        var sum = 0
        input.map(Game::fromInput).forEach { game ->
            sum += game.getMinimumSet().power
        }
        return sum
    }
}

private class Game (
    val id: Int,
    val sets: List<CubeSet>
) {
    fun getMinimumSet(): CubeSet {
        var minRed = 0
        var minGreen = 0
        var minBlue = 0
        sets.forEach {
            if (it.red > minRed) minRed = it.red
            if (it.green > minGreen) minGreen = it.green
            if (it.blue > minBlue) minBlue = it.blue
        }
        return CubeSet(minRed, minGreen, minBlue)
    }

    companion object {
        fun fromInput(input: String) : Game {
            val gameSplit = input.split(":")
            val gameId = gameSplit.first().split(" ").last().toInt()
            val sets = gameSplit.last().split(";").map { cubeSetString ->
                var red = 0
                var green = 0
                var blue = 0
                cubeSetString.split(",").map(String::trim).forEach { cubeColorText ->
                    if (cubeColorText.contains("red")) red = cubeColorText.split(" ").first().toInt()
                    if (cubeColorText.contains("green")) green = cubeColorText.split(" ").first().toInt()
                    if (cubeColorText.contains("blue")) blue = cubeColorText.split(" ").first().toInt()
                }
                CubeSet(red, green, blue)
            }
            return Game(gameId, sets)
        }
    }
}

private data class CubeSet(
    val red: Int = 0,
    val green: Int = 0,
    val blue: Int = 0,
) {
    val power = red * green * blue
}