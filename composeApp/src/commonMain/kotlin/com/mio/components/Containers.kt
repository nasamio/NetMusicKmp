package com.mio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * 定义状态
 */
sealed class UiState {
    data object Loading : UiState()
    data object Empty : UiState()
    data object Content : UiState()
}

/**
 * 状态容器组件
 *
 * @param state 当前状态
 * @param modifier 组件修饰符，可选
 * @param emptyContent 空数据时显示的内容，可选，默认显示简单文本
 * @param content 正常显示时的内容 Composable lambda
 */
@Composable
fun StateContainer(
    state: UiState,
    modifier: Modifier = Modifier,
    emptyContent: @Composable () -> Unit = {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "暂无数据")
        }
    },
    content: @Composable () -> Unit,
) {
    when (state) {
        is UiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Empty -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                emptyContent()
            }
        }

        is UiState.Content -> {
            Box(modifier = modifier.fillMaxSize()) {
                content()
            }
        }
    }
}
