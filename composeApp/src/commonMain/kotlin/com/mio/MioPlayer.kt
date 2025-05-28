package com.mio

import kotlinx.coroutines.flow.MutableStateFlow

expect object MioPlayer {

    var state: MutableStateFlow<Int>

    fun init()
    fun play(url: String)
    fun pause()
    fun resume()
    fun stop()
    fun release()
    fun getVolume(): Int
    fun setVolume(volume: Int)

    // 监听
    fun addListener(
        onPlaying: () -> Unit , // 播放开始
        onPause: () -> Unit , // 暂停
        onStop: () -> Unit , // 停止
        onEnd: () -> Unit, // 播放结束
        onError: () -> Unit,
    )

    // 获取当前播放总时长 ms
    fun getTotal(): Long

    // 获取当前播放进度 ms
    fun getCurrent(): Long

    // 获取当前进度百分比
    fun getProgress(): Float

}