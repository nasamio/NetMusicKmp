@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.mio

import kotlinx.coroutines.flow.MutableStateFlow
import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.player.base.MediaPlayer

actual object MioPlayer {
    private lateinit var mediaPlayer: MediaPlayer

    actual fun init() {
        logcat("vlcj init start...")
        val appDir = System.getProperty("user.dir")
        val vlcLibPath = "$appDir/src/desktopMain/kotlin/libs/vlcLib"  // 这样写只支持调试的时候运行 打包产物后续考虑是否先把资源拷贝
        System.setProperty("jna.library.path", vlcLibPath)
        mediaPlayer = MediaPlayerFactory().mediaPlayers().newMediaPlayer()
        logcat("vlcj init")
    }

    actual fun play(url: String) {
        mediaPlayer.media().play(url)

    }

    actual fun pause() {
        mediaPlayer.controls().pause()
    }

    actual fun resume() {
        mediaPlayer.controls().play()
    }

    actual fun stop() {
        mediaPlayer.controls().stop()
    }

    actual fun getVolume(): Int {
        return mediaPlayer.audio().volume()
    }

    actual fun setVolume(volume: Int) {
        mediaPlayer.audio().setVolume(volume)
    }

    actual fun release() {
        mediaPlayer.release()
    }

    actual fun addListener(
        onPlaying: () -> Unit,
        onPause: () -> Unit,
        onStop: () -> Unit,
        onEnd: () -> Unit,
        onError: () -> Unit,
    ) {
        mediaPlayer.events()
            .addMediaPlayerEventListener(object : uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter() {
                override fun playing(mediaPlayer: MediaPlayer?) {
                    super.playing(mediaPlayer)
                    onPlaying()
                }

                override fun paused(mediaPlayer: MediaPlayer?) {
                    super.paused(mediaPlayer)
                    onPause()
                }

                override fun stopped(mediaPlayer: MediaPlayer?) {
                    super.stopped(mediaPlayer)
                    onStop()
                }


                override fun finished(mediaPlayer: MediaPlayer?) {
                    super.finished(mediaPlayer)
                    onEnd()
                }

                override fun error(mediaPlayer: MediaPlayer?) {
                    super.error(mediaPlayer)
                    onError( )
                }
            })
    }

    actual var state: MutableStateFlow<Int>
        get() = TODO("Not yet implemented")
        set(value) {}

    actual fun getTotal(): Long {
        return mediaPlayer.status().length()
    }
    actual fun getCurrent(): Long {
        return mediaPlayer.status().time()
    }
    actual fun getProgress(): Float {
        return mediaPlayer.status().position()
    }
}