package com.mio

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
import com.mio.pages.LeftTabUi
import com.mio.pages.LoadingUi
import com.mio.pages.PlayerUi
import com.mio.pages.RightTop
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.lang.ref.WeakReference

@Composable
@Preview
fun App() {
    Player.initPlayer()

    GlobalScope.launch {
        play("http://m701.music.126.net/20250519181552/893aec3d8239a4dbff70a333b1272a3a/jdymusic/obj/wo3DlMOGwrbDjj7DisKw/28482149825/5032/13a7/4198/6a58ae33817499ac5699190e56d6379d.mp3?vuutv=Ibz2jcCfNDkbVH60matL4AxH4zfn4s88ZOiXH2BVn4tGmzdCpJVm+6qBQFwQ4IcQKUdkV/Swn1zIQnh+OPUtbvv7/VwI5qumdW6QrSdSUC9wfao0XQriMsldNnvyw5fc0rfdqBxTji52sKlVcbdvdIP3bLaHvQc1xASeJ6J63cY=")
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
        modifier = Modifier.fillMaxSize(),
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
    val navController = rememberNavController()
    AppHelper.navController = WeakReference(navController)
    Box(modifier = Modifier.fillMaxSize()) {
        if (showContent) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                startDestination = startDestination
            ) {
                AppHelper.pages.forEach { (route, content) ->
                    composable(route) {
                        navController.currentBackStackEntry?.let { it1 -> content(it1) }
                    }
                }
            }
        } else {
            LoadingUi()
        }
    }
}


