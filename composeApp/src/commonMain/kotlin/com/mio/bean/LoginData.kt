package com.mio.bean

import kotlinx.serialization.Serializable

@Serializable
data class LoginData(
    val code: Int?,
    val account: Account?,
    val profile: Profile?
)