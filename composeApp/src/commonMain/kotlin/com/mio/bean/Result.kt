package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("alg")
    val alg: String = "",
    @SerialName("canDislike")
    val canDislike: Boolean = false,
    @SerialName("copywriter")
    val copywriter: String = "",
    @SerialName("highQuality")
    val highQuality: Boolean = false,
    @SerialName("id")
    val id: Long = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("picUrl")
    val picUrl: String = "",
    @SerialName("playCount")
    val playCount: Long = 0,
    @SerialName("trackCount")
    val trackCount: Int = 0,
    @SerialName("trackNumberUpdateTime")
    val trackNumberUpdateTime: Long = 0,
    @SerialName("type")
    val type: Int = 0
)