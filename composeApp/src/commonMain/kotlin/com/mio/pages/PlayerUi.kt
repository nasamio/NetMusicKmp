package com.mio.pages

import HoveredIcon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mio.Player
import com.mio.bean.SongResponse
import com.mio.logcat
import netmusickmp.composeapp.generated.resources.*
import netmusickmp.composeapp.generated.resources.Res
import netmusickmp.composeapp.generated.resources.ic_comment
import netmusickmp.composeapp.generated.resources.ic_plus
import org.jetbrains.compose.resources.painterResource
import java.time.format.TextStyle

@Composable
fun PlayerUi(modifier: Modifier) {
    Box(modifier = modifier) {
        // mini播放器
        MiniPlayer(
            modifier = Modifier.height(80.dp)
                .fillMaxWidth()
                .background(Color(0xfffafafa))
                .drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    val y = strokeWidth / 2  // 偏移半个线宽，避免锯齿和模糊
                    drawLine(
                        color = Color(0xffe5e6e8),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )

                }
                .align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun MiniPlayer(modifier: Modifier) {
    val currentIndex = Player.currentIndex.collectAsState()
    val currentSong = Player.playList.value.let { if (it.isEmpty()) null else it[currentIndex.value] }

    logcat("url:${currentSong?.al?.picUrl}")

    Box(
        modifier = modifier
    ) {
        // 左侧
        MiniLeft(
            modifier = Modifier.align(Alignment.CenterStart)
                .padding(horizontal = 28.dp),
            currentSong = currentSong,
        )


        // 播放器操作
        MiniCenter(modifier = Modifier.align(Alignment.Center))


        // 右侧菜单
        MiniRight(
            modifier = Modifier.align(Alignment.CenterEnd)
                .padding(horizontal = 28.dp),
            currentSong = currentSong,
        )
    }
}

@Composable
fun MiniLeft(modifier: Modifier, currentSong: SongResponse.Song?) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(60.dp)
                .clip(CircleShape)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.size(40.dp)
                    .clip(CircleShape),
                model = currentSong?.al?.picUrl ?: "",
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        }

        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                Text(
                    text = currentSong?.name?.trim() ?: "",
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                )
                Text(
                    text = ("-" + currentSong?.ar?.get(0)?.name) ?: "",
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(start = 10.dp),
                    color = Color.Black.copy(alpha = .45f),
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HoveredIcon(
                    painter = painterResource(Res.drawable.ic_plus),
                    modifier = Modifier.size(20.dp),
                    onClick = {
                    }
                )

                Spacer(Modifier.width(20.dp))
                HoveredIcon(
                    painter = painterResource(Res.drawable.ic_comment),
                    modifier = Modifier.size(20.dp),
                    onClick = {
                    }
                )
                Spacer(Modifier.width(20.dp))

                HoveredIcon(
                    painter = painterResource(Res.drawable.ic_like),
                    modifier = Modifier.size(20.dp),
                    onClick = {
                    }
                )
                Spacer(Modifier.width(20.dp))

                HoveredIcon(
                    painter = painterResource(Res.drawable.ic_more),
                    modifier = Modifier.size(20.dp),
                    onClick = {
                    }
                )
            }
        }
    }
}

@Composable
fun MiniCenter(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val margin = 20.dp
            HoveredIcon(
                painter = painterResource(Res.drawable.ic_like),
                modifier = Modifier.size(20.dp),
                onClick = {
                }
            )
            Spacer(Modifier.width(margin))
            HoveredIcon(
                painter = painterResource(Res.drawable.ic_previous),
                modifier = Modifier.size(20.dp),
                onClick = {
                }
            )
            Spacer(Modifier.width(margin))
            Box(
                modifier = Modifier.size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xfffd3c57)),
                contentAlignment = Alignment.Center,
            ) {
                HoveredIcon(
                    painter = painterResource(Res.drawable.ic_play),
                    modifier = Modifier.size(36.dp),
                    hoverColor = Color.White,
                    hoverTint = Color.White,
                    normalTint = Color.White,
                    onClick = {
                    },
                )
            }
            Spacer(Modifier.width(margin))
            HoveredIcon(
                painter = painterResource(Res.drawable.ic_next),
                modifier = Modifier.size(20.dp),
                onClick = {
                }
            )
            Spacer(Modifier.width(margin))
            HoveredIcon(
                painter = painterResource(Res.drawable.ic_recycler),
                modifier = Modifier.size(20.dp),
                onClick = {
                }
            )
        }

        Spacer(Modifier.height(10.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val margin = 10.dp
            Text(
                text = "00:00",
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = Color.Black.copy(alpha = .45f)
            )
            Spacer(Modifier.width(margin))
            // 进度条
            LinearProgressIndicator(
                modifier = Modifier.width(300.dp)
                    .height(2.dp)
            )
            Spacer(Modifier.width(margin))
            Text(
                text = "00:00",
                fontSize = 12.sp,
                lineHeight = 12.sp,
                color = Color.Black.copy(alpha = .45f)
            )
        }
    }
}

@Composable
fun MiniRight(modifier: Modifier, currentSong: SongResponse.Song?) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val margin = 10.dp
        HoveredIcon(
            painter = painterResource(Res.drawable.ic_volumn),
            modifier = Modifier.size(32.dp),
            onClick = {
            }
        )
        Spacer(Modifier.width(margin))
        HoveredIcon(
            painter = painterResource(Res.drawable.ic_menu),
            modifier = Modifier.size(32.dp),
            onClick = {
            }
        )
    }
}

