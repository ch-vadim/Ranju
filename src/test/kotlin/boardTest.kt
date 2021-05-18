import model.Board
import model.Cell
import model.Checker
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BoardTest {
    @Test
    fun testBoard() {
        val board = Board(15, 15)
        val listOfCell = listOf(Cell(0, 0),Cell(6, 7),Cell(9, 1),Cell(5, 7),
                Cell(9, 2),Cell(4, 7),Cell(1, 1), Cell(3, 7))
        for(cell in listOfCell) {
            board.makeTurn(cell)
        }
        assertEquals(Checker.WHITE, board[0, 0])
        assertEquals(Checker.WHITE, board[9, 1])
        assertEquals(Checker.BLACK, board.winner())
        assertTrue { board.hasFreeCells() }
        board.clear()
        assertEquals(Checker.BLACK, board[7, 7])
        assertEquals(Checker.WHITE, board.turn)

    }
    @Test
    fun testChecker() {
        val checker = Checker.WHITE
        assertEquals(Checker.BLACK, checker.opposite())
    }
}