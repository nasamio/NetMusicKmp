package com.mio.utils

import com.mio.AppHelper
import com.mio.bean.*
import com.mio.httpClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

/**
 * 网络请求 使用ktor进行网络请求
 */
object KtorHelper {
    const val BASE_URL = "1.94.134.14"
    const val PORT = 3001

    val httpClient: HttpClient by lazy {
        httpClient {
            // 这里可以配置你的HttpClient
            install(DefaultRequest) {
                url {
                    host = BASE_URL
                    port = PORT
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        encodeDefaults = true
                        isLenient = true
                        allowSpecialFloatingPointValues = true
                        allowStructuredMapKeys = true
                        prettyPrint = false
                        useArrayPolymorphism = false

                        // 忽略多余字段
                        ignoreUnknownKeys = true
                    }
                )
            }

        }
    }


    fun close() {
        httpClient.close()
    }

    inline fun <reified T> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
    ): Flow<T> {
        println("get: 请求接口:$url,参数:$params")
        return flow {
            val response = httpClient.get(url) {
                headers.forEach { header(it.key, it.value) }
                params.forEach { parameter(it.key, it.value) }
            }
            val result = response.body<T>()
            emit(result)
        }.catch { throwable: Throwable ->
            logError("Error occurred during network request", throwable)
        }.onCompletion { cause ->
//            close()
        }.flowOn(Dispatchers.Default)
    }


    inline fun <reified T> post(
        url: String,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
    ): Flow<T> {
        return flow {
            val response = httpClient.post(url) {
                headers.forEach { header(it.key, it.value) }
                params.forEach { parameter(it.key, it.value) }
            }
            val result = response.body<T>()
            emit(result)
        }.catch { throwable: Throwable ->
            logError("Error occurred during network request", throwable)
        }.onCompletion { cause ->
//            close()
        }.flowOn(Dispatchers.Default)
    }


    fun logError(message: String, throwable: Throwable) {
        // 这里可以使用你喜欢的日志框架进行日志记录
        // 例如，使用println简单输出到控制台
        println("Net Error:$message: ${throwable.message}")
        throwable.printStackTrace()
    }

    /**
     * 下面的是具体的网络请求
     */

    suspend fun test(): Flow<String> {
        return get<String>("/")
    }

    suspend fun qrKey() = get<BaseResponse<QrKey>>(
        url = "/login/qr/key",
        params = mapOf(),
    )

    suspend fun qrImg(key: String) = get<BaseResponse<QrImg>>(
        url = "/login/qr/create",
        params = mapOf(
            "key" to key,
            "qrimg" to "true",
        ),
    )

    suspend fun qrCheck(key: String) = get<QrCheck>(
        url = "/login/qr/check",
        params = mapOf(
            "key" to key,
            "timestamp" to System.currentTimeMillis().toString(),
        ),
    )

    /**
     * 以下所有登录后的接口都需要登录状态 请求接口带cookie
     */

    suspend fun loginStatus() = get<BaseResponse<LoginData>>(
        url = "/login/status",
        params = mapOf(
            "timestamp" to System.currentTimeMillis().toString(),
            "cookie" to AppHelper.cookie.value,
        ),
    )



    fun getSongs(songId: String) = get<SongResponse>(
        url = "/playlist/track/all",
        params = mapOf(
            "id" to songId.toString(),
            "timestamp" to System.currentTimeMillis().toString(),
            "cookie" to AppHelper.cookie.value,
        ),
    )

    // 日推歌单
    fun recommendResource() = get<RecommendResourceResponse>(
        url = "/recommend/resource",
        params = mapOf(
            "timestamp" to System.currentTimeMillis().toString(),
            "cookie" to AppHelper.cookie.value,
        ),
    )

    // 个推歌单
    fun recommendPersonal() = get<PersonalRecommendResponse>(
        url = "/personalized",
        params = mapOf(
            "limit" to "10",
            "timestamp" to System.currentTimeMillis().toString(),
            "cookie" to AppHelper.cookie.value,
        ),
    )

    // 用户歌单 不加参数 获取所有的
    suspend fun userPlaylist(userId: Long?) = get<PlayListResponse>(
        url = "/user/playlist",
        params = mapOf(
            "uid" to userId.toString(),
            "timestamp" to System.currentTimeMillis().toString(),
            "cookie" to AppHelper.cookie.value,
        ),
    )

    // 获取歌曲播放链接
    fun getSongUrl(id: String) = get<SongUrlResponse>(
        url = "/song/url",
        params = mapOf(
            "id" to id,
            "timestamp" to System.currentTimeMillis().toString(),
            "cookie" to AppHelper.cookie.value,
            "br" to "320000",
        ),
    )

    // 获取歌单详情
    fun getPlaylistDetail(id: String) = get<PlaylistDetailResponse>(
        url = "/playlist/detail",
        params = mapOf(
            "id" to id,
            "timestamp" to System.currentTimeMillis().toString(),
            "cookie" to AppHelper.cookie.value,
        ),
    )
}

fun Int.isOk() = this == 200
