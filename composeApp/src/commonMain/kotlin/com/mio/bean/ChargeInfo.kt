package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChargeInfo(
    @SerialName("chargeMessage")
    val chargeMessage: String? = null,
    @SerialName("chargeType")
    val chargeType: Int = 0,
    @SerialName("chargeUrl")
    val chargeUrl: String? = null,
    @SerialName("rate")
    val rate: Int = 0
)