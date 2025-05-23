package com.mio.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Track(
    @SerialName("a")
    val a: String? = null,

    @SerialName("additionalTitle")
    val additionalTitle: String? = null,

    @SerialName("al")
    val al: Al? = null,

    @SerialName("alg")
    val alg: String? = null,

    @SerialName("alia")
    val alia: List<String?>? = null,

    @SerialName("ar")
    val ar: List<Ar>? = null,

    @SerialName("awardTags")
    val awardTags: String? = null,

    @SerialName("cd")
    val cd: String? = null,

    @SerialName("cf")
    val cf: String? = null,

    @SerialName("copyright")
    val copyright: Int = 0,

    @SerialName("cp")
    val cp: Int = 0,

    @SerialName("crbt")
    val crbt: String? = null,

    @SerialName("displayReason")
    val displayReason: String? = null,

    @SerialName("displayTags")
    val displayTags: String? = null,

    @SerialName("djId")
    val djId: Int = 0,

    @SerialName("dt")
    val dt: Int = 0,

    @SerialName("entertainmentTags")
    val entertainmentTags: String? = null,

    @SerialName("fee")
    val fee: Int = 0,

    @SerialName("ftype")
    val ftype: Int = 0,

    @SerialName("h")
    val h: H? = null,

    @SerialName("hr")
    val hr: SongResponse.Song.Hr? = null,

    @SerialName("id")
    val id: Long = 0,

    @SerialName("l")
    val l: L? = null,

    @SerialName("m")
    val m: M? = null,

    @SerialName("mainTitle")
    val mainTitle: String? = null,

    @SerialName("mark")
    val mark: Long = 0,

    @SerialName("mst")
    val mst: Int = 0,

    @SerialName("mv")
    val mv: Int = 0,

    @SerialName("name")
    val name: String? = null,

    @SerialName("no")
    val no: Int = 0,

    @SerialName("noCopyrightRcmd")
    val noCopyrightRcmd: NoCopyrightRcmd? = null,

    @SerialName("originCoverType")
    val originCoverType: Int = 0,

    @SerialName("originSongSimpleData")
    val originSongSimpleData: OriginSongSimpleData? = null,

    @SerialName("pop")
    val pop: Int = 0,

    @SerialName("pst")
    val pst: Int = 0,

    @SerialName("publishTime")
    val publishTime: Long = 0,

    @SerialName("resourceState")
    val resourceState: Boolean = false,

    @SerialName("rt")
    val rt: String? = null,

    @SerialName("rtUrl")
    val rtUrl: String? = null,

    @SerialName("rtUrls")
    val rtUrls: List<String?>? = null,

    @SerialName("rtype")
    val rtype: Int = 0,

    @SerialName("rurl")
    val rurl: String? = null,

    @SerialName("s_id")
    val sId: Int = 0,

    @SerialName("single")
    val single: Int = 0,

    @SerialName("songJumpInfo")
    val songJumpInfo: String? = null,

    @SerialName("sq")
    val sq: Sq? = null,

    @SerialName("st")
    val st: Int = 0,

    @SerialName("t")
    val t: Int = 0,

    @SerialName("tagPicList")
    val tagPicList: String? = null,

    @SerialName("tns")
    val tns: List<String>? = null,

    @SerialName("v")
    val v: Int = 0,

    @SerialName("version")
    val version: Int = 0,
)
