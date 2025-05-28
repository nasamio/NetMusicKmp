package com.mio

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import com.mio.components.StateContainer
import com.mio.components.UiState
import com.mio.pages.*
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import java.lang.ref.WeakReference

/**
 * 1.读取本地cookie 根据cookie获取登录状态
 * 2.未登录 则跳转登录界面
 * 3.已登录 跳转主页
 * 4.未判断出是否登录状态之前 显示loading
 */
@Composable
fun App(
    leftTabUi: @Composable (Modifier) -> Unit = { LeftTabUi(it) },
    rightTopUi: @Composable (Modifier) -> Unit = { RightTop(it) },
) {
    MaterialTheme {
        LaunchedEffect(Unit) {
            Player.initPlayer()
        }


        Box(modifier = Modifier.fillMaxSize()) {
            var uiState by remember { mutableStateOf<UiState>(UiState.Loading) }

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
                    uiState = UiState.Content
                }
            }

            StateContainer(
                state = uiState,
            ) {
                AppContainer(
                    startDestination,
                    leftTabUi = leftTabUi,
                    rightTopUi = rightTopUi,
                ) {
                    // 页面内容
                    NavContent(startDestination)
                }
            }

            // 其他内容 一般用于页面上物

            // 播放器
            PlayerUi(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun AppContainer(
    startDestination: String,
    leftTabUi: @Composable (Modifier) -> Unit = { LeftTabUi(it) },
    rightTopUi: @Composable (Modifier) -> Unit = { RightTop(it) },
    content: @Composable (Modifier) -> Unit = { NavContent(startDestination) },
) {
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(bottom = 80.dp), // todo 这里先固定给mini player让一个位置 后续改成根据播放器状态来让位置
    ) {
        // 左侧tab
        leftTabUi(
            Modifier.width(200.dp)
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

            rightTopUi(
                Modifier.fillMaxWidth()
                    .height(72.dp)
            )

            // 内容区域
            // 页面导航
            content(Modifier)
        }
    }
}

/**
 * 导航区域
 */
@Composable
fun NavContent(startDestination: String) {
    NavigationComponent(startDestination)
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigationComponent(startDestination: String) {
    val navController = rememberNavController()
    AppHelper.navController = WeakReference(navController)

    SharedTransitionLayout {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = startDestination
        ) {
            composable(
                Page.Recommend.route,
//                enterTransition = { EnterTransition.None },
//                exitTransition = { ExitTransition.None },
//                popEnterTransition = { EnterTransition.None },
//                popExitTransition = { ExitTransition.None },
            ) {
                RecommendUi(this@composable)
            }
            composable(
                Page.PlayListDetail.route + "/{id}",
//                enterTransition = { EnterTransition.None },
//                exitTransition = { ExitTransition.None },
//                popEnterTransition = { EnterTransition.None },
//                popExitTransition = { ExitTransition.None },
            ) { entry ->
                val id = entry.arguments?.getString("id")
                PlayListDetailUi(id = id, animScope = this@composable)
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
