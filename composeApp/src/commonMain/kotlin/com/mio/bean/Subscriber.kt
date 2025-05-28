package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Subscriber(
    @SerialName("accountStatus")
    val accountStatus: Int = 0,
    @SerialName("anchor")
    val anchor: Boolean = false,
    @SerialName("authStatus")
    val authStatus: Int = 0,
    @SerialName("authenticationTypes")
    val authenticationTypes: Int = 0,
    @SerialName("authority")
    val authority: Int = 0,
    @SerialName("avatarDetail")
    val avatarDetail: AvatarDetail? = null,
    @SerialName("avatarImgId")
    val avatarImgId: Long = 0,
    @SerialName("avatarImgIdStr")
    val avatarImgIdStr: String = "",
    @SerialName("avatarImgId_str")
    val avatarImgIdStr2: String = "",
    @SerialName("avatarUrl")
    val avatarUrl: String = "",
    @SerialName("backgroundImgId")
    val backgroundImgId: Long = 0,
    @SerialName("backgroundImgIdStr")
    val backgroundImgIdStr: String = "",
    @SerialName("backgroundUrl")
    val backgroundUrl: String? = null,
    @SerialName("birthday")
    val birthday: Int = 0,
    @SerialName("city")
    val city: Int = 0,
    @SerialName("defaultAvatar")
    val defaultAvatar: Boolean = false,
    @SerialName("description")
    val description: String = "",
    @SerialName("detailDescription")
    val detailDescription: String = "",
    @SerialName("djStatus")
    val djStatus: Int = 0,
    @SerialName("expertTags")
    val expertTags: String? = null,
    @SerialName("experts")
    val experts: String? = null,
    @SerialName("followed")
    val followed: Boolean = false,
    @SerialName("gender")
    val gender: Int = 0,
    @SerialName("mutual")
    val mutual: Boolean = false,
    @SerialName("nickname")
    val nickname: String = "",
    @SerialName("province")
    val province: Int = 0,
    @SerialName("remarkName")
    val remarkName: String? = null,
    @SerialName("signature")
    val signature: String = "",
    @SerialName("userId")
    val userId: Long = 0,
    @SerialName("userType")
    val userType: Int = 0,
    @SerialName("vipType")
    val vipType: Int = 0,
) {
    @Serializable
    class AvatarDetail
}