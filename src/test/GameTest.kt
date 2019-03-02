import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class GameTest {
    private val originalSout = System.out!!
    private val testOutput = ByteArrayOutputStream()

    private val game = Game(5,5)
    private val pacman = Pacman()
    @Before
    fun setUpStream(){
        System.setOut(PrintStream(testOutput))
        game.pacman = pacman
        pacman.game = game
    }

    @After
    fun restoreStream(){
        System.setOut(originalSout)
    }

    @Test
    fun `Ignore all including invalid PLACE command before the first valid PLACE Command`() {
        val commands = arrayListOf(
            "MOVE",
            "REPORT",
            "LEFT",
            "REPORT",
            "RIGHT",
            "REPORT",
            "PLACE 5,5,EAST",
            "PLACE 3,4,EAST",
            "PLACE 3,1,SOUTH",
            "REPORT"
        )
        game.processCommands(commands)
        assertEquals("Pacman @ 3,1 facing SOUTH.", testOutput.toString().trimEnd())
    }

    @Test
    fun `Process VALID command - PLACE, LEFT, RIGHT, MOVE, REPORT`(){
        val commands = arrayListOf(
            "PLACE 3,1,SOUTH",
            "MOVE",
            "REPORT",
            "LEFT",
            "REPORT",
            "MOVE",
            "REPORT",
            "RIGHT",
            "REPORT"
        )
        game.processCommands(commands)
        assertEquals("Pacman @ 3,0 facing SOUTH.\r\nPacman @ 3,0 facing EAST.\r\n" +
                "Pacman @ 4,0 facing EAST.\r\nPacman @ 4,0 facing SOUTH.".trim(),
            testOutput.toString().trim())
    }

    @Test
    fun `Ignore all INVALID command`(){
        //PLACE Commands with out of bounds X and Y
        //PLACE Commands with invalid X, Y and F (non-numeric, non ordinal direction)
        //MOVE Commands that will result result pacman to fall

        val commands = listOf(
            "PLACE 3,3,EAST",
            //invalid PLACE Command
            "PLACE -1,-1,SOUTH",
            "PLACE 0,5,SOUTH",
            "PLACE 5,0,SOUTH",
            "PLACE 5,5,SOUTH",
            "PLACE 0,0,NORTHWEST",
            "PLACE 0,0",
            "PLACE 0,NORTH",
            "PLACE NORTH",
            "move",
            "mov",
            "left",
            "lft",
            "rigt",
            "report",
            "right",
            "rep",
            "REPORT", // Should report 3,3,EAST,

            //MOVE Commands resulting to out of bound
            "PLACE 4,4,EAST",
            "MOVE",
            "REPORT", // Should report 4,4,EAST,
            "LEFT",
            "MOVE",
            "REPORT", // Should report 4,4,NORTH,


            "PLACE 0,4,WEST",
            "MOVE",
            "REPORT", // Should report 0,4,WEST,
            "RIGHT",
            "MOVE",
            "REPORT", // Should report 0,4,NORTH,

            "PLACE 4,0,EAST",
            "MOVE",
            "REPORT", // Should report 4,0,EAST,
            "RIGHT",
            "MOVE",
            "REPORT", // Should report 4,0,SOUTH,

            "PLACE 0,0,WEST",
            "MOVE",
            "REPORT", // Should report 0,0,WEST,
            "LEFT",
            "MOVE",
            "REPORT"// Should report 0,0,SOUTH,

        )
        game.processCommands(commands)
        assertEquals(("Pacman @ 3,3 facing EAST.\r\n" +
                "Pacman @ 4,4 facing EAST.\r\nPacman @ 4,4 facing NORTH.\r\n" +
                "Pacman @ 0,4 facing WEST.\r\nPacman @ 0,4 facing NORTH.\r\n" +
                "Pacman @ 4,0 facing EAST.\r\nPacman @ 4,0 facing SOUTH.\r\n" +
                "Pacman @ 0,0 facing WEST.\r\nPacman @ 0,0 facing SOUTH.\r\n").trim(),
            testOutput.toString().trim())
    }


    @Test
    fun `Example 1 from IE Code challenge`(){
        val commands = "PLACE 0,0,NORTH\nMOVE\nREPORT\n"
        game.processCommands(commands.split("\n"))
        assertEquals("Pacman @ 0,1 facing NORTH.", testOutput.toString().trim())
    }

    @Test
    fun `Example 2 from IE Code challenge`(){
        val commands = "PLACE 0,0,NORTH\nLEFT\nREPORT\n"
        game.processCommands(commands.split("\n"))
        assertEquals("Pacman @ 0,0 facing WEST.", testOutput.toString().trim())
    }

    @Test
    fun `Example 3 from IE Code challenge`(){
        val commands = "PLACE 1,2,EAST\nMOVE\nMOVE\nLEFT\nMOVE\nREPORT\n"
        game.processCommands(commands.split("\n"))
        assertEquals("Pacman @ 3,3 facing NORTH.", testOutput.toString().trim())
    }
}