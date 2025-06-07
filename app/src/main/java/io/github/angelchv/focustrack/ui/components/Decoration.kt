package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import io.github.angelchv.focustrack.R

@Composable
fun HeaderImage(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.chv), contentDescription = "Logo",
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
    )
}

@Composable
fun FocusTrackLogo(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(R.mipmap.ic_launcher_foreground), contentDescription = "Logo",
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
    )
}
