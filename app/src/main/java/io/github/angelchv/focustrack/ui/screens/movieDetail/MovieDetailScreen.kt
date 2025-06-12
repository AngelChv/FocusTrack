package io.github.angelchv.focustrack.ui.screens.movieDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi.Companion.ImageSize
import io.github.angelchv.focustrack.domain.model.MovieDetail
import io.github.angelchv.focustrack.ui.components.movie.MovieGenresLazyRowChips
import io.github.angelchv.focustrack.ui.components.movie.MovieSortInfoRow
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState

    MovieDetail(state)
}

@Composable
fun MovieDetail(
    state: MovieDetailUiState,
) {
    val movie = state.movieDetail

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    movie?.let {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = TMDBApi.getImageUrl(it.backdropPath.toString(), ImageSize.ORIGINAL),
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
                // TODO: placeholder and error
                //placeholder = painterResource(R.drawable.placeholder),
                //error = painterResource(R.drawable.placeholder)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            ),
                            startY = 200f,
                            endY = 600f
                        )
                    )
                    .padding(bottom = 16.dp)
            ) {
                Spacer(Modifier.height(200.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    text = it.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Tagline
                if (it.tagline.isNotBlank()) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(horizontal = 16.dp),
                        text = "\"${it.tagline}\"",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                MovieSortInfoRow(
                    it.releaseDate, it.voteAverage, it.voteCount, it.runtime,
                    Modifier.padding(top = 16.dp).padding(horizontal = 16.dp)
                )

                MovieGenresLazyRowChips(it.genres)

                Spacer(modifier = Modifier.height(16.dp))

                // Overview
                SectionTitle(stringResource(R.string.overview))

                Text(
                    text = it.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle(title = stringResource(R.string.spoken_languages))
                Text(
                    text = it.spokenLanguages.joinToString(", ")
                        .ifBlank { stringResource(R.string.unknown_language) },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${stringResource(R.string.status)}: ${it.status}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "${stringResource(R.string.adult)}: ${if (it.adult) "Sí" else "No"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${stringResource(R.string.budget)}: ${formatCurrency(it.budget)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "${stringResource(R.string.revenue)}: ${formatCurrency(it.revenue)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Todo: implement functionality
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    it.imdbId?.takeIf { id -> id.isNotBlank() }?.let { imdbId ->
                        OutlinedButton(onClick = { }) {
                            Text("IMDb")
                        }
                    }

                    it.homepage?.takeIf { url -> url.isNotBlank() }?.let { homepage ->
                        OutlinedButton(onClick = { }) {
                            Text(stringResource(R.string.official_website))
                        }
                    }
                }
            }
        }
    } ?: run {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(stringResource(R.string.movie_not_loaded))
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

fun formatRuntime(runtime: Int, unknownRuntimeString: String?): String {
    if (runtime <= 0) return unknownRuntimeString ?: ""
    val hours = runtime / 60
    val minutes = runtime % 60
    return "${hours}h ${minutes}m"
}

fun formatCurrency(amount: Int): String {
    if (amount <= 0) return "N/D"
    return "$${"%,d".format(amount)}"
}

@Preview(showBackground = true)
@Composable
fun MovieDetailPreview() {
    FocusTrackTheme {
        MovieDetail(MovieDetailUiState(movieDetail = MovieDetail.example))
    }
}
