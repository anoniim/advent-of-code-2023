fun main() {
    Day6().run(
        288,
        -1
    )
}

private class Day6 : Day(6) {

    override fun part1(input: List<String>): Int {
        // Determine the number of ways you could beat the record in each race. What do you get if you multiply these numbers together?
        return getRacesFromInput(input)
            .map(Race::getWaysToWinCount)
            .fold(1, Int::times)
    }

    override fun part2(input: List<String>): Int {
        //
        return -1
    }

    fun getRacesFromInput(input: List<String>): List<Race> {
        val numRegex = Regex("""\d+""")
        val times = numRegex.findAll(input[0])
            .map(MatchResult::value)
            .map(String::toInt)
            .toList()
        val records = numRegex.findAll(input[1])
            .map(MatchResult::value)
            .map(String::toInt)
            .toList()
        return times.mapIndexed { index, time ->
            Race(time, records[index])
        }
    }
}

private class Race(val time: Int, val record: Int) {

    fun getWaysToWinCount(): Int {
        var waysToWinCount = 0
        // Doesn't make sense to hold 0 or time milliseconds, so don't calculate holdTime for 0 and time
        repeat(time-1) {
            val holdTime = it + 1
            val travelTime = time - holdTime
            val distance = holdTime * travelTime
            if (distance > record) waysToWinCount++
        }
        return waysToWinCount
    }
}