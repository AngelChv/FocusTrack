package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi.Companion.ImageSize
import io.github.angelchv.focustrack.domain.model.Movie

@Composable
fun MovieCard(
    modifier: Modifier = Modifier, movie: Movie,
    size: ImageSize,
    onClick: (Movie) -> Unit,
) {
    ElevatedCard(
        modifier = modifier,
        onClick = { onClick(movie) },
    ) {
        AsyncImage(
            model = TMDBApi.getImageUrl(movie.posterPath.toString(), size),
            contentDescription = stringResource(R.string.movie_poster),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}