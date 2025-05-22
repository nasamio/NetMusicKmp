package com.mio

import com.mio.bean.SongResponse
import com.mio.utils.KtorHelper
import com.mio.utils.collectIn
import com.mio.utils.isOk
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * 播放器
 */
object Player {
    private lateinit var player: GadulkaPlayer

    // 歌曲播放列表
    val playList: MutableStateFlow<List<SongResponse.Song>> = MutableStateFlow(emptyList())

    // 当前播放列表中歌曲的序号
    val currentIndex = MutableStateFlow(0)

    // 音量
    val volume = MutableStateFlow(.6f)

    // 当前进度
    val currentDuration = MutableStateFlow(0L)
    // 总进度
    val totalDuration = MutableStateFlow(0L)

   suspend fun initPlayer() {
        player = GadulkaPlayer().apply {}

        GlobalScope.launch {
            volume.collectIn{
                setVolume(it)
            }
        }

        // 读取设置

        // 音量
        getString("volume", "0.6").toFloatOrNull()?.let {
            volume.value = it
        } ?: run {
            volume.value = 0.6f
        }

       startProgressCheck()
    }

    private suspend fun startProgressCheck() {
        currentDuration.value = player.currentPosition() ?: 0L
        totalDuration.value = player.currentDuration() ?: 0L

        delay(400)
        startProgressCheck()
    }

    suspend fun play(url: String) {
        player.play(url)
        delay(100)
        player.playerState?.let { mp ->
            // 应用监听
            mp.setOnReady {
                logcat("onReady")
            }
            mp.setOnPlaying {
                logcat("onPlaying")
            }
            mp.setOnPaused {
                logcat("onPaused")
            }
            mp.setOnStopped {
                logcat("onStopped")
            }
            mp.setOnEndOfMedia {
                logcat("onEndOfMedia")
            }
            mp.setOnError {
                logcat("onError")
            }

//            // 每一次mp都要重新设置音量 因为每次播放一个新的 会新建一个mp对象来操作
//            logcat("current volume:${getVolume()}")
//
//            setVolume(volume.value)
//
//            logcat("after volume:${getVolume()}")
        } ?: run {
            logcat("mp is null...")
        }
    }

    suspend fun play(songs: List<SongResponse.Song>) {
        playList.value = songs
        currentIndex.value = 0

        play(playList.value[currentIndex.value])
    }

    // 播放每首歌曲 先通过接口查询播放url 播放url会失效
    suspend fun play(song: SongResponse.Song) {
        song.id?.toString()?.let {
            KtorHelper.getSongUrl(it).collect {
                logcat("接口请求完毕(${it.code}),url:${it.data.joinToString { it.url }}")
                if (it.code.isOk()) {
                    // 这里返回多个 猜测可能是不同音质 这里先实现第一个
                    it.data.first().url.let { play(it) }
                } else {
                    logcat("Player play: ${it.code}")
                }
            }
        } ?: run { logcat("Player play: empty id...") }
    }


    fun stop() = player.stop()
    fun release() = player.release()

    // play state只有在播放的时候才有值 音量默认为1.0
    fun getVolume() = player.currentVolume()
    fun setVolume(volume: Float) {
        player.setVolume(volume)
    }


    fun test() {
        player.playerState?.setOnPlaying {
            println("onPlaying")
        }
    }
}