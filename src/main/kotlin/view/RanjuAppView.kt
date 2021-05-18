package view

import controller.BoardBasedCellListener
import controller.BoardListener
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.BorderPane
import model.Board
import model.Cell
import model.Checker
import tornadofx.*

class RanjuAppView : View(), BoardListener {
    companion object {
        const val columns = 15
        const  val rows = 15
    }
    private val board = Board(columns, rows)
    private var inProcess = true
    private lateinit var status: Label
    private val buttons = mutableMapOf<Cell, Button>()
    override val root = BorderPane()

    init {
        title = "Ranju"
        val listener = BoardBasedCellListener(board)
        board.setListener(this)
        with(root) {
            top{
                vbox {
                    menubar {
                        menu("Settings") {
                            item("Restart").action {
                                restartGame()
                            }
                            item("Exit").action {
                                this@RanjuAppView.close()
                            }
                        }
                    }
                }
            }
            center {
                gridpane {
                    for (y in 0 until rows) {
                        row {
                            for (x in 0 until columns) {
                                val cell = Cell(x, y)
                                val button = button (graphic = ImageView(getImage(y, x))) {
                                    style {
                                        minHeight = 48.px
                                        minWidth = 48.px
                                        padding = box(0.px)
                                    }
                                }
                                button.action {
                                    if (inProcess) {
                                        listener.cellClicked(cell)
                                    }
                                }
                                buttons[cell] = button
                            }
                        }
                    }
                }

            }
            bottom {
                status = label("")
            }

        }
        updateBoardAndStatus()
    }

    private fun getImage(y: Int, x: Int): String {
        return when {
            (y == 0 && x == 0) -> "/topLeft.png"
            (y == 0 && x == columns - 1) -> "/topRight.png"
            (y == rows - 1 && x == columns - 1) -> "/downRight.png"
            (y == rows - 1 && x == 0) -> "/downLeft.png"
            (x == columns - 1) -> "/right.png"
            (x == 0) -> "/left.png"
            (y == rows - 1) -> "/down.png"
            (y == 0) -> "/top.png"
            (y == rows / 2 && x == columns / 2) -> "/black.png"
            (y == rows / 2 + 2 && (x == columns / 2 + 2 || x == columns / 2 - 2) ||
                    y == rows / 2 - 2 && (x == columns / 2 + 2 || x == columns / 2 - 2))
            -> "/point.png"
            else -> "/center.png"
        }
    }

    private fun restartGame() {
        board.clear()
        for (x in 0 until  columns) {
            for (y in 0 until rows) {
                updateBoardAndStatus(Cell(x, y))
            }
        }
        inProcess = true
    }

    private fun updateBoardAndStatus(cell: Cell? = null) {
        val winningCombo = board.winningCombo()
        val winner = winningCombo?.winner
        status.text = when {
            winner == Checker.WHITE -> {
                inProcess = false
                "White win! Press 'Restart' or 'Exit' in 'Settings'"
            }
            winner == Checker.BLACK -> {
                inProcess = false
                "Black win! Press 'Restart' or 'Exit' in 'Settings'"
            }
            !board.hasFreeCells() -> {
                inProcess = false
                "Draw! Press 'Restart' or 'Exit' in 'Settings'"
            }
            board.turn == Checker.BLACK ->
                "Game in process: Black turn"
            else -> "Game in process: White turn"
        }
        if (cell != null) {
            val imageName = when (board[cell]) {
                Checker.BLACK -> "/black.png"
                Checker.WHITE -> "/white.png"
                else -> getImage(cell.y, cell.x)
            }
            buttons[cell]?.apply {
                graphic = ImageView(imageName)
                style {
                    minHeight = 48.px
                    minWidth = 48.px
                    padding = box(0.px)
                }
            }
        }
        if (winner != null) {
            val startCell = winningCombo.startCell
            val endCell = winningCombo.endCell
            var actualCell = startCell
            val imageName = when (winner) {
                Checker.BLACK -> "/blackWin.png"
                Checker.WHITE -> "/whiteWin.png"
            }
            while (true) {
                buttons[actualCell]?.apply {
                    graphic = ImageView(imageName)
                    style {
                        minHeight = 48.px
                        minWidth = 48.px
                        padding = box(0.px)
                    }
                }
                if (actualCell == endCell) break
                actualCell += winningCombo.direction
            }
        }
    }

    override fun turnMade(cell: Cell) {
        updateBoardAndStatus(cell)
    }


}