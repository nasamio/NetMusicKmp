package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackId(
    @SerialName("alg")
    val alg: String? = null,
    @SerialName("at")
    val at: Long = 0,
    @SerialName("dpr")
    val dpr: String? = null,
    @SerialName("f")
    val f: String? = null,
    @SerialName("id")
    val id: Int = 0,
    @SerialName("rcmdReason")
    val rcmdReason: String = "",
    @SerialName("rcmdReasonTitle")
    val rcmdReasonTitle: String = "",
    @SerialName("sc")
    val sc: String? = null,
    @SerialName("sr")
    val sr: String? = null,
    @SerialName("t")
    val t: Int = 0,
    @SerialName("uid")
    val uid: Int = 0,
    @SerialName("v")
    val v: Int = 0
)