package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OriginSongSimpleData(
    @SerialName("albumMeta")
    val albumMeta: AlbumMeta = AlbumMeta(),
    @SerialName("artists")
    val artists: List<Artist> = listOf(),
    @SerialName("name")
    val name: String = "",
    @SerialName("songId")
    val songId: Int = 0
)