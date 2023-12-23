fun main() {
    Day5().run(
        35,
        46
    )
}

private class Day5 : Day(5) {

    override fun part1(input: List<String>): Int {
        // What is the lowest location number that corresponds to any of the initial seed numbers?
        return ListAlmanac.fromInput(input).getLowestLocation().toInt()
    }

    override fun part2(input: List<String>): Int {
        // What is the lowest location number that corresponds to any of the initial seed numbers?
        return SequenceAlmanac.fromInput(input).getLowestLocation().toInt()
    }
}

private open class Almanac(
    val seedToSoilMapping: List<Mapping>,
    val soilToFertilizerMapping: List<Mapping>,
    val fertilizerToWaterMapping: List<Mapping>,
    val waterToLightMapping: List<Mapping>,
    val lightToTemperatureMapping: List<Mapping>,
    val temperatureToHumidityMapping: List<Mapping>,
    val humidityToLocationMapping: List<Mapping>,
) {

    constructor(fullInput: String) : this(
        createMapping(fullInput, "seed-to-soil map:"),
        createMapping(fullInput, "soil-to-fertilizer map:"),
        createMapping(fullInput, "fertilizer-to-water map:"),
        createMapping(fullInput, "water-to-light map:"),
        createMapping(fullInput, "light-to-temperature map:"),
        createMapping(fullInput, "temperature-to-humidity map:"),
        createMapping(fullInput, "humidity-to-location map:")
    )

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

    protected fun getLocationForSeed(seed: Long): Long {
        val soil = seedToSoilMapping[seed]
        val fertilizer = soilToFertilizerMapping[soil]
        val water = fertilizerToWaterMapping[fertilizer]
        val light = waterToLightMapping[water]
        val temperature = lightToTemperatureMapping[light]
        val humidity = temperatureToHumidityMapping[temperature]
        return humidityToLocationMapping[humidity]
    }
}

private class ListAlmanac(
    val seeds: List<Long>,
    fullInput: String
) : Almanac(fullInput) {

    fun getLowestLocation(): Long {
        var minLocation = Long.MAX_VALUE
        seeds.forEach {
            val location = getLocationForSeed(it)
            if (location < minLocation) {
                println("New lowest location found: $location")
                minLocation = location
            }
        }
        return minLocation
    }

    companion object {
        fun fromInput(input: List<String>): ListAlmanac {
            val fullInput = getFullInput(input)
            val seeds =
                numberRegex.findAll(fullInput.split("seeds:")[1].split("\n")[0]).toList()
                    .map(MatchResult::value)
                    .map(String::toLong)
            return ListAlmanac(seeds, fullInput)
        }

    }
}

private class SequenceAlmanac(
    val seedSequences: List<Sequence<Long>>,
    fullInput: String,
) : Almanac(fullInput) {

    fun getLowestLocation(): Long {
        return seedSequences.minOf { sequence -> sequence.minOf(::getLocationForSeed) }
    }

    companion object {
        fun fromInput(input: List<String>): SequenceAlmanac {
            val fullInput = getFullInput(input)
            val seeds = numberRegex.findAll(fullInput.split("seeds:")[1].split("\n")[0])
                .map(MatchResult::value)
                .map(String::toLong)
                .chunked(2)
                .map(::pairToSequence)
                .toList()
            return SequenceAlmanac(seeds, fullInput)
        }

        private fun pairToSequence(rangeList: List<Long>): Sequence<Long> {
            return Sequence { RangeIterator(rangeList[0], rangeList[1]) }
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

private val numberRegex = Regex("""\d+""")

private fun getFullInput(input: List<String>) = input.joinToString(separator = "\n")

private fun createMapping(fullInput: String, mappingId: String) = numberRegex.findAll(fullInput.split(mappingId)[1].split("\n\n")[0])
    .toList()
    .map(MatchResult::value)
    .map(String::toLong)
    .chunked(3)
    .map(::Mapping)

private class RangeIterator(val rangeStart: Long, val rangeLength: Long) : Iterator<Long> {

    var initValue = rangeStart

    override fun hasNext(): Boolean {
        return initValue < rangeStart + rangeLength
    }

    override fun next(): Long {
        return initValue++
    }
}