package com.mio.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mio.logcat
import com.mio.utils.KtorHelper

@Composable
fun PlayListDetailUi(id: Long) {
    logcat("PlayListDetailUi id:$id")

    LaunchedEffect(1) {
        KtorHelper.getPlaylistDetail(id.toString()).collect {
            logcat("PlayListDetailUi: $it")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("PlayListDetailUi")
    }
}
