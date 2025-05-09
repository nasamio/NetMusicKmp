package com.mio

import io.ktor.client.*
import io.ktor.client.engine.apache.*

class JVMPlatform: Platform {
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