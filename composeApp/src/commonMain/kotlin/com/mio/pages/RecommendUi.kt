@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.mio.pages

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.mio.*
import hoverListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import netmusickmp.composeapp.generated.resources.Res
import netmusickmp.composeapp.generated.resources.ic_music
import org.jetbrains.compose.resources.*

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.RecommendUi(animScope: AnimatedContentScope) {
    val recommendList = MemoryDataHelper.recommendList.collectAsState()
    val density = LocalDensity.current.density


    println("Current density: $density")

    LaunchedEffect(Unit) {
        MemoryDataHelper.requestRecommendList()
    }

    LazyColumn {
        items(recommendList.value.size) { index ->
            RecommendItemUi(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 20.dp),
                data = recommendList.value[index],
                animScope = animScope,
            )
        }
    }
}

@Composable
fun SharedTransitionScope.RecommendItemUi(
    modifier: Modifier,
    data: RecommendItem,
    animScope: AnimatedContentScope,
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp)
    ) {
        Row(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = data.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
            )
        }


        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(data.list) { index, item ->
                var hovered by remember { mutableStateOf(false) }
                var shape = remember { mutableStateOf(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)) }

                PlayListItem(
                    modifier = Modifier
                        .hoverListener {
                            hovered = it
                        }
                        .clickable {
                            shape.value = RoundedCornerShape(8.dp)
                            // 跳转到详情页
                            AppHelper.navigate(Page.PlayListDetail.route + "/${item.id}")
                        }
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    data = item,
                    animScope = animScope,
                    shape = shape.value,
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class, DelicateCoroutinesApi::class)
@Composable
fun SharedTransitionScope.PlayListItem(
    modifier: Modifier = Modifier,
    data: SongList,
    animScope: AnimatedContentScope,
    shape: RoundedCornerShape,
) {
    logcat("RecommendUI PlayListItem data:${data}")
    var dominaColor by remember { mutableStateOf<Color?>(null) }

    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.width(140.dp)
                .height(190.dp)
        ) {
            Box(
                modifier = Modifier
                    .sharedBounds(
                        // 使用shared bounds 必须都是外层的box
                        rememberSharedContentState(data.id.toString()),
                        animatedVisibilityScope = animScope,
                        enter = EnterTransition.None,
                        exit = ExitTransition.None,
//                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    )
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(shape),
                    model = data.picUrl,
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
//                                logcat("color2:$it")
                                        dominaColor = it?.toLong()?.let { it1 -> darkenColor(Color(it1)) }
                                    }
                            }
                        }
                    }
                )
            }

            // 底部纯色
            // 底部背景色，使用提取的主色或者默认色
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(
                        color = dominaColor ?: Color.DarkGray,
                        shape = RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = data.name,
                    fontSize = 16.sp,
                    color = Color.White,
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

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
                text = data.playCount.countStr(),
                color = Color.White,
                fontSize = 12.sp,
                // 行高
                lineHeight = 12.sp,
            )
        }
    }
}


data class RecommendItem(
    val title: String,
    val list: List<SongList>,
)

data class SongList(
    val id: Long,
    val name: String,
    val picUrl: String,
    val playCount: Long,
) {
    override fun toString(): String {
        return "SongList(id=$id, name='$name', picUrl='$picUrl', playcount=$playCount)"
    }
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
