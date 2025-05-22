package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Privilege(
    @SerialName("chargeInfoList")
    val chargeInfoList: List<ChargeInfo> = listOf(),
    @SerialName("code")
    val code: Int = 0,
    @SerialName("cp")
    val cp: Int = 0,
    @SerialName("cs")
    val cs: Boolean = false,
    @SerialName("dl")
    val dl: Int = 0,
    @SerialName("dlLevel")
    val dlLevel: String = "",
    @SerialName("dlLevels")
    val dlLevels: String? = String(),
    @SerialName("downloadMaxBrLevel")
    val downloadMaxBrLevel: String = "",
    @SerialName("downloadMaxbr")
    val downloadMaxbr: Int = 0,
    @SerialName("fee")
    val fee: Int = 0,
    @SerialName("fl")
    val fl: Int = 0,
    @SerialName("flLevel")
    val flLevel: String = "",
    @SerialName("flag")
    val flag: Int = 0,
    @SerialName("freeTrialPrivilege")
    val freeTrialPrivilege: FreeTrialPrivilegeX = FreeTrialPrivilegeX(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("ignoreCache")
    val ignoreCache: String? = String(),
    @SerialName("maxBrLevel")
    val maxBrLevel: String = "",
    @SerialName("maxbr")
    val maxbr: Int = 0,
    @SerialName("message")
    val message: String? = String(),
    @SerialName("paidBigBang")
    val paidBigBang: Boolean = false,
    @SerialName("payed")
    val payed: Int = 0,
    @SerialName("pc")
    val pc: String? = String(),
    @SerialName("pl")
    val pl: Int = 0,
    @SerialName("plLevel")
    val plLevel: String = "",
    @SerialName("plLevels")
    val plLevels: String? = String(),
    @SerialName("playMaxBrLevel")
    val playMaxBrLevel: String = "",
    @SerialName("playMaxbr")
    val playMaxbr: Int = 0,
    @SerialName("preSell")
    val preSell: Boolean = false,
    @SerialName("realPayed")
    val realPayed: Int = 0,
    @SerialName("rightSource")
    val rightSource: Int = 0,
    @SerialName("rscl")
    val rscl: String? = String(),
    @SerialName("sp")
    val sp: Int = 0,
    @SerialName("st")
    val st: Int = 0,
    @SerialName("subp")
    val subp: Int = 0,
    @SerialName("toast")
    val toast: Boolean = false
)