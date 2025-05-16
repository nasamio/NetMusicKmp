package com.mio

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.mio.bean.Account
import com.mio.bean.Playlist
import com.mio.bean.Profile
import com.mio.pages.*
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.ref.WeakReference

object AppHelper {
    lateinit var navController: WeakReference<NavController>
    val cookie = MutableStateFlow("")
    var qrKey: String = ""

    val account = MutableStateFlow<Account?>(null)
    val profile = MutableStateFlow<Profile?>(null)
    val isLogin = MutableStateFlow(false)

    // 用于跳转歌单界面
    var toJumpPlayList: Playlist? = null

    // 路由定义
    val pages = mutableMapOf<String, @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit>(
        "home" to { HomeUi() },
        "mine" to { MineUi() },
        "recommend" to { RecommendUi() },
        "radio" to { RadioUi() },
        "follow" to { FollowUi() },
        "like" to { LikeUi() },
        "recent" to { RecentUi() },
        "collect" to { CollectUi() },
        "local" to { LocalUi() },

        "login" to { LoginUi() },
    )

    // 页面跳转
    fun navigate(route: String) = navController.get()?.navigate(route)

    // 返回
    fun popBackStack() = navController.get()?.popBackStack()
}
