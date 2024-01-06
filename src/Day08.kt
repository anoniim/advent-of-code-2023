fun main() {
    Day8().run(
        2,
        6
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
            val next = instructions[step % instructions.size]
            currentNode = next(nodes.getNode(currentNode))
            step++
        }
        return step
    }

    override fun part2(input: List<String>): Int {
        // Simultaneously start on every node that ends with A. How many steps does it take before you're only on nodes that end with Z?
        val instructions = input.getInstructions()
        val nodes = input.getNodes()
        var currentNodes = nodes.getStartingNodes()
        var step = 0
        while (currentNodes.allEndWithZ()) {
            val next = instructions[step % instructions.size]
            currentNodes = currentNodes.map { next(nodes.getNode(it)) }
            step++
        }
        return step
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

    private fun List<Node>.getNode(nodeName: String): Pair<String, String> {
        return find { it.name == nodeName }?.connections ?: throw Exception("Node $nodeName not found")
    }

    private fun List<String>.getNodes(): List<Node> {
        return takeLast(size - 2).map { Node.from(it) }
    }

    private fun List<Node>.getStartingNodes(): List<String> {
        return filter { it.name.endsWith(char = 'A') }
            .map(Node::name)
    }

    private fun List<String>.allEndWithZ(): Boolean {
        return all { it.endsWith(char = 'Z') }
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