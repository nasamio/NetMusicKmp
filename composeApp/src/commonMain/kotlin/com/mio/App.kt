package com.mio

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mio.pages.HomeUi
import com.mio.pages.MineUi
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

@Composable
fun MainUi() {
    Box(modifier = Modifier.fillMaxSize()) {
        // 页面导航
        val navController = rememberNavController()
        AppHelper.navController = WeakReference(navController)
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = "home"
        ) {
            AppHelper.pages.forEach { (route, content) ->
                composable(route) {
                    content(navController.currentBackStackEntry!!)
                }
            }
        }

        // 其他内容 一般用于页面上物

    }
}

object AppHelper {
    lateinit var navController: WeakReference<NavController>
    val cookie = MutableStateFlow("")


    // 路由定义
    val pages = mutableMapOf<String, @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit>(
        "home" to { HomeUi() },
        "mine" to { MineUi() },
    )

    // 页面跳转
    fun navigate(route: String) = navController.get()?.navigate(route)

    // 返回
    fun popBackStack() = navController.get()?.popBackStack()
}
