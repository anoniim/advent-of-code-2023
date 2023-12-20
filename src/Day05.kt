fun main() {
    Day5().run(
        35,
        -1
    )
}

private class Day5 : Day(5) {

    override fun part1(input: List<String>): Int {
        // What is the lowest location number that corresponds to any of the initial seed numbers?
        Almanac.fromInput(input)
        return -1
    }

    override fun part2(input: List<String>): Int {
        //
        return -1
    }
}

private class Almanac(
    val seeds: List<Int>,
    val seedToSoil: List<Mapping>,
    val soilToFertilizer: List<Mapping>,
    val fertilizerToWater: List<Mapping>,
    val waterToLight: List<Mapping>,
    val lightToTemperature: List<Mapping>,
    val temperatureToHumidity: List<Mapping>,
    val humidityToLocation: List<Mapping>,
) {



    companion object {
        fun fromInput(input: List<String>): Almanac {
            val fullInput = input.joinToString(separator = "\n")
            val numberRegex = Regex("""\d+""")
            val seeds = numberRegex.findAll(fullInput.split("seeds:")[1].split("\n")[0]).toList().map(MatchResult::value).map(String::toInt)
            val seedToSoilMapping = numberRegex.findAll(fullInput.split("seed-to-soil map:")[1].split("\n\n")[0]).toList().map(MatchResult::value).map(String::toInt).chunked(3).map(::Mapping)
            val soilToFertilizerMapping = numberRegex.findAll(fullInput.split("soil-to-fertilizer map:")[1].split("\n\n")[0]).toList().map(MatchResult::value).map(String::toInt).chunked(3).map(::Mapping)
            val fertilizerToWaterMapping = numberRegex.findAll(fullInput.split("fertilizer-to-water map:")[1].split("\n\n")[0]).toList().map(MatchResult::value).map(String::toInt).chunked(3).map(::Mapping)
            val waterToLightMapping = numberRegex.findAll(fullInput.split("water-to-light map:")[1].split("\n\n")[0]).toList().map(MatchResult::value).map(String::toInt).chunked(3).map(::Mapping)
            val lightToTemperatureMapping = numberRegex.findAll(fullInput.split("light-to-temperature map:")[1].split("\n\n")[0]).toList().map(MatchResult::value).map(String::toInt).chunked(3).map(::Mapping)
            val temperatureToHumidityMapping = numberRegex.findAll(fullInput.split("temperature-to-humidity map:")[1].split("\n\n")[0]).toList().map(MatchResult::value).map(String::toInt).chunked(3).map(::Mapping)
            val humidityToLocationMapping = numberRegex.findAll(fullInput.split("humidity-to-location map:")[1].split("\n\n")[0]).toList().map(MatchResult::value).map(String::toInt).chunked(3).map(::Mapping)
            return Almanac(seeds,
                seedToSoilMapping,
                soilToFertilizerMapping,
                fertilizerToWaterMapping,
                waterToLightMapping,
                lightToTemperatureMapping,
                temperatureToHumidityMapping,
                humidityToLocationMapping)
        }
    }
}

private class Mapping(
    val destinationRangeStart: Int,
    val sourceRangeStart: Int,
    val rangeLength: Int,
) {
    constructor (input: List<Int>) : this(input[0], input[1], input[2])
}