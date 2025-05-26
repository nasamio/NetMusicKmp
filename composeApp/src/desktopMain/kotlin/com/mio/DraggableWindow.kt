import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.launch
import java.awt.GraphicsEnvironment
import java.awt.MouseInfo
import kotlin.math.roundToInt

fun getGlobalMousePosition(): Offset {
    val point = MouseInfo.getPointerInfo().location
    return Offset(point.x.toFloat(), point.y.toFloat())
}

@Composable
fun ApplicationScope.DraggableWindow(
    onCloseRequest: () -> Unit = this::exitApplication,
    title: String = "",
    icon: Painter? = null,
    windowSize: DpSize = DpSize(1056.dp, 752.dp),
    position: Alignment = Alignment.Center,
    content: @Composable () -> Unit = {},
) {
    Window(
        onCloseRequest = onCloseRequest,
        title = title,
        icon = icon,
        undecorated = true,
        transparent = true,
        state = WindowState(
            size = windowSize,
            position = WindowPosition.Aligned(position)
        ),
    ) {

        var dragStartWindowPos by remember { mutableStateOf(Offset.Zero) }
        var dragStartMousePos by remember { mutableStateOf(Offset.Zero) }

        // 目标位置与上一次实际设置的窗口位置，用于阈值判断
        var targetPos by remember { mutableStateOf(Offset(window.x.toFloat(), window.y.toFloat())) }
        var previousTargetPos by remember { mutableStateOf(targetPos) }

        val animX = remember { Animatable(window.x.toFloat()) }
        val animY = remember { Animatable(window.y.toFloat()) }
        val coroutineScope = rememberCoroutineScope()

        var isDragging by remember { mutableStateOf(false) }

        // 位置更新阈值，单位像素，低于此值不更新位置避免抖动 这个阈值越大变化需求越明显，抖动进一步减少，但响应可能略显迟钝，建议0.5~2之间实验
        val threshold = 1.8f

        LaunchedEffect(targetPos, isDragging) {
            if (isDragging) {
                // 拖拽时立即跳转，不动画
                coroutineScope.launch { animX.snapTo(targetPos.x) }
                coroutineScope.launch { animY.snapTo(targetPos.y) }
            } else {
                // 拖拽结束后使用 spring 动画平滑移动
                coroutineScope.launch {
                    animX.animateTo(
                        targetPos.x,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
                coroutineScope.launch {
                    animY.animateTo(
                        targetPos.y,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }

        LaunchedEffect(animX.value, animY.value) {
            window.setLocation(animX.value.roundToInt(), animY.value.roundToInt())
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .width(windowSize.width)
                    .height(windowSize.height + 80.dp)
                    .align(position)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(Color.DarkGray)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = {
                                    isDragging = true
                                    dragStartWindowPos = Offset(window.x.toFloat(), window.y.toFloat())
                                    dragStartMousePos = getGlobalMousePosition()
                                },
                                onDrag = { _, _ -> // 不用 dragAmount，改用绝对鼠标坐标
                                    val currentMousePos = getGlobalMousePosition()
                                    val deltaMouse = currentMousePos - dragStartMousePos

                                    val newPos = dragStartWindowPos + deltaMouse
                                    // 只有超过阈值才更新，避免小范围抖动
                                    if ((newPos - previousTargetPos).getDistance() > threshold) {
                                        targetPos = newPos
                                        previousTargetPos = newPos
                                    }
                                },
                                onDragEnd = {
                                    isDragging = false
                                },
                                onDragCancel = {
                                    isDragging = false
                                }
                            )
                        }
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
                    content()
                }
            }
        }
    }
}

fun getCurrentScreenSize(window: java.awt.Window): java.awt.Dimension {
    val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
    val gd = ge.screenDevices.firstOrNull { device ->
        val bounds = device.defaultConfiguration.bounds
        bounds.contains(window.location)
    } ?: ge.defaultScreenDevice
    val bounds = gd.defaultConfiguration.bounds
    return bounds.size
}
