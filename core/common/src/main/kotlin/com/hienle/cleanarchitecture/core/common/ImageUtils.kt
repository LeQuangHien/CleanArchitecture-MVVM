package com.hienle.cleanarchitecture.core.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun CaImage(
    modifier: Modifier = Modifier,
    url: String,
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
    )
}
