package com.alexc.ph

import androidx.compose.ui.window.ComposeUIViewController
import com.alexc.ph.app.App
import com.alexc.ph.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}