package com.mio

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mio.bean.Account
import com.mio.bean.Playlist
import com.mio.bean.Profile
import com.mio.pages.HomeUi
import com.mio.pages.LoginUi
import com.mio.pages.MineUi
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import java.lang.ref.WeakReference

@Composable
@Preview
fun App() {
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
        var startDestination by remember { mutableStateOf("home") }

        LaunchedEffect(1) {
            // 读取本地之前存的cookie
            AppHelper.cookie.value = getString("cookie", "")


            KtorHelper.loginStatus().collect {
                logcat("${it.data?.code}${it.data?.account}\n${it.data?.profile}")

                val hasLogin = it.code.isOk() && it.data?.account != null && it.data.profile != null

                if (hasLogin) {
                    AppHelper.account.value = it.data?.account
                    AppHelper.profile.value = it.data?.profile
                    toast("登录成功")
                }
                startDestination = if (hasLogin) "home" else "login"

                AppHelper.isLogin.value = hasLogin
                hasJudgeLogin = true
            }
        }

        // 页面导航
        NavContent(hasJudgeLogin, startDestination)

        // 其他内容 一般用于页面上物

    }
}

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
                        content(navController.currentBackStackEntry!!)
                    }
                }
            }
        } else {
            LoadingUi()
        }
    }
}
@Composable
fun LoadingUi() {
    // 加载中界面可选
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        // todo 解决导入不了的问题
//            CircularProgressIndicator(
//                modifier = Modifier
//                    .padding(16.dp)
//                    .align(androidx.compose.ui.Alignment.Center)
//            )

        Text(text = "加载中...")
    }
}
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
        "login" to { LoginUi() },
    )

    // 页面跳转
    fun navigate(route: String) = navController.get()?.navigate(route)

    // 返回
    fun popBackStack() = navController.get()?.popBackStack()
}
