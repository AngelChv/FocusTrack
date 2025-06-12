package io.github.angelchv.focustrack.ui.screens.listDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.domain.model.UserList
import io.github.angelchv.focustrack.ui.components.MovieCardInfo

@Composable
fun ListDetailScreen(
    viewModel: ListDetailViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState

    ListDetail(state)
}

@Composable
fun ListDetail(
    state: ListDetailUiState,
) {
    state.list?.let {
        Column {
            Text(
                state.list.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(it.movieIds.size) { movieId ->
                    val movie = state.movies?.get(movieId)?.toMovie()
                    movie?.let {
                        MovieCardInfo(
                            movie = it,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        ) { movieClickedId ->
                        }
                    }
                }
            }
        }
    } ?: run {
        if (!state.isLoading) {
            Text("No list found")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListDetailPreview() {
    ListDetail(
        ListDetailUiState(
            list = UserList(
                "", "Title",
                movieIds = listOf(1, 2, 3, 4, 5)
            )
        )
    )
}