package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi.Companion.ImageSize
import io.github.angelchv.focustrack.domain.model.Movie
import io.github.angelchv.focustrack.ui.components.movie.MovieSortInfoRow
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@Composable
fun MoviePosterCard(
    modifier: Modifier = Modifier, movie: Movie,
    size: ImageSize = ImageSize.W154,
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

@Composable
fun MovieCardInfo(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (movieId: Int) -> Unit = {},
    showRemoveButton: Boolean = false,
    onRemoveClick: (movieId: Int) -> Unit = {},
) {
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        ConfirmDialog(
            dialogTitle = stringResource(R.string.remove_movie_from_list),
            onConfirmation = {
                openDialog.value = false
                onRemoveClick(movie.id)
            },
            onDismissRequest = { openDialog.value = false }
        )
    }

    Box(modifier) {

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onClick(movie.id) }
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(8.dp)
            ) {
                Box {
                    AsyncImage(
                        model = TMDBApi.getImageUrl(movie.posterPath.toString(), ImageSize.W154),
                        contentDescription = stringResource(R.string.movie_poster),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(100.dp)
                            .height(150.dp)
                            .clip(MaterialTheme.shapes.medium)
                    )

                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    MovieSortInfoRow(movie.releaseDate, movie.voteAverage, movie.voteCount)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }

        if (showRemoveButton) Button(
            onClick = { openDialog.value = true },
            modifier = Modifier.size(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            contentPadding = PaddingValues(0.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(R.string.remove),
            )
        }
    }
}

@Preview
@Composable
fun MovieCardInfoPreview() {
    FocusTrackTheme {
        MovieCardInfo(
            movie = Movie.example,
            onClick = {},
            showRemoveButton = true,
            onRemoveClick = {},
        )
    }
}
