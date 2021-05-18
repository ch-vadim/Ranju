package controller

import model.Board
import model.Cell

class BoardBasedCellListener(private val board: Board) : CellListener {
    override fun cellClicked(cell: Cell) {
        if (board.winner() == null) {
            board.makeTurn(cell)
        }
    }
}