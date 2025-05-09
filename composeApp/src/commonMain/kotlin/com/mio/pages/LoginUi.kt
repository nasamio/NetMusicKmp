@file:OptIn(ExperimentalEncodingApi::class)

package com.mio.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mio.AppHelper
import com.mio.logcat
import com.mio.saveString
import com.mio.toast
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.decodeToImageBitmap
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginUi() {
    val scope = rememberCoroutineScope()
    var qrImgBase64 by remember { mutableStateOf("") }
    val bitmap = remember(qrImgBase64) {
        if (qrImgBase64.startsWith("data:image/png;base64,")) {
            val base64Str = qrImgBase64.removePrefix("data:image/png;base64,")
            try {
                Base64.decode(base64Str).decodeToImageBitmap()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }
    var loginText by remember { mutableStateOf("") }

    LaunchedEffect(bitmap) {
        if (bitmap != null && AppHelper.qrKey.isNotEmpty()) {
            while (AppHelper.cookie.value.isEmpty()) {
                logcat("准备请求 qr check:${AppHelper.qrKey}")
                // 800 为二维码过期,801 为等待扫码,802 为待确认,803 为授权登录成功(803 状态码下会返回 cookies),如扫码后返回502,则需加上noCookie参数,如&noCookie=true
                KtorHelper.qrCheck(AppHelper.qrKey).collect {
                    logcat("qr check$it")
                    when (it.code) {
                        800 -> {
                            loginText = "二维码已过期，请重新扫码"
                            // todo 实现点击刷新
                        }

                        801 -> loginText = "请打开网易云音乐APP扫码登录"


                        802 -> loginText = "已扫描，待确认"


                        803 -> {
                            saveString("cookie", it.cookie)
                            AppHelper.cookie.value = it.cookie
                            loginText = "登录成功"
                            toast("登录成功,即将跳转...")
                            AppHelper.navigate("home")
                        }
                    }

                    delay(2_000)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        KtorHelper.qrKey().collect {
            logcat("qr key:$it")
            if (it.code.isOk()) {
                AppHelper.qrKey = it.data!!.unikey
                KtorHelper.qrImg(AppHelper.qrKey).collect {
                    logcat("qr img:$it")
                    if (it.code.isOk()) {
                        qrImgBase64 = it.data!!.qrimg
                    } else {
//                        AppHelper.toast("获取qr img失败，请退出应用稍后再试...")
                        loginText = "获取qr img失败，请退出应用稍后再试..."
                    }
                }
            } else {
//                AppHelper.toast("获取qr key失败，请退出应用稍后再试...")
                loginText = "获取qr key失败，请退出应用稍后再试..."
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        bitmap?.let {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.size(200.dp),
                    bitmap = bitmap,
                    contentDescription = "QR Code",
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = loginText)
            }

        } ?: Text(text = "二维码加载失败，请退出应用稍后再试...")


    }
}
