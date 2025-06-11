package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.angelchv.focustrack.R

@Composable
fun HeaderImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.chv), contentDescription = "Logo",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
    }
}

@Composable
fun FocusTrackLogo(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.focus_track_monochrome),
            contentDescription = "Logo",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun BottomChvDesign() {
    Column {
        HeaderImage(Modifier
            .size(60.dp)
            .align(Alignment.CenterHorizontally))
        Text(
            text = "© 2025 FocusTrack",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall,
        )
    }
}

@Composable
fun UserImage(modifier: Modifier = Modifier, photoUrl: String?) {
    if (!photoUrl.isNullOrEmpty()) {
        AsyncImage(
            model = photoUrl,
            contentDescription = stringResource(R.string.profile_photo),
            modifier = modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentScale = ContentScale.Crop
        )
    } else {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = stringResource(R.string.no_profile_photo),
            modifier = modifier.size(64.dp),
        )
    }
}
