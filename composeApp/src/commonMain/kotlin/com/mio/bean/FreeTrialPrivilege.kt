package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FreeTrialPrivilege(
    @SerialName("cannotListenReason")
    val cannotListenReason: String? = null,
    @SerialName("freeLimitTagType")
    val freeLimitTagType: String? = null,
    @SerialName("listenType")
    val listenType: String? = null,
    @SerialName("playReason")
    val playReason: String? = null,
    @SerialName("resConsumable")
    val resConsumable: Boolean = false,
    @SerialName("userConsumable")
    val userConsumable: Boolean = false
)