package com.mio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.onSizeChanged

@Composable
fun VerticalVolumeSlider(
    volume: Float,
    onVolumeChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    var sliderHeight by remember { mutableStateOf(0) }

    Box(
        modifier = modifier
            .width(20.dp)
            .height(120.dp)
            .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    change.consume()

                    val newVolume = when {
                        sliderHeight == 0 -> volume
                        else -> {
                            // 纵向移动，向上滑音量增大，向下滑音量减小
                            val touchedY = change.position.y.coerceIn(0f, sliderHeight.toFloat())
                            val vol = 1f - (touchedY / sliderHeight)
                            vol.coerceIn(0f, 1f)
                        }
                    }

                    onVolumeChange(newVolume)
                }
            }
            .onSizeChanged { sliderHeight = it.height }
    ) {
        // 画当前音量进度
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(volume)
                .background(Color(0xfffd3c57), RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .align(Alignment.BottomCenter)
        )
    }
}

