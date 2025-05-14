import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HoveredBox(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(6.dp),
    hoverColor: Color = Color(0xffe2e5e9),
    borderStroke: BorderStroke? = BorderStroke(0.5.dp, Color.Gray.copy(alpha = 0.3f)),
    onClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit,
) {
    val hovered = remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .border(borderStroke ?: BorderStroke(0.dp, Color.Transparent), shape)
            .clip(shape)
            .background(if (hovered.value) hoverColor else Color.Transparent)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { onClick() })
            }
            .pointerMoveFilter(
                onEnter = {
                    hovered.value = true
                    false
                },
                onExit = {
                    hovered.value = false
                    false
                }
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}
