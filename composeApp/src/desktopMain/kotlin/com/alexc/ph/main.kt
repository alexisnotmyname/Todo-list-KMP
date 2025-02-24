package com.alexc.ph

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.alexc.ph.app.App
import com.alexc.ph.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "TodoAppKmp",
        ) {
            App()
        }
    }
}