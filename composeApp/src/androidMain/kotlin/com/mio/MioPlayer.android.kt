package com.mio

actual object MioPlayer {

    actual fun init() {

    }

    actual fun play(url: String) {

    }

    actual fun pause() {}
    actual fun resume() {}
    actual fun stop() {}

    actual fun release() {

    }

    actual fun getVolume(): Int {
        return 0
    }

    actual fun setVolume(volume: Int) {
    }

}
