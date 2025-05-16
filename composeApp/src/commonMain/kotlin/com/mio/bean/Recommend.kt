package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recommend(
    @SerialName("alg")
    val alg: String = "",
    @SerialName("copywriter")
    val copywriter: String = "",
    @SerialName("createTime")
    val createTime: Long = 0,
    @SerialName("creator")
    val creator: CreatorX = CreatorX(),
    @SerialName("id")
    val id: Long = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("picUrl")
    val picUrl: String = "",
    @SerialName("playcount")
    val playcount: Long = 0,
    @SerialName("trackCount")
    val trackCount: Int = 0,
    @SerialName("type")
    val type: Int = 0,
    @SerialName("userId")
    val userId: Long = 0
)