val createNewDay = task<DefaultTask>("new") {
    description = "Task to prepare files for new day exercise"

    if (project.hasProperty("day")) {
        val dayNumber = (project.property("day") as String).toInt()
        val dayString = getDayString(dayNumber)

        createInputFiles(dayString)
        createSourceFileFromTemplate(dayNumber, dayString)

        println("Files for day $dayNumber created")
    } else {
        println("Property 'day' not provided, run the task with '-Pday='")
    }
}

fun getDayString(dayNumber: Int): String {
    return if (dayNumber / 10 == 0) {
        "0$dayNumber"
    } else {
        dayNumber.toString()
    }
}

fun createInputFiles(dayString: String) {
    File("${project.rootDir}/src/Day$dayString.txt").writeText("")
    File("${project.rootDir}/src/Day${dayString}_test.txt").writeText("")
}

fun createSourceFileFromTemplate(dayNumber: Int, dayString: String) {
    val template = File("${project.rootDir}/src/Day_template.kt").readText()
    val updatedTemplate = template.replace("0", "$dayNumber")
    File("${project.rootDir}/src/Day$dayString.kt").writeText(updatedTemplate)
}