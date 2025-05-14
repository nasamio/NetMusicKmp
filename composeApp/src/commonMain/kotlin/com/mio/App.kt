package com.mio

import HoveredBox
import HoveredIcon
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.mio.bean.Account
import com.mio.bean.Playlist
import com.mio.bean.Profile
import com.mio.pages.HomeUi
import com.mio.pages.LeftTabUi
import com.mio.pages.LoginUi
import com.mio.pages.MineUi
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import kotlinx.coroutines.flow.MutableStateFlow
import netmusickmp.composeapp.generated.resources.*
import netmusickmp.composeapp.generated.resources.Res
import netmusickmp.composeapp.generated.resources.ic_back
import netmusickmp.composeapp.generated.resources.ic_clear
import netmusickmp.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource
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

        AppContainer(hasJudgeLogin, startDestination)

        // 其他内容 一般用于页面上物

    }
}

@Composable
fun AppContainer(hasJudgeLogin: Boolean, startDestination: String) {
    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        // 左侧tab
        LeftTab(
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RightTop(modifier: Modifier) {
    val profile = AppHelper.profile.collectAsState()

    Row(
        modifier = modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        HoveredBox(
            modifier = Modifier.width(27.dp)
                .height(36.dp),
            onClick = {
                logcat("点击了菜单")
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_back),
                contentDescription = "Menu",
                tint = Color.Black.copy(alpha = .5f),
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // 搜索框
        Row(
            modifier = Modifier.height(36.dp)
                .weight(1f)
                .border(
                    1.dp,
                    Color.Gray.copy(alpha = 0.3f),
                    RoundedCornerShape(6.dp)
                ).padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HoveredIcon(
                painter = painterResource(Res.drawable.ic_search),
                modifier = Modifier.size(20.dp),
                onClick = {
                    logcat("点击了搜索")
                }
            )
            Spacer(modifier = Modifier.width(10.dp))

            // 搜索框
            var text by remember { mutableStateOf("") }
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(10.dp))

            HoveredIcon(
                painter = painterResource(Res.drawable.ic_clear),
                modifier = Modifier.size(20.dp),
                onClick = {
                    logcat("点击了删除")
                }
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // 麦克风
        HoveredBox(
            modifier = Modifier.width(27.dp)
                .height(36.dp),
            onClick = {
                logcat("点击了麦克风")
            }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_mic),
                contentDescription = "Mic",
                tint = Color.Black.copy(alpha = .5f),
                modifier = Modifier.size(16.dp)
            )
        }

        // 头像
        Spacer(modifier = Modifier.width(20.dp))
        logcat("头像地址：${AppHelper.profile.value?.avatarUrl}")
        AsyncImage(
            model = profile.value?.avatarUrl,
            contentDescription = "头像",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(28.dp)
                .clip(CircleShape)
        )
        // 用户名
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = profile.value?.nickname ?: "未登录",
            style = TextStyle(color = Color.Black),
            color = Color.Black.copy(alpha = .8f),
        )

        Image(
            painter = painterResource(Res.drawable.ic_vip),
            contentDescription = "vip",
            modifier = Modifier.height(28.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))

        // 右侧的其他图标

        HoveredIcon(
            painter = painterResource(Res.drawable.ic_down),
            modifier = Modifier.size(20.dp),
            onClick = {
                logcat("点击了下")
            }
        )

        val margin: @Composable () -> Unit = {
            Spacer(modifier = Modifier.width(12.dp))
        }

        margin()
        HoveredIcon(
            painter = painterResource(Res.drawable.ic_mail),
            modifier = Modifier.size(20.dp),
            onClick = {
            }
        )

        margin()

        HoveredIcon(
            painter = painterResource(Res.drawable.ic_sun),
            modifier = Modifier.size(20.dp),
            onClick = {
            }
        )

        margin()


        HoveredIcon(
            painter = painterResource(Res.drawable.ic_min),
            modifier = Modifier.size(20.dp),
            onClick = {
            }
        )

        margin()


        HoveredIcon(
            painter = painterResource(Res.drawable.ic_max),
            modifier = Modifier.size(20.dp),
            onClick = {
            }
        )

        margin()


        HoveredIcon(
            painter = painterResource(Res.drawable.ic_close),
            modifier = Modifier.size(20.dp),
            onClick = {
            }
        )
    }
}

@Composable
fun LeftTab(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
//        Text(text = "左侧tab", color = Color.Black)
        LeftTabUi()
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
