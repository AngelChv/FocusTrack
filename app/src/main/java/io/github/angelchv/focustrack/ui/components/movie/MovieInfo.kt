package io.github.angelchv.focustrack.ui.components.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.ui.screens.movieDetail.formatRuntime

@Composable
fun MovieGenresLazyRowChips(genres: List<String>) {
    LazyRow(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        items(genres.size) { index ->
            SuggestionChip(
                modifier = Modifier.padding(horizontal = 4.dp),
                label = {
                    Text(
                        text = genres[index],
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
                onClick = {}
            )
        }

    }
}

@Composable
fun MovieSortInfoRow(
    releaseDate: String?,
    voteAverage: Double,
    voteCount: Int,
    runtime: Int? = null,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = releaseDate.orEmpty(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = "★ $voteAverage ($voteCount)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        runtime?.let {
            Text(
                text = formatRuntime(runtime, stringResource(R.string.unknown_runtime)),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}