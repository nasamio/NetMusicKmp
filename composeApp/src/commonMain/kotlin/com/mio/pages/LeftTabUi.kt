package com.mio.pages

import HoveredBox
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mio.AppHelper
import com.mio.logcat
import defaultHoveredColor
import netmusickmp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

private val leftList = listOf(
    LeftTabItem("推荐", Res.drawable.ic_recommand, "recommend"),
    LeftTabItem("漫游", Res.drawable.ic_radio, "radio"),
    LeftTabItem("关注", Res.drawable.ic_follow, "follow"),

    LeftTabItem("我喜欢的音乐", Res.drawable.ic_heart, "like", true),
    LeftTabItem("最近播放", Res.drawable.ic_resent, "recent"),
    LeftTabItem("我的收藏", Res.drawable.ic_collect, "collect"),
    LeftTabItem("本地音乐", Res.drawable.ic_local, "local"),
)


@Composable
fun LeftTabUi(modifier: Modifier) {
    Column(modifier = modifier) {
        TabTitleSubItemUi(
            modifier = Modifier.fillMaxWidth()
                .height(80.dp),
            text = "Miosic",
            res = Res.drawable.jp_main,
        )

        var selectPos by remember { mutableStateOf(0) }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(horizontal = 8.dp),
        ) {
            itemsIndexed(leftList) { index, item ->
                TabGroupUi(
                    modifier = Modifier,
                    hasDividerAbove = item.hasDividerAbove,
                ) {
                    TabSubItemUi(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.text,
                        res = item.icon,
                        fSize = 16.sp,
                        iSize = 20.dp,
                        selected = index == selectPos,
                    ) {
                        selectPos = index

                        // 跳转倒对应页面
                        AppHelper.navigate(item.route)
                    }
                }
            }
        }
    }
}

@Composable
fun TabGroupUi(
    modifier: Modifier = Modifier,
    hasDividerAbove: Boolean = false,
    content: @Composable ColumnScope.() -> Unit,
) {
    // 分割线
    if (hasDividerAbove) Divider(
        color = Color.LightGray.copy(alpha = .5f),
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
    )
    Column(
        modifier = modifier.padding(2.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun TabSubItemUi(
    modifier: Modifier = Modifier,
    text: String,
    res: DrawableResource = Res.drawable.ic_void,
    fSize: TextUnit = 14.sp,
    iSize: Dp = 16.dp,
    selected: Boolean,
    onClick: () -> Unit = {},
) {

    val selectBgColor = Color(0xfffc3d49)
    HoveredBox(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            logcat("left tab click:$text")
            onClick()
        },
        borderStroke = null,
        hoverColor = if (selected) selectBgColor else defaultHoveredColor,
        defaultColor = if (selected) selectBgColor else Color.Transparent,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,  // 图标和文本间隔8dp
            modifier = modifier.padding(10.dp)
        ) {
            val itemColor = if (selected) Color.White else Color.Black.copy(alpha = .6f)

            Icon(
                painter = painterResource(res),
                contentDescription = text,  // 用 text 作为描述更语义化
                tint = itemColor,
                modifier = Modifier.size(iSize)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = fSize,
                color = itemColor,
                lineHeight = fSize * 1.2 // 解决上下不对齐
            )

        }
    }
}

@Composable
fun TabTitleSubItemUi(
    text: String,
    res: DrawableResource = Res.drawable.ic_void,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(res),
                contentDescription = text,  // 用 text 作为描述更语义化
                tint = Color.Unspecified,
                modifier = Modifier.size(48.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 28.sp,
                color = Color.DarkGray
            )
        }
    }
}

data class LeftTabItem(
    val text: String,
    val icon: DrawableResource,
    val route: String,
    val hasDividerAbove: Boolean = false, // 是否在上方添加分割线
)