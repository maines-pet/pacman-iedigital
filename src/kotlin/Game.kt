import java.io.BufferedReader

fun main(args: Array<String>) {
    val game = Game(5, 5)
    val pacman = Pacman().also { it.game = game }
    game.pacman = pacman

    var inputFile = game.javaClass.getResourceAsStream("/input/commandlist.txt")
        ?.let { it.bufferedReader().use(BufferedReader::readLines) }

    game.processCommands(inputFile ?: listOf())

}

class Game(val width: Int, val length: Int) {
    lateinit var pacman: Pacman

    fun processCommands(inputCommand: List<String>) {
        if (inputCommand.isEmpty()) println("Empty input file.")
        var i = 0
        while (true) {
            if (inputCommand[i].contains("PLACE")) {
                if (isValidPlaceCommand(inputCommand[i], null)) break
            }
            i++
        }
        inputCommand.drop(i).forEach { command ->
            when (command) {
                "LEFT" -> pacman.left()
                "RIGHT" -> pacman.right()
                "REPORT" -> println(pacman.report())
                "MOVE" -> pacman.move()
            }

            if (command.contains("PLACE")) {
                isValidPlaceCommand(command) { xPos, yPos, face ->
                    pacman.place(xPos, yPos, face)
                }
            }
        }
    }

    private fun isValidPlaceCommand(
        place: String,
        executeFunction: ((xPos: Int, yPos: Int, face: String) -> Unit)?
    ): Boolean {
        val find = placeCommandPattern.find(place)
        if (find != null) {
            val (x, y, f) = find.destructured
            if (isWithinBounds(x.toInt(), y.toInt())) {
                executeFunction?.apply { this(x.toInt(), y.toInt(), f) }
                return true
            }
        }
        return false
    }

    fun isWithinBounds(x: Int, y: Int): Boolean {
        if (x < 0 || x >= width || y < 0 || y >= length) {
            return false
        }
        return true
    }

}

val placeCommandPattern = Regex("""PLACE (\d+),(\d+),(\w+)""")
