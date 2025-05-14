package com.mio.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import netmusickmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LeftTabUi() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabSubItemUi("Miosic", Res.drawable.jp_main, 28.sp, 64.dp)
        TabGroupUi {
            TabSubItemUi("推荐", Res.drawable.ic_recommand)
            TabSubItemUi("漫游", Res.drawable.ic_radio)
            TabSubItemUi("关注", Res.drawable.ic_follow)
        }
        TabGroupUi {
            TabSubItemUi("我喜欢的音乐", Res.drawable.ic_heart)
            TabSubItemUi("最近播放", Res.drawable.ic_resent)
            TabSubItemUi("我的收藏", Res.drawable.ic_collect)
            TabSubItemUi("本地音乐", Res.drawable.ic_local)
        }
    }
}

@Composable
fun TabGroupUi(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    // 分割线
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth().padding(2.dp)
    )
    Column(
        modifier = modifier.padding(6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun TabSubItemUi(
    text: String,
    res: DrawableResource = Res.drawable.ic_void,
    fSize: TextUnit = 18.sp,
    iSize: Dp = 32.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically, // 垂直居中
        modifier = Modifier.padding(4.dp)
    ) {
        Icon(
            painter = painterResource(res),
            contentDescription = "icon_main",
            tint = Color.Unspecified,
            modifier = Modifier.size(iSize).padding(8.dp),
        )
        Text(
            text,
            fontSize = fSize
        )
    }
}