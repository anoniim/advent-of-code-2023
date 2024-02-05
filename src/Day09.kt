fun main() {
    Day9().run(
        114,
        2
    )
}

private class Day9 : Day(9) {

    override fun part1(input: List<String>): Int {
        // Analyze your OASIS report and extrapolate the next value for each history. What is the sum of these extrapolated values?
        return input.map(History::fromInput)
            .sumOf(History::getPrediction)
    }

    override fun part2(input: List<String>): Int {
        // Analyze your OASIS report again, this time extrapolating the previous value for each history. What is the sum of these extrapolated values?
        return input.map(History::fromInput)
            .sumOf(History::getBackwardPrediction)
    }

    private class History(val values: List<Int>) {

        fun getPrediction(): Int {
            val steps = calculateSteps()
            return steps.scan(0) { prev, curr -> prev + curr }
                .last() + values.last()
        }

        private fun calculateSteps(): List<Int> {
            val steps = mutableListOf<Int>()
            var nextStep = values
            do {
                nextStep = nextStep.getNextStep()
                steps.add(nextStep.last())
            } while (!nextStep.all { it == 0 })
            return steps
        }

        private fun List<Int>.getNextStep(): List<Int> {
            return List(size - 1) { get(it + 1) - get(it) }
        }

        fun getBackwardPrediction(): Int {
            // TODO
            val steps = calculateSteps()
            return steps.scan(0) { prev, curr -> prev + curr }
                .last() + values.last()
        }

        companion object {
            fun fromInput(input: String): History {
                return History(input.split(" ").map(String::toInt))
            }
        }
    }
}