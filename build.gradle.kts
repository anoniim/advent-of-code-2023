/**
 * Custom util Gradle task to create test and input files as well as a Kotlin solution file for every day of Advent of Code.
 *
 * Usage: ./gradlew new -PDay=X
 *        [where X is the day number]
 */
val createNewDay = task<DefaultTask>("new") {
    description = "Task to prepare files for new day exercise"

    doLast {
        if (project.hasProperty("day")) {
            val dayNumber = (project.property("day") as String).toInt()
            val dayString = getDayString(dayNumber)

            createInputFiles(dayNumber.toString(), dayString)
            createSourceFileFromTemplate(dayNumber.toString(), dayString)
        } else {
            println("Property 'day' not provided, run the task with '-Pday='")
        }
    }
}

fun getDayString(dayNumber: Int): String {
    return if (dayNumber / 10 == 0) {
        "0$dayNumber"
    } else {
        dayNumber.toString()
    }
}

fun createInputFiles(dayNumber: String, dayString: String) {
    val inputFile = File("${project.rootDir}/src/Day$dayString.txt")
    writeToFileIfNotExists(inputFile, "Input file", dayNumber, "")

    val testInputFile = File("${project.rootDir}/src/Day${dayString}_test.txt")
    writeToFileIfNotExists(testInputFile, "Test input file", dayNumber, "")
}

fun createSourceFileFromTemplate(dayNumber: String, dayString: String) {
    val template = File("${project.rootDir}/src/Day_template.kt").readText()
    val updatedTemplate = template.replace("0", dayNumber)
    val dayFile = File("${project.rootDir}/src/Day$dayString.kt")
    writeToFileIfNotExists(dayFile, "Code file", dayNumber, updatedTemplate)
}

fun writeToFileIfNotExists(file: File, fileName: String, dayNumber: String, content: String) {
    if (!file.exists()) {
        file.writeText(content)
        println("- $fileName for day $dayNumber created")
    } else {
        println("- $fileName for day $dayNumber already exists")
    }
}