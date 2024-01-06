fun main() {
    Day8().run(
        2,
        -1
    )
}

private class Day8 : Day(8) {

    override fun part1(input: List<String>): Int {
        // Starting at AAA, follow the left/right instructions. How many steps are required to reach ZZZ?
        val instructions = input.getInstructions()
        val nodes = input.getNodes()
        var currentNode = "AAA"
        var step = 0
        while (currentNode != "ZZZ") {
            val next = instructions[step]
            currentNode = next(nodes.getNode(currentNode))
            step++
        }
        return step + 1
    }

    override fun part2(input: List<String>): Int {
        //
        return -1
    }

    private fun List<String>.getInstructions(): List<(Pair<String, String>) -> String> {
        val left = { pair: Pair<String, String> -> pair.first }
        val right = { pair: Pair<String, String> -> pair.second }
        return get(0).map {
            when (it) {
                'L' -> left
                'R' -> right
                else -> throw Exception("Unknown instruction input: $it")
            }
        }
    }

    private fun List<Node>.getNode(nodeName: String) : Pair<String, String> {
        return find { it.name == nodeName }?.connections ?: throw Exception("Node $nodeName not found")
    }

    private fun List<String>.getNodes(): List<Node> {
        return takeLast(size - 2).map { Node.from(it) }
    }

    private class Node(
        val name: String,
        val connections: Pair<String, String>
    ) {
        companion object {

            private val connectionsRegex = Regex("""\(([A-Z]{3}), ([A-Z]{3})\)""")

            fun from(input: String): Node {
                val split = input.split(" = ")
                val connectionNames = connectionsRegex.find(split[1])!!.groupValues
                val connections = Pair(connectionNames[1], connectionNames[2])
                return Node(split[0], connections)
            }
        }
    }
}