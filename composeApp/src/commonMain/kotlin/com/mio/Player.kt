package com.mio

import com.mio.bean.SongResponse
import com.mio.utils.KtorHelper
import com.mio.utils.isOk
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * 播放器
 */
object Player {
    private var player = MioPlayer

    fun initPlayer() {
        player.init()

        // 每100ms更新一次进度
        GlobalScope.launch {
            while (true) {
                currentDuration.value = player.getCurrent()
                totalDuration.value = player.getTotal()
                progress.value = player.getProgress()
                delay(100)
            }
        }
    }

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

    // 进度 百分比
    val progress: MutableStateFlow<Float> = MutableStateFlow(0f)

    // 播放状态
    val playerState = MutableStateFlow<PlayState>(PlayState.Paused)

    fun isPlaying() = playerState.value == PlayState.Playing

    suspend fun play(url: String) {
        player.addListener(
            onPlaying = {
                playerState.value = PlayState.Playing
            },
            onPause = {
                playerState.value = PlayState.Paused
            },
            onStop = {
                playerState.value = PlayState.Paused
            },
            onEnd = {
                if (currentIndex.value < playList.value.size - 1) {
                    currentIndex.value++
                    GlobalScope.launch { play(playList.value[currentIndex.value]) }
                } else {
                    playerState.value = PlayState.Paused
                }
            },
            onError = {
                logcat("Player play: error")
            },
        )
        player.play(url)
    }

    suspend fun play(songs: List<SongResponse.Song>) {
        playList.value = songs
        currentIndex.value = 0

        play(playList.value[currentIndex.value])
    }

    // 播放每首歌曲 先通过接口查询播放url 播放url会失效
    suspend fun play(song: SongResponse.Song) {
        playerState.value = PlayState.Idle
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

    fun pause() = player.pause()
    fun resume() = player.resume()
    fun stop() = player.stop()
    fun release() = player.release()

    //
//    // play state只有在播放的时候才有值 音量默认为1.0
    fun getVolume() = player.getVolume()
    fun setVolume(volume: Int) {
        player.setVolume(volume)
    }

    fun seekTo(f: Long) {
    }

    fun playNext() {
        if (currentIndex.value < playList.value.size - 1) {
            currentIndex.value++
            GlobalScope.launch { play(playList.value[currentIndex.value]) }
        }
    }

    fun previous() {
        if (currentIndex.value > 0) {
            currentIndex.value--
            GlobalScope.launch { play(playList.value[currentIndex.value]) }
        }
    }
//
//
//    fun test() {
//        player.playerState?.setOnPlaying {
//            println("onPlaying")
//        }
//    }

    sealed class PlayState {
        object Idle : PlayState() // 加载中
        object Playing : PlayState()
        object Paused : PlayState()
    }
}
