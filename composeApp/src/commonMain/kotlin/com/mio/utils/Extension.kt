package com.mio.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(DelicateCoroutinesApi::class)
fun <T> MutableStateFlow<T>.collectIn(
    scope: CoroutineScope = GlobalScope,
    action: suspend (T) -> Unit
): Job {
    return scope.launch(Dispatchers.IO) {
        this@collectIn.collect { value ->
            action(value)
        }
    }
}

/**
 * 主进程回调
 */
@OptIn(DelicateCoroutinesApi::class)
fun <T> MutableStateFlow<T>.collectOn(
    scope: CoroutineScope = GlobalScope,
    action: suspend (T) -> Unit
): Job {
    return scope.launch(Dispatchers.IO) {
        this@collectOn.collect { value ->
            scope.launch(Dispatchers.Main) { action(value) }
        }
    }
}

fun Long.toMinAndSecStr():String{
    val min = this / 1000 / 60
    val sec = this / 1000 % 60
    return String.format("%02d:%02d", min, sec)
}