package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendResourceResponse(
    @SerialName("code")
    val code: Int = 0,
    @SerialName("featureFirst")
    val featureFirst: Boolean = false,
    @SerialName("haveRcmdSongs")
    val haveRcmdSongs: Boolean = false,
    @SerialName("recommend")
    val recommend: List<Recommend> = listOf()
)