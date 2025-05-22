package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class L(
    @SerialName("br")
    val br: Int = 0,
    @SerialName("fid")
    val fid: Int = 0,
    @SerialName("size")
    val size: Int = 0,
    @SerialName("sr")
    val sr: Int = 0,
    @SerialName("vd")
    val vd: Int = 0
)