package com.mio.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.mio.AppHelper

@Composable
fun MineUi() {
    Column {
        Text("我的")
        Button(onClick = {
            AppHelper.popBackStack()
        }) {
            Text("go back")
        }
    }
}