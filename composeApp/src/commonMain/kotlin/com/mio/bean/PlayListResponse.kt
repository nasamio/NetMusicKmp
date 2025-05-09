package com.mio.bean

import com.mio.bean.Playlist
import kotlinx.serialization.Serializable

@Serializable
data class PlayListResponse(
    val code: Int?,
    val more: Boolean?,
    val playlist: List<Playlist>
)