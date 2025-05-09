package com.mio.pages

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import com.mio.AppHelper

@Composable
fun HomeUi() {
    Column {
        Text("home")
        Button(onClick = {
            AppHelper.navigate("mine")
        }) {
            Text("to mine")
        }
    }
}