package com.mio.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@ExperimentalSharedTransitionApi
@Composable
fun SharedTransitionScope.FastSharedBounds(
    key: String,
    animatedVisibilityScope: AnimatedContentScope,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.sharedBounds(
            rememberSharedContentState(key),
            animatedVisibilityScope = animatedVisibilityScope,
        )
    ) {
        content()
    }
}
