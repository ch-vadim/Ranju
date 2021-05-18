package model

import controller.BoardListener

class Board constructor(private val width: Int = 15, private val height: Int = 15) {
    private val checkers: MutableMap<Cell, Checker> = mutableMapOf(Cell(7,7) to Checker.BLACK)
    var turn = Checker.WHITE
    private var listener: BoardListener? = null

    companion object {
        const val TO_WIN_LENGTH = 5
        private val DIRECTIONS = arrayOf(
            Cell(0, 1), Cell(1, 0),
            Cell(1, 1), Cell(1, -1)
        )
    }

    fun clear() {
        checkers.clear()
        checkers[Cell(7,7)] = Checker.BLACK
        turn = Checker.WHITE
    }

    fun setListener(listener: BoardListener) {
        this.listener = listener
    }

    operator fun get(x: Int, y: Int): Checker? {
        return get(Cell(x, y))
    }

    operator fun get(cell: Cell): Checker? {
        return checkers[cell]
    }
    fun makeTurn(cell:Cell): Cell? {
        if (!correct(cell)) return null
        if (!checkers.containsKey(cell)) {
            checkers[cell] = turn
            turn = turn.opposite()
            if (listener != null) {
                listener!!.turnMade(cell)
            }
            return cell
        }

        return null
    }
    fun hasFreeCells(): Boolean {
        for (x in 0 until width) {
            for (y in 0 until height) {
                if (get(x, y) == null) return true
            }
        }
        return false
    }

    private fun correct(cell: Cell): Boolean {
        return cell.x in 0 until width && cell.y in 0 until  height
    }

    fun winner(): Checker? {
        val combo: WinningCombo = winningCombo() ?: return null
        return combo.winner
    }

    fun winningCombo(): WinningCombo? {
        for (x in 0 until width) {
            for (y in 0 until height) {
                val cell = Cell(x, y)
                val startChecker = checkers[cell] ?: continue
                for (dir in DIRECTIONS) {
                    var actual = cell
                    var length = 1
                    while (true) {
                        actual += dir
                        if (get(actual) != startChecker) break
                        length++
                    }
                    if (length >= TO_WIN_LENGTH) return WinningCombo(startChecker, cell, actual - dir, dir)
                }

            }
        }
        return null
    }
}