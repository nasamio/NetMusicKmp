package com.mio

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
        logcat("vlcj init over...")
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

    actual fun release() {
        mediaPlayer.release()
    }
}