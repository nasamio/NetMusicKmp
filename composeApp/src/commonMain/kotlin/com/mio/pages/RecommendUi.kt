package com.mio.pages

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
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.BitmapImage
import coil3.compose.AsyncImage
import coil3.toBitmap
import com.kmpalette.palette.graphics.Palette
import com.kmpalette.rememberDominantColorState
import com.mio.bean.Recommend
import com.mio.logcat
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import netmusickmp.composeapp.generated.resources.Res
import netmusickmp.composeapp.generated.resources.ic_app
import org.jetbrains.compose.resources.*
import org.jetbrains.skia.*
import org.jetbrains.skia.Paint

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
                .height(240.dp)
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

        // 列表
        LazyRow(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
        ) {
            itemsIndexed(data.list) { index, item ->
                PlayListItem(
                    // 这里控制item的高度 但会被外层高度限制
                    modifier = Modifier.width(180.dp)
                        .height(240.dp)
                        .padding(
                            horizontal = 10.dp,
                            vertical = 10.dp,
                        ),
                    data = item
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class, DelicateCoroutinesApi::class)
@Composable
fun PlayListItem(
    modifier: Modifier = Modifier,
    data: Recommend,
) {
    logcat("data:${data}")
    var dominaColor by remember { mutableStateOf<Color?>(null) }

    Box(modifier = modifier) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            model = data.picUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            onSuccess = { success ->
                // 需要一个image bitmap对象 先转换成byte数组 再调用 toImageBitmap
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

        // 底部背景色
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .align(Alignment.BottomCenter)
                .background(
                    color = dominaColor ?: Color.DarkGray,
                    shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
                ) // 半透明叠加层
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

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {

        }
    }
}


data class RecommendItem(
    val title: String,
    val list: List<Recommend>,
)


fun ImageBitmap.getDominantColor(): Color {
    // 读取像素ARGB Int数组
    val width = this.width
    val height = this.height
    // 获取所有像素
    val pixels = IntArray(width * height)
    this.readPixels(pixels, 0, width, 0, 0, width, height)
    // 颜色计数
    val colorCountMap = mutableMapOf<Int, Int>()
    pixels.forEach { pixel ->
        // 跳过透明度过低的颜色
        val alpha = (pixel shr 24) and 0xFF
        if (alpha < 128) return@forEach
        // 对颜色量化，每个通道保留高5位
        val r = ((pixel shr 16) and 0xFF) shr 3
        val g = ((pixel shr 8) and 0xFF) shr 3
        val b = (pixel and 0xFF) shr 3
        val quantColor = (r shl 10) or (g shl 5) or b
        colorCountMap[quantColor] = (colorCountMap[quantColor] ?: 0) + 1
    }
    if (colorCountMap.isEmpty()) {
        return Color.Black
    }
    // 频率最高颜色的量化值
    val dominantQuantColor = colorCountMap.maxByOrNull { it.value }!!.key
    // 还原颜色值（量化会丢失信息，只是近似）
    val r = ((dominantQuantColor shr 10) and 0x1F) shl 3
    val g = ((dominantQuantColor shr 5) and 0x1F) shl 3
    val b = (dominantQuantColor and 0x1F) shl 3
    return Color(r, g, b)
}

fun darkenColor(color: Color, factor: Float = 0.8f): Color {
    // factor < 1 会使颜色变暗，范围一般是 0..1
    return Color(
        red = (color.red * factor).coerceIn(0f, 1f),
        green = (color.green * factor).coerceIn(0f, 1f),
        blue = (color.blue * factor).coerceIn(0f, 1f),
        alpha = color.alpha
    )
}
