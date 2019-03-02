import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PacmanTest {

    private val game = Game(5,5)
    private val pacman = Pacman()

    @Before
    fun setUpGameAndPacman() {
        game.pacman = pacman
        pacman.game = game
    }

    @Test
    fun `Report command`() {
        assertEquals(pacman.report(), "Pacman @ 0,0 facing EAST.")
    }

    @Test
    fun `Place command`() {
        pacman.place(1, 2, Direction.WEST.toString())
        assertEquals(pacman.report(), "Pacman @ 1,2 facing WEST.")
    }

    @Test
    fun `Left command`() {
        pacman.left()
        assertEquals(pacman.report(), "Pacman @ 0,0 facing ${Direction.NORTH}.")
        pacman.left()
        assertEquals(pacman.report(), "Pacman @ 0,0 facing ${Direction.WEST}.")
        pacman.left()
        assertEquals(pacman.report(), "Pacman @ 0,0 facing ${Direction.SOUTH}.")
        pacman.left()
        assertEquals(pacman.report(), "Pacman @ 0,0 facing ${Direction.EAST}.")

    }

    @Test
    fun `Right command`() {

        pacman.right()
        assertEquals("Pacman @ 0,0 facing ${Direction.SOUTH}.", pacman.report())
        pacman.right()
        assertEquals("Pacman @ 0,0 facing ${Direction.WEST}.", pacman.report())
        pacman.right()
        assertEquals("Pacman @ 0,0 facing ${Direction.NORTH}.", pacman.report())
        pacman.right()
        assertEquals("Pacman @ 0,0 facing ${Direction.EAST}.", pacman.report())

    }

    @Test
    fun `Move command`() {

        pacman.move()
        assertEquals("Pacman @ 1,0 facing EAST.", pacman.report())

        pacman.left()
        pacman.move()
        assertEquals("Pacman @ 1,1 facing NORTH.", pacman.report())

        pacman.left()
        pacman.move()
        assertEquals("Pacman @ 0,1 facing WEST.", pacman.report())

        pacman.left()
        pacman.move()
        assertEquals("Pacman @ 0,0 facing SOUTH.", pacman.report())
    }

}