package com.mio

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import netmusickmp.composeapp.generated.resources.Res
import netmusickmp.composeapp.generated.resources.ic_app
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NetMusicKmp",
        icon = painterResource(Res.drawable.ic_app)
    ) {
        App()
    }
}