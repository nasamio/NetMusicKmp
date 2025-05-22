package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonalRecommendResponse(
    @SerialName("category")
    val category: Int = 0,
    @SerialName("code")
    val code: Int = 0,
    @SerialName("hasTaste")
    val hasTaste: Boolean = false,
    @SerialName("result")
    val result: List<Result> = listOf()
)