package view

import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog

class PlayerDialog : Dialog<ButtonType>() {
    init {
        title = "Ranju"
        with (dialogPane) {
            headerText = "Do you want to play?"
            buttonTypes.add(ButtonType("Yes", ButtonBar.ButtonData.OK_DONE))
            buttonTypes.add(ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE))
        }
    }
}