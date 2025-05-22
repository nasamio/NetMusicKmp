package com.mio.bean


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistX(
    @SerialName("adType")
    val adType: Int = 0,
    @SerialName("algTags")
    val algTags: String? = String(),
    @SerialName("backgroundCoverId")
    val backgroundCoverId: Int = 0,
    @SerialName("backgroundCoverUrl")
    val backgroundCoverUrl: String? = String(),
    @SerialName("bannedTrackIds")
    val bannedTrackIds: String? = String(),
    @SerialName("bizExtInfo")
    val bizExtInfo: BizExtInfo = BizExtInfo(),
    @SerialName("cloudTrackCount")
    val cloudTrackCount: Int = 0,
    @SerialName("commentCount")
    val commentCount: Int = 0,
    @SerialName("commentThreadId")
    val commentThreadId: String = "",
    @SerialName("copied")
    val copied: Boolean = false,
    @SerialName("coverImgId")
    val coverImgId: Long = 0,
    @SerialName("coverImgId_str")
    val coverImgIdStr: String = "",
    @SerialName("coverImgUrl")
    val coverImgUrl: String = "",
    @SerialName("coverStatus")
    val coverStatus: Int = 0,
    @SerialName("createTime")
    val createTime: Long = 0,
    @SerialName("creator")
    val creator: CreatorXX = CreatorXX(),
    @SerialName("description")
    val description: String = "",
    @SerialName("detailPageTitle")
    val detailPageTitle: String? = String(),
    @SerialName("displayTags")
    val displayTags: String? = String(),
    @SerialName("displayUserInfoAsTagOnly")
    val displayUserInfoAsTagOnly: Boolean = false,
    @SerialName("distributeTags")
    val distributeTags: List<String?> = listOf(),
    @SerialName("englishTitle")
    val englishTitle: String? = String(),
    @SerialName("gradeStatus")
    val gradeStatus: String = "",
    @SerialName("highQuality")
    val highQuality: Boolean = false,
    @SerialName("historySharedUsers")
    val historySharedUsers: String? = String(),
    @SerialName("id")
    val id: Long = 0,
    @SerialName("mvResourceInfos")
    val mvResourceInfos: String? = String(),
    @SerialName("name")
    val name: String = "",
    @SerialName("newDetailPageRemixVideo")
    val newDetailPageRemixVideo: String? = String(),
    @SerialName("newImported")
    val newImported: Boolean = false,
    @SerialName("officialPlaylistType")
    val officialPlaylistType: String? = String(),
    @SerialName("opRecommend")
    val opRecommend: Boolean = false,
    @SerialName("ordered")
    val ordered: Boolean = false,
    @SerialName("playCount")
    val playCount: Int = 0,
    @SerialName("playlistType")
    val playlistType: String = "",
    @SerialName("privacy")
    val privacy: Int = 0,
    @SerialName("relateResType")
    val relateResType: String? = String(),
    @SerialName("remixVideo")
    val remixVideo: String? = String(),
    @SerialName("score")
    val score: String? = String(),
    @SerialName("shareCount")
    val shareCount: Int = 0,
    @SerialName("sharedUsers")
    val sharedUsers: String? = String(),
    @SerialName("specialType")
    val specialType: Int = 0,
    @SerialName("status")
    val status: Int = 0,
    @SerialName("subscribed")
    val subscribed: String? = String(),
    @SerialName("subscribedCount")
    val subscribedCount: Int = 0,
    @SerialName("subscribers")
    val subscribers: List<Subscriber> = listOf(),
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("titleImage")
    val titleImage: Int = 0,
    @SerialName("titleImageUrl")
    val titleImageUrl: String? = String(),
    @SerialName("trackCount")
    val trackCount: Int = 0,
    @SerialName("trackIds")
    val trackIds: List<TrackId> = listOf(),
    @SerialName("trackNumberUpdateTime")
    val trackNumberUpdateTime: Long = 0,
    @SerialName("trackUpdateTime")
    val trackUpdateTime: Long = 0,
    @SerialName("tracks")
    val tracks: List<Track> = listOf(),
    @SerialName("trialMode")
    val trialMode: Int = 0,
    @SerialName("updateFrequency")
    val updateFrequency: String? = String(),
    @SerialName("updateTime")
    val updateTime: Long = 0,
    @SerialName("userId")
    val userId: Int = 0,
    @SerialName("videoIds")
    val videoIds: String? = String(),
    @SerialName("videos")
    val videos: String? = String()
)