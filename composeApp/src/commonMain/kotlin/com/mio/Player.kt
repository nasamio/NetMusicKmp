package com.mio

import com.mio.bean.SongResponse
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 播放器
 */
object Player {
    val currentSong = MutableStateFlow<SongResponse.Song?>(null)

}