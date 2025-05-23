package com.mio

/**
 * 小于10万显示具体数字
 * 大于显示几点几万 保留一位小数
 */
fun Int.countStr(): String {
    return this.toLong().countStr()
}

fun Long.countStr(): String {
    return if (this < 100000) {
        this.toString()
    } else {
        val decimal = this / 10000.0
        String.format("%.1f", decimal) + "万"
    }
}

/**
 * 时间戳转时间 类似 2023-12-18
 */
fun Long.timeStr(): String {
    return if (this == 0L) {
        ""
    } else {
        val date = java.util.Date(this)
        val format = java.text.SimpleDateFormat("yyyy-MM-dd")
        format.format(date)
    }
}