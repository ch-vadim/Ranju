package view

import javafx.application.Application
import javafx.scene.control.ButtonBar
import javafx.stage.Stage
import tornadofx.App

class RanjuApp : App(RanjuAppView::class) {
    override fun start(stage: Stage) {
        val dialog = PlayerDialog()
        val result = dialog.showAndWait()
        if (result.isPresent && result.get().buttonData == ButtonBar.ButtonData.OK_DONE) {
            super.start(stage)
        }
    }
}

fun main(args: Array<String>) {
    Application.launch(RanjuApp::class.java, *args)
}