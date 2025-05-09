package com.mio

import java.io.File
import java.util.Properties

class PropertiesStorage(private val filePath: String = "config.properties") {

    val properties = Properties()

    init {
        val file = File(filePath)
        if (file.exists()) {
            file.inputStream().use { properties.load(it) }
        }
    }

    fun saveString(key: String, value: String) {
        properties.setProperty(key, value)
        File(filePath).outputStream().use { properties.store(it, null) }
    }

    fun getString(key: String, default: String): String {
        return properties.getProperty(key, default)
    }
}
