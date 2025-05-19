package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("accompany")
    val accompany: String? = null,
    @SerialName("auEff")
    val auEff: String? = null,
    @SerialName("br")
    val br: Long = 0,
    @SerialName("canExtend")
    val canExtend: Boolean = false,
    @SerialName("channelLayout")
    val channelLayout: String? = null,
    @SerialName("closedGain")
    val closedGain: Long = 0,
    @SerialName("closedPeak")
    val closedPeak: Long = 0,
    @SerialName("code")
    val code: Long = 0,
    @SerialName("effectTypes")
    val effectTypes: String? = null,
    @SerialName("encodeType")
    val encodeType: String = "",
    @SerialName("expi")
    val expi: Long = 0,
    @SerialName("fee")
    val fee: Long = 0,
    @SerialName("flag")
    val flag: Long = 0,
    @SerialName("freeTimeTrialPrivilege")
    val freeTimeTrialPrivilege: FreeTimeTrialPrivilege = FreeTimeTrialPrivilege(),
    @SerialName("freeTrialInfo")
    val freeTrialInfo: String? = null,
    @SerialName("freeTrialPrivilege")
    val freeTrialPrivilege: FreeTrialPrivilege = FreeTrialPrivilege(),
    @SerialName("gain")
    val gain: Long = 0,
    @SerialName("id")
    val id: Long = 0,
    @SerialName("level")
    val level: String = "",
    @SerialName("levelConfuse")
    val levelConfuse: String? = null,
    @SerialName("md5")
    val md5: String = "",
    @SerialName("message")
    val message: String? = null,
    @SerialName("musicId")
    val musicId: String = "",
    @SerialName("payed")
    val payed: Long = 0,
    @SerialName("peak")
    val peak: Double = 0.0,
    @SerialName("podcastCtrp")
    val podcastCtrp: String? = null,
    @SerialName("rightSource")
    val rightSource: Long = 0,
    @SerialName("size")
    val size: Long = 0,
    @SerialName("sr")
    val sr: Long = 0,
    @SerialName("time")
    val time: Long = 0,
    @SerialName("type")
    val type: String = "",
    @SerialName("uf")
    val uf: String? = null,
    @SerialName("url")
    val url: String = "",
    @SerialName("urlSource")
    val urlSource: Long = 0
)