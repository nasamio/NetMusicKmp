package com.mio

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import java.io.File
import java.util.*

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Apache) {
    engine {
        followRedirects = true
        socketTimeout = 10_000
        connectTimeout = 10_000
        connectionRequestTimeout = 20_000
        customizeClient {
            setMaxConnTotal(1000)
            setMaxConnPerRoute(100)
        }
        customizeRequest {
            // TODO: request transformations
        }
    }
    config(this)
}

actual fun toast(msg: String) {
    // TODO("Not yet implemented")
}

actual fun logcat(msg: String) {
    println(msg)
}

object PropertiesHolder {
    val filePath = "config.properties"
    val properties = Properties()

    init {
        val file = File(filePath)
        if (!file.exists()) {
            // 文件不存在，尝试创建它（及其父目录）
            file.parentFile?.let { if (!it.exists()) it.mkdirs() }
            file.createNewFile()
        }
        if (file.exists()) {
            file.inputStream().use { properties.load(it) }
        }
    }
}


actual fun saveString(key: String, value: String) {
    PropertiesHolder.properties.setProperty(key, value)
    File(PropertiesHolder.filePath).outputStream().use { PropertiesHolder.properties.store(it, null) }
}

actual fun getString(key: String, default: String): String {
    return PropertiesHolder.properties.getProperty(key, default)
}