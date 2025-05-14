package com.mio.pages

import HoveredBox
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
import com.mio.logcat
import netmusickmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun leftTabUi() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        tabTitleSubItemUi("Miosic", Res.drawable.jp_main, 28.sp, 48.dp)
        tabGroupUi {
            tabSubItemUi("推荐", Res.drawable.ic_recommand)
            tabSubItemUi("漫游", Res.drawable.ic_radio)
            tabSubItemUi("关注", Res.drawable.ic_follow)
        }
        tabGroupUi {
            tabSubItemUi("我喜欢的音乐", Res.drawable.ic_heart)
            tabSubItemUi("最近播放", Res.drawable.ic_resent)
            tabSubItemUi("我的收藏", Res.drawable.ic_collect)
            tabSubItemUi("本地音乐", Res.drawable.ic_local)
        }
    }
}

@Composable
fun tabGroupUi(
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
fun tabSubItemUi(
    text: String,
    res: DrawableResource = Res.drawable.ic_void,
    fSize: TextUnit = 14.sp,
    iSize: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    HoveredBox(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            logcat("left tab click")
        },
        borderStroke = null
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),  // 图标和文本间隔8dp
            modifier = modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(res),
                contentDescription = text,  // 用 text 作为描述更语义化
                tint = Color.Unspecified,
                modifier = Modifier.size(iSize)
            )
            Text(
                text = text,
                fontSize = fSize,
                modifier = Modifier.offset(y = (-2).dp)
            )
        }
    }
}

@Composable
fun tabTitleSubItemUi(
    text: String,
    res: DrawableResource = Res.drawable.ic_void,
    fSize: TextUnit = 14.sp,
    iSize: Dp = 16.dp,
    modifier: Modifier = Modifier
)
{
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),  // 图标和文本间隔8dp
        modifier = modifier.padding(4.dp)
    ) {
        Icon(
            painter = painterResource(res),
            contentDescription = text,  // 用 text 作为描述更语义化
            tint = Color.Unspecified,
            modifier = Modifier.size(iSize)
        )
        Text(
            text = text,
            fontSize = fSize,
            modifier = Modifier.offset(y = (-2).dp)
        )
    }
}