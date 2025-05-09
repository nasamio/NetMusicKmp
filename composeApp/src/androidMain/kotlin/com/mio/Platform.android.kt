package com.mio

import android.content.Context
import android.os.Build
import android.widget.Toast
import com.mio.ContextHolder.context
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

object ContextHolder {
    lateinit var context: WeakReference<Context>
}

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(Android) {
    config(this)
}

actual fun toast(msg: String) {
    GlobalScope.launch(Dispatchers.Main) {
        Toast.makeText(context.get(), msg, Toast.LENGTH_SHORT).show()
    }
}