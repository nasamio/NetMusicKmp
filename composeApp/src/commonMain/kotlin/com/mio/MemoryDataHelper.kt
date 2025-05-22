package com.mio

import com.mio.pages.RecommendItem
import com.mio.pages.SongList
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 把网络请求 而不需要本地存储的数据在内存层面持久化 防止来回切换页面重复请求数据
 */
object MemoryDataHelper {
    val recommendList = MutableStateFlow(emptyList<RecommendItem>())

    suspend fun requestRecommendList() {
        if (recommendList.value.isNotEmpty()) return

        // 日推
        KtorHelper.recommendResource().collect {
            if (it.code.isOk()) {
                logcat(
                    "featureFirst:${it.featureFirst},haveRcmdSongs:${it.haveRcmdSongs},recommend:${
                        it.recommend.joinToString {
                            it.name + " " + it.copywriter + " " + it.trackCount
                        }
                    }"
                )

                recommendList.value += RecommendItem(
                    title = "日推",
                    list = it.recommend.map {
                        SongList(
                            id = it.id,
                            name = it.name,
                            picUrl = it.picUrl,
                            playCount = it.playcount,
                        )
                    }
                )
            }
        }
        // 个推
        KtorHelper.recommendPersonal().collect {
            if (it.code.isOk()) {
                recommendList.value += RecommendItem(
                    title = "个推",
                    list = it.result.map {
                        SongList(
                            id = it.id,
                            name = it.name,
                            picUrl = it.picUrl,
                            playCount = it.playCount,
                        )
                    }
                )
            }
        }
        // 用户歌单
        KtorHelper.userPlaylist(AppHelper.userId).collect {
            if (it.code.isOk()) {
                recommendList.value += RecommendItem(
                    title = "用户歌单",
                    list = it.playlist.map {
                        SongList(
                            id = it.id ?: 0,
                            name = it.name.toString(),
                            picUrl = it.coverImgUrl.toString(),
                            playCount = it.playCount ?: 0,
                        )
                    }
                )
            }
        }

    }
}