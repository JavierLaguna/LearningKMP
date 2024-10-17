package dev.jlaguna

import androidx.compose.ui.window.ComposeUIViewController
import dev.jlaguna.data.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}