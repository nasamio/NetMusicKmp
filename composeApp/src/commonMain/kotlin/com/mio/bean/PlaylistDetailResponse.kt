package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDetailResponse(
    @SerialName("code")
    val code: Int = 0,
    @SerialName("fromUserCount")
    val fromUserCount: Int = 0,
    @SerialName("fromUsers")
    val fromUsers: String? = String(),
    @SerialName("playlist")
    val playlist: PlaylistX = PlaylistX(),
    @SerialName("privileges")
    val privileges: List<Privilege> = listOf(),
    @SerialName("relatedVideos")
    val relatedVideos: String? = String(),
    @SerialName("resEntrance")
    val resEntrance: String? = String(),
    @SerialName("sharedPrivilege")
    val sharedPrivilege: String? = String(),
    @SerialName("songFromUsers")
    val songFromUsers: String? = String(),
    @SerialName("urls")
    val urls: String? = String()
)