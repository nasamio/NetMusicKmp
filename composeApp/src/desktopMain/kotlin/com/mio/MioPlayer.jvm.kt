package com.mio

import uk.co.caprica.vlcj.factory.MediaPlayerFactory
import uk.co.caprica.vlcj.player.base.MediaPlayer

actual object MioPlayer {
    private lateinit var mediaPlayer: MediaPlayer

    actual fun init() {
        val vlcLibPath = "D:\\codes\\kt\\publicLibs\\vlc-3.0.21"  // Windows示例
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

    actual fun release() {
        mediaPlayer.release()
    }
}