package com.mio

import io.ktor.client.*

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

// 初始化ktor client
expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

expect fun toast(msg: String)

expect fun logcat(msg: String)

expect fun saveString(key: String, value: String)

expect fun getString(key: String, default: String): String
