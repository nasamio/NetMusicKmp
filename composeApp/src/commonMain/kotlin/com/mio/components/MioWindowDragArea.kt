package com.mio.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.WindowScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.awt.MouseInfo
import kotlin.math.roundToInt

/**
 * MioWindowDragArea：基于绝对鼠标位置拖拽移动窗体的自定义拖拽区域。
 *
 * @param enableReleaseAnimation 是否在拖拽松手时启用窗口位置回弹动画，默认开启
 * @param content 可组合内容，作为拖拽区域的子内容
 *
 * 使用时请确保当前 Composable 处于 Window 内容层级中，
 * 且 Window 可通过以下方式访问（ComposeWindow 类型）
 */
@Composable
fun WindowScope.MioWindowDragArea(
    enableReleaseAnimation: Boolean = false,
    content: @Composable () -> Unit,
) {
    var dragStartWindowPos by remember { mutableStateOf(Offset.Zero) }
    var dragStartMousePos by remember { mutableStateOf(Offset.Zero) }

    // 这里初始时只保存 window 位置，不赋值给 targetPos，targetPos 初始为 null 表示未开始拖拽
    var targetPos by remember { mutableStateOf<Offset?>(null) }
    var previousTargetPos by remember { mutableStateOf<Offset?>(null) }

    val animX = remember { Animatable(window.x.toFloat()) }
    val animY = remember { Animatable(window.y.toFloat()) }
    val coroutineScope = rememberCoroutineScope()

    var isDragging by remember { mutableStateOf(false) }

    val threshold = 1.2f // 阈值，单位px

    fun getGlobalMousePosition(): Offset {
        val point = MouseInfo.getPointerInfo().location
        return Offset(point.x.toFloat(), point.y.toFloat())
    }

    LaunchedEffect(targetPos, isDragging) {
        if (targetPos == null) {
            // 初始状态，什么都不做，避免一开始就设置窗口位置
            return@LaunchedEffect
        }

        if (isDragging) {
            // 拖拽中，立刻设置位置，不动画
            coroutineScope.launch { animX.snapTo(targetPos!!.x) }
            coroutineScope.launch { animY.snapTo(targetPos!!.y) }
        } else {
            if (enableReleaseAnimation) {
                // 拖拽结束且开启动画，做弹性动画
                coroutineScope.launch {
                    animX.animateTo(
                        targetPos!!.x,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
                coroutineScope.launch {
                    animY.animateTo(
                        targetPos!!.y,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            } else {
                // 拖拽结束且关闭动画，立刻定位，不动画
                coroutineScope.launch { animX.snapTo(targetPos!!.x) }
                coroutineScope.launch { animY.snapTo(targetPos!!.y) }
            }
        }
    }

    LaunchedEffect(animX.value, animY.value) {
        // 只有在有拖拽相关的 targetPos 时才设置窗口位置，避免初始时干扰
        if (targetPos != null) {
            window.setLocation(animX.value.roundToInt(), animY.value.roundToInt())
        }
    }

    Box(
        modifier = Modifier.pointerInput(Unit) {
            coroutineScope {
                while (true) {
                    val down = awaitPointerEventScope { awaitFirstDown(requireUnconsumed = false) }
                    if (down.isConsumed) {
                        continue
                    }
                    isDragging = true
                    dragStartWindowPos = Offset(window.x.toFloat(), window.y.toFloat())
                    dragStartMousePos = getGlobalMousePosition()
                    // 拖拽开始时，初始化 targetPos、previousTargetPos 为当前窗口位置
                    targetPos = dragStartWindowPos
                    previousTargetPos = dragStartWindowPos

                    awaitPointerEventScope {
                        drag(down.id) { change ->
                            change.consume()
                            val currentMousePos = getGlobalMousePosition()
                            val delta = currentMousePos - dragStartMousePos
                            val newPos = dragStartWindowPos + delta
                            if (previousTargetPos == null || (newPos - previousTargetPos!!).getDistance() > threshold) {
                                targetPos = newPos
                                previousTargetPos = newPos
                            }
                        }
                    }
                    isDragging = false
                }
            }
        }
    ) {
        content()
    }
}
