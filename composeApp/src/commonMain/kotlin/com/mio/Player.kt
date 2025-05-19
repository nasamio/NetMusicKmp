package com.mio

import com.mio.bean.SongResponse
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * 播放器
 */
object Player {
    private lateinit var player: GadulkaPlayer

    fun initPlayer() {
        player = GadulkaPlayer().apply {}


    }

    // 歌曲播放列表
    val playList: MutableStateFlow<List<SongResponse.Song>> = MutableStateFlow(emptyList())

    // 当前播放列表中歌曲的序号
    val currentIndex = MutableStateFlow(0)

    // 音量
    val volume = MutableStateFlow(.2)

    suspend fun play(url: String){
        player.play(url)
        player.playerState?.setOnReady {
            logcat("media ready")
            setVolume(volume.value)
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
    fun getVolume() = player.playerState?.volume
    fun setVolume(volume: Double) {
        player.playerState?.volume = volume
    }


    fun test() {
        player.playerState?.setOnPlaying {
            println("onPlaying")
        }
    }
}