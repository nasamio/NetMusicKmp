package com.mio.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.BitmapImage
import coil3.compose.AsyncImage
import com.kmpalette.palette.graphics.Palette
import com.mio.bean.Recommend
import com.mio.countStr
import com.mio.logcat
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import netmusickmp.composeapp.generated.resources.Res
import netmusickmp.composeapp.generated.resources.ic_music
import org.jetbrains.compose.resources.*

@Composable
fun RecommendUi() {
    val recommendList = remember { mutableStateListOf<RecommendItem>() }
    val density = LocalDensity.current.density
    println("Current density: $density")

    LaunchedEffect(1) {
        KtorHelper.recommendResource().collect {
            if (it.code.isOk()) {
                logcat(
                    "featureFirst:${it.featureFirst},haveRcmdSongs:${it.haveRcmdSongs},recommend:${
                        it.recommend.joinToString {
                            it.name + " " + it.copywriter + " " + it.trackCount
                        }
                    }"
                )

                recommendList += RecommendItem(
                    title = "推荐歌单",
                    list = it.recommend
                )
            }
        }
    }

    recommendList.forEach {
        RecommendItemUi(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp),
            data = it,
        )
    }
}

@Composable
fun RecommendItemUi(modifier: Modifier, data: RecommendItem) {
    Column(
        modifier = modifier.padding(10.dp)
    ) {
        Row(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = data.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        // 关键修改：动态获取最大宽度，固定5个item宽度，高度宽度4/3比例
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val maxWidthPx = with(LocalDensity.current) { maxWidth.toPx() }
            val itemCountPerPage = 5
            val itemWidthPx = maxWidthPx / itemCountPerPage
            val itemHeightPx = itemWidthPx * 4f / 3f

            val itemWidth = with(LocalDensity.current) { itemWidthPx.toDp() }
            val itemHeight = with(LocalDensity.current) { itemHeightPx.toDp() }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(itemHeight + 20.dp) // 预留padding高度
            ) {
                itemsIndexed(data.list) { index, item ->
                    PlayListItem(
                        modifier = Modifier
                            .width(itemWidth)
                            .height(itemHeight)
                            .padding(horizontal = 10.dp, vertical = 10.dp),
                        data = item,
                        width = itemWidthPx.toInt(),
                        height = itemHeightPx.toInt(),
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class, DelicateCoroutinesApi::class)
@Composable
fun PlayListItem(
    modifier: Modifier = Modifier,
    data: Recommend,
    width: Int,
    height: Int,
) {
    logcat("data:${data}")
    var dominaColor by remember { mutableStateOf<Color?>(null) }

    // 这里构造更高清的图地址，参数根据需要调整（宽720，高960，比例4:3）
    val highResUrl = remember(data.picUrl) {
        if (data.picUrl.contains("?")) {
            data.picUrl.substringBefore("?") + "?param=${width}y${height}"
        } else {
            data.picUrl + "?param=${width}y${height}"
        }
    }

    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            model = highResUrl, // 使用高清图url加载
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onSuccess = { success ->
                // 解析图片，提取主色用于底部背景
                val bitmap = (success.result.image as BitmapImage).bitmap.asComposeImageBitmap()
                GlobalScope.launch {
                    bitmap.let {
                        Palette.from(it)
                            .generate()
                            .dominantSwatch
                            ?.rgb
                            .let {
                                logcat("color2:$it")
                                dominaColor = it?.toLong()?.let { it1 -> darkenColor(Color(it1)) }
                            }
                    }
                }
            }
        )

        // 右上角
        Row(
            modifier = Modifier.align(Alignment.TopEnd)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(12.dp),
                painter = painterResource(Res.drawable.ic_music),
                contentDescription = null,
                // 白色显示
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White)
            )
            Text(
                text = data.playcount.countStr(),
                color = Color.White,
                fontSize = 12.sp,
                // 行高
                lineHeight = 12.sp,
            )
        }

        // 底部背景色，使用提取的主色或者默认色
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = dominaColor ?: Color.DarkGray,
                    shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = data.name,
                fontSize = 16.sp,
                color = Color.White,
                lineHeight = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


data class RecommendItem(
    val title: String,
    val list: List<Recommend>,
)

fun darkenColor(color: Color, factor: Float = 0.8f): Color {
    // factor < 1 会使颜色变暗，范围一般是 0..1
    return Color(
        red = (color.red * factor).coerceIn(0f, 1f),
        green = (color.green * factor).coerceIn(0f, 1f),
        blue = (color.blue * factor).coerceIn(0f, 1f),
        alpha = color.alpha
    )
}
