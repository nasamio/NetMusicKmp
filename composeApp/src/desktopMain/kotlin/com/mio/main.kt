package com.mio

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.mio.components.MioWindowDragArea
import com.mio.pages.RightTop
import netmusickmp.composeapp.generated.resources.Res
import netmusickmp.composeapp.generated.resources.ic_app
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NetMusicKmp",
        icon = painterResource(Res.drawable.ic_app),
        undecorated = true,
        transparent = true,
        state = WindowState(
            size = DpSize(1056.dp, 752.dp),
            position = WindowPosition.Aligned(Alignment.Center)  // 这里设置窗口居中
        ),
    ) {
        App(
            rightTopUi = { MioWindowDragArea { RightTop(it) } }
        )
    }
}