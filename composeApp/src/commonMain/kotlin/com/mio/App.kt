package com.mio

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mio.Player.play
import com.mio.pages.*
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.lang.ref.WeakReference

@Composable
@Preview
fun App() {
    GlobalScope.launch {
        Player.initPlayer()
    }

    GlobalScope.launch {
        delay(1000)
        play("http://m801.music.126.net/20250520161505/9011f11262a4d0711e9d1400010abcac/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/28482149825/5032/13a7/4198/6a58ae33817499ac5699190e56d6379d.mp3?vuutv=1PYnX2esEo20KMVleAuDxAxqd4P0g/lZF3jtAthF3Lx0TWqMrUY7aq5t/JIl08FuGoGfbCFXRwuGxypLkVNd8xuqu7HTIzWEmd+jPI3/AJ8ikbEtb/b6JOhDjG2axxSEfXcUhqaLlTVcx2n6XiYiDQ9tt4lpkWi8u1wGjj+1mCU=")
        delay(1_000)
        logcat("volume:${Player.getVolume()}")
    }

    MaterialTheme {
        MainUi()
    }
}

/**
 * 1.读取本地cookie 根据cookie获取登录状态
 * 2.未登录 则跳转登录界面
 * 3.已登录 跳转主页
 * 4.未判断出是否登录状态之前 显示loading
 */
@Composable
fun MainUi() {
    Box(modifier = Modifier.fillMaxSize()) {
        var hasJudgeLogin by remember { mutableStateOf(false) }
        var startDestination by remember { mutableStateOf("recommend") }

        LaunchedEffect(1) {
            // 读取本地之前存的cookie
            AppHelper.cookie.value = getString("cookie", "")
            logcat("cookie: ${AppHelper.cookie.value}")

            KtorHelper.loginStatus().collect {
                logcat("${it.data?.code}${it.data?.account}\n${it.data?.profile}")

                val hasLogin = it.code.isOk() && it.data?.account != null && it.data.profile != null

                if (hasLogin) {
                    AppHelper.account.value = it.data?.account
                    AppHelper.profile.value = it.data?.profile
                    toast("登录成功")
                }
                startDestination = if (hasLogin) "recommend" else "login"

                AppHelper.isLogin.value = hasLogin
                hasJudgeLogin = true
            }
        }

        AppContainer(hasJudgeLogin, startDestination)

        // 其他内容 一般用于页面上物

        // 播放器
        PlayerUi(
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun AppContainer(hasJudgeLogin: Boolean, startDestination: String) {
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(bottom = 80.dp), // todo 这里先固定给mini player让一个位置 后续改成根据播放器状态来让位置
    ) {
        // 左侧tab
        LeftTabUi(
            modifier = Modifier.width(200.dp)
                .fillMaxHeight()
                .background(Color(0xfff0f3f6))
        )

        // 右侧内容
        Column(
            modifier = Modifier.fillMaxHeight()
                .weight(1f)
                .background(Color(0xfff7f9fc))
        ) {
            // 顶部区域 搜索 头像 等
            RightTop(
                modifier = Modifier.fillMaxWidth()
                    .height(72.dp)
            )

            // 内容区域
            // 页面导航
            NavContent(hasJudgeLogin, startDestination)
        }
    }
}

/**
 * 导航区域
 */
@Composable
fun NavContent(showContent: Boolean, startDestination: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (showContent) {
            NavigationComponent(startDestination)
        } else {
            LoadingUi()
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationComponent(startDestination: String) {
    val navController = rememberNavController()
    AppHelper.navController = WeakReference(navController)

    SharedTransitionScope { sharedScope ->
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = startDestination
        ) {

            composable(Page.Recommend.route) {
                RecommendUi()
            }
            composable(Page.PlayListDetail.route + "/{id}") { entry ->
                val id = entry.arguments?.getString("id")?.toLongOrNull() ?: 0L
                PlayListDetailUi(id = id)
            }


            composable(Page.Home.route) {
                HomeUi()
            }
            composable(Page.Mine.route) {
                MineUi()
            }
            composable(Page.Radio.route) {
                RadioUi()
            }
            composable(Page.Follow.route) {
                FollowUi()
            }
            composable(Page.Like.route) {
                LikeUi()
            }
            composable(Page.Recent.route) {
                RecentUi()
            }
            composable(Page.Collect.route) {
                CollectUi()
            }
            composable(Page.Local.route) {
                LocalUi()
            }

            composable(Page.Login.route) {
                LoginUi() // todo 登录做成小弹窗
            }
        }
    }
}

enum class Page(val route: String) {
    Home("home"),
    Mine("mine"),
    Recommend("recommend"),
    PlayListDetail("playlist_detail"),

    Radio("radio"),
    Follow("follow"),
    Like("like"),
    Recent("recent"),
    Collect("collect"),
    Local("local"),
    Login("login"),
}
