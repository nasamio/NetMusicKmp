package com.mio.pages

import HoveredBox
import HoveredIcon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mio.AppHelper
import com.mio.logcat
import netmusickmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RightTop(modifier: Modifier) {
    val profile = AppHelper.profile.collectAsState()

    Row(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        HoveredBox(
            modifier = Modifier.width(27.dp)
                .height(36.dp),
            onClick = {
                logcat("点击了菜单")

                AppHelper.popBackStack()
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_back),
                contentDescription = "back",
                tint = Color.Black.copy(alpha = .5f),
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // 搜索框
        val color1 = Color(0xFF9c88ff).copy(alpha = .1f)
        val color2 = Color(0xFFf368e0).copy(alpha = .1f)
        Row(
            modifier = Modifier
                .height(36.dp)
                .weight(1f)
                .border(
                    1.dp,
                    Color.Gray.copy(alpha = 0.3f),
                    RoundedCornerShape(6.dp)
                )
                .drawWithCache {
                    val gradient = Brush.linearGradient(
                        colors = listOf(color1, color2),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height)
                    )
                    onDrawBehind {
                        drawRoundRect(
                            brush = gradient,
                            size = size,
                            cornerRadius = CornerRadius(6.dp.toPx())
                        )
                    }
                }
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(6.dp)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HoveredIcon(
                painter = painterResource(Res.drawable.ic_search),
                modifier = Modifier.size(20.dp),
                onClick = {
                    logcat("点击了搜索")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))

            var text by remember { mutableStateOf("") }
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(10.dp))

            HoveredIcon(
                painter = painterResource(Res.drawable.ic_clear),
                modifier = Modifier.size(20.dp),
                onClick = {
                    logcat("点击了删除")
                }
            )
        }


        Spacer(modifier = Modifier.width(10.dp))

        // 麦克风
        HoveredBox(
            modifier = Modifier.width(36.dp)
                .height(36.dp),
            onClick = {
                logcat("点击了麦克风")
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_mic),
                contentDescription = "Mic",
                tint = Color.Black.copy(alpha = .5f),
                modifier = Modifier.size(16.dp)
            )
        }

        // 头像
        Spacer(modifier = Modifier.width(20.dp))
        logcat("头像地址：${AppHelper.profile.value?.avatarUrl}")
        AsyncImage(
            model = profile.value?.avatarUrl,
            contentDescription = "头像",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(28.dp)
                .clip(CircleShape)
        )
        // 用户名
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = profile.value?.nickname ?: "未登录",
            style = TextStyle(color = Color.Black),
            color = Color.Black.copy(alpha = .8f),
        )

        Image(
            painter = painterResource(Res.drawable.ic_vip),
            contentDescription = "vip",
            modifier = Modifier.height(28.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))

        // 右侧的其他图标

        HoveredIcon(
            painter = painterResource(Res.drawable.ic_down),
            modifier = Modifier.size(20.dp),
            onClick = {
                logcat("点击了下")
            }
        )

        val margin: @Composable () -> Unit = {
            Spacer(modifier = Modifier.width(12.dp))
        }

        margin()
        HoveredIcon(
            painter = painterResource(Res.drawable.ic_mail),
            modifier = Modifier.size(20.dp),
            onClick = {
            }
        )

        margin()

        HoveredIcon(
            painter = painterResource(Res.drawable.ic_sun),
            modifier = Modifier.size(20.dp),
            onClick = {
            }
        )

//        margin()
//
//
//        HoveredIcon(
//            painter = painterResource(Res.drawable.ic_min),
//            modifier = Modifier.size(20.dp),
//            onClick = {
//            }
//        )
//
//        margin()
//
//
//        HoveredIcon(
//            painter = painterResource(Res.drawable.ic_max),
//            modifier = Modifier.size(20.dp),
//            onClick = {
//            }
//        )
//
//        margin()
//
//
//        HoveredIcon(
//            painter = painterResource(Res.drawable.ic_close),
//            modifier = Modifier.size(20.dp),
//            onClick = {
//            }
//        )
    }
}