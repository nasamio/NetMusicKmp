package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FreeTimeTrialPrivilege(
    @SerialName("remainTime")
    val remainTime: Int = 0,
    @SerialName("resConsumable")
    val resConsumable: Boolean = false,
    @SerialName("type")
    val type: Int = 0,
    @SerialName("userConsumable")
    val userConsumable: Boolean = false
)