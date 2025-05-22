package com.mio

expect object MioPlayer {

    fun init()
    fun play(url: String)
    fun pause()
    fun resume()
    fun stop()
    fun release()
}