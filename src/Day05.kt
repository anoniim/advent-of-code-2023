fun main() {
    Day5().run(
        35,
        46
    )
}

private class Day5 : Day(5) {

    override fun part1(input: List<String>): Int {
        // What is the lowest location number that corresponds to any of the initial seed numbers?
        return Almanac.fromInputPart1(input).getLowestLocation().toInt()
    }

    override fun part2(input: List<String>): Int {
        // What is the lowest location number that corresponds to any of the initial seed numbers?
        return Almanac.fromInputPart2(input).getLowestLocation().toInt()
    }
}

private class Almanac(
    val seeds: List<Long>,
    val seedToSoilMapping: List<Mapping>,
    val soilToFertilizerMapping: List<Mapping>,
    val fertilizerToWaterMapping: List<Mapping>,
    val waterToLightMapping: List<Mapping>,
    val lightToTemperatureMapping: List<Mapping>,
    val temperatureToHumidityMapping: List<Mapping>,
    val humidityToLocationMapping: List<Mapping>,
) {

    private operator fun List<Mapping>.get(source: Long): Long {
        val specialMapping = find { mapping ->
            val range = LongRange(mapping.sourceRangeStart, mapping.sourceRangeStart + mapping.rangeLength)
            range.contains(source)
        }
        return if (specialMapping != null) {
            val delta = specialMapping.destinationRangeStart - specialMapping.sourceRangeStart
            source + delta
        } else source
    }

    fun getLowestLocation(): Long {
        var minLocation = Long.MAX_VALUE
        seeds.forEach {
            val location = it.getLocation()
            if (location < minLocation) {
                println("New lowest location found: $location")
                minLocation = location
            }
        }
        return minLocation
    }

    private fun Long.getLocation(): Long {
        val soil = seedToSoilMapping[this]
        val fertilizer = soilToFertilizerMapping[soil]
        val water = fertilizerToWaterMapping[fertilizer]
        val light = waterToLightMapping[water]
        val temperature = lightToTemperatureMapping[light]
        val humidity = temperatureToHumidityMapping[temperature]
        return humidityToLocationMapping[humidity]
    }

    companion object {

        private val numberRegex = Regex("""\d+""")

        private fun getFullInput(input: List<String>) = input.joinToString(separator = "\n")

        fun fromInputPart1(input: List<String>): Almanac {
            val fullInput = getFullInput(input)
            val seeds =
                numberRegex.findAll(fullInput.split("seeds:")[1].split("\n")[0]).toList()
                    .map(MatchResult::value)
                    .map(String::toLong)
            return createAlmanac(fullInput, seeds)
        }

        fun fromInputPart2(input: List<String>): Almanac {
            val fullInput = getFullInput(input)
            val seeds = numberRegex.findAll(fullInput.split("seeds:")[1].split("\n")[0])
                .map(MatchResult::value)
                .map(String::toLong)
                .chunked(2)
                .map(::pairToList)
                .flatten()
                .toList()
            println("Seeds parsed")
            return createAlmanac(fullInput, seeds)
        }

        private fun pairToList(rangeList: List<Long>): List<Long> {
            return List(rangeList[1].toInt()) {
                rangeList[0] + it
            }
        }

        private fun createAlmanac(fullInput: String, seeds: List<Long>): Almanac {
            val seedToSoilMapping =
                numberRegex.findAll(fullInput.split("seed-to-soil map:")[1].split("\n\n")[0]).toList()
                    .map(MatchResult::value).map(String::toLong).chunked(3).map(::Mapping)
            val soilToFertilizerMapping =
                numberRegex.findAll(fullInput.split("soil-to-fertilizer map:")[1].split("\n\n")[0]).toList()
                    .map(MatchResult::value).map(String::toLong).chunked(3).map(::Mapping)
            val fertilizerToWaterMapping =
                numberRegex.findAll(fullInput.split("fertilizer-to-water map:")[1].split("\n\n")[0]).toList()
                    .map(MatchResult::value).map(String::toLong).chunked(3).map(::Mapping)
            val waterToLightMapping =
                numberRegex.findAll(fullInput.split("water-to-light map:")[1].split("\n\n")[0]).toList()
                    .map(MatchResult::value).map(String::toLong).chunked(3).map(::Mapping)
            val lightToTemperatureMapping =
                numberRegex.findAll(fullInput.split("light-to-temperature map:")[1].split("\n\n")[0]).toList()
                    .map(MatchResult::value).map(String::toLong).chunked(3).map(::Mapping)
            val temperatureToHumidityMapping =
                numberRegex.findAll(fullInput.split("temperature-to-humidity map:")[1].split("\n\n")[0]).toList()
                    .map(MatchResult::value).map(String::toLong).chunked(3).map(::Mapping)
            val humidityToLocationMapping =
                numberRegex.findAll(fullInput.split("humidity-to-location map:")[1].split("\n\n")[0]).toList()
                    .map(MatchResult::value).map(String::toLong).chunked(3).map(::Mapping)
            println("Mappings created")
            return Almanac(
                seeds,
                seedToSoilMapping,
                soilToFertilizerMapping,
                fertilizerToWaterMapping,
                waterToLightMapping,
                lightToTemperatureMapping,
                temperatureToHumidityMapping,
                humidityToLocationMapping
            )
        }
    }
}

private class Mapping(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long,
) {
    constructor (input: List<Long>) : this(input[0], input[1], input[2])

}