@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.mio.pages

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mio.bean.PlaylistX
import com.mio.components.FastSharedBounds
import com.mio.countStr
import com.mio.logcat
import com.mio.timeStr
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import netmusickmp.composeapp.generated.resources.Res
import netmusickmp.composeapp.generated.resources.ic_music
import netmusickmp.composeapp.generated.resources.ic_play
import org.jetbrains.compose.resources.painterResource

@ExperimentalSharedTransitionApi
@Composable
fun SharedTransitionScope.PlayListDetailUi(id: String?, animScope: AnimatedContentScope) {
    var playlist by remember { mutableStateOf(PlaylistX()) }

    LaunchedEffect(id) {
        KtorHelper.getPlaylistDetail(id.toString()).collect {
            if (it.code.isOk()) {
                playlist = it.playlist
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row {
            Spacer(Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .width(169.dp)
                    .height(169.dp)
                    .sharedBounds(
                        rememberSharedContentState(id.toString()),
                        animatedVisibilityScope = animScope,
                        enter = EnterTransition.None,
                        exit = ExitTransition.None,
//                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                    )
            ) {
                logcat("img" + id.toString())
                AsyncImage(
                    modifier = Modifier.fillMaxSize()
                        .clip(RoundedCornerShape(10.dp)),
                    model = playlist.coverImgUrl,
//                    placeholder = painterResource(Res.drawable.ic_placeholder),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
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
                        text = playlist.playCount.countStr(),
                        color = Color.White,
                        fontSize = 12.sp,
                        // 行高
                        lineHeight = 12.sp,
                    )
                }
            }
            Spacer(Modifier.width(20.dp))

            Box(modifier = Modifier.height(169.dp)) {
                Column {
                    FastSharedBounds(id.toString() + "_title", animScope) {
                        Text(
                            text = playlist.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            lineHeight = 20.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Spacer(Modifier.height(20.dp))
                    AnimatedVisibility(
                        true,
                        enter = fadeIn(
                            animationSpec = tween(
                                durationMillis = 2400,
                                delayMillis = 300,
                            )
                        )
                    ) {
                        Text(
                            text = playlist.description?.replace("\n", " ") ?: "",
                            color = Color.Black.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            // 行高
                            lineHeight = 14.sp,
                            modifier = Modifier.widthIn(max = 500.dp),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                }


                Column(
                    modifier = Modifier.align(Alignment.BottomStart)
                ) {
                    // 创建者
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            modifier = Modifier.size(24.dp)
                                .clip(CircleShape),
                            model = playlist.creator.avatarUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = playlist.creator.nickname,
                            color = Color.Black.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            // 行高
                            lineHeight = 12.sp,
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "${playlist.createTime.timeStr()}创建",
                            color = Color.Black.copy(alpha = 0.5f),
                            fontSize = 12.sp,
                            // 行高
                            lineHeight = 12.sp,
                        )
                    }
                    Spacer(Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(Color.Red, RoundedCornerShape(4.dp))
                            .padding(8.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(Res.drawable.ic_play),
                            contentDescription = null,
                            // 白色显示
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Text(
                            text = "播放全部",
                            color = Color.White,
                            fontSize = 12.sp,
                            // 行高
                            lineHeight = 12.sp,
                        )
                    }
                }
            }
        }
    }
}
