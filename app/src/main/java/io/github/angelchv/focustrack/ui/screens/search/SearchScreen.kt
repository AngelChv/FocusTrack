package io.github.angelchv.focustrack.ui.screens.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.ui.components.MovieCardInfo
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

// Todo: send isExpanded and scroll when nav back.
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onNavigateToMovieDetail: (movieId: Int) -> Unit,
) {
    val state = viewModel.uiState

    Search(
        modifier = modifier,
        state = state,
        onQueryChange = viewModel::onQueryChange,
        performSearch = viewModel::performSearch,
        setSearching = viewModel::setSearching,
        setExpanded = viewModel::setExpanded,
        onMovieClick = { movieId -> onNavigateToMovieDetail(movieId) }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    modifier: Modifier = Modifier,
    state: SearchUiState,
    onQueryChange: (String) -> Unit = {},
    performSearch: () -> Unit = {},
    setSearching: (Boolean) -> Unit = {},
    setExpanded: (Boolean) -> Unit = {},
    onMovieClick: (Int) -> Unit = {},
) {
    SearchBar(
        modifier = modifier,
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = state.query,
                onQueryChange = onQueryChange,
                onSearch = {
                    performSearch()
                    setExpanded(false)
                },
                expanded = state.isExpanded,
                onExpandedChange = {
                    setSearching(it)
                    setExpanded(it)
                },
                placeholder = { Text("Buscar películas...") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = stringResource(R.string.search)
                    )
                },
                trailingIcon = {
                    if (state.query.isNotBlank() || state.isExpanded) {
                        IconButton({
                            onQueryChange("")
                            setExpanded(false)
                        }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = stringResource(R.string.clear_query),
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        }
                    }
                }
            )
        },
        expanded = state.isExpanded,
        onExpandedChange = {
            setSearching(it)
            setExpanded(it)
        }
    ) {
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (state.results.isEmpty()) {
            Text(
                "No se encontraron resultados",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            // Todo: change to LazyVerticalGrid
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(state.results.size) { movieId ->
                    MovieCardInfo(
                        movie = state.results[movieId],
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        onClick = { movieClickedId ->
                            onMovieClick(movieClickedId)
                            setExpanded(false)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    FocusTrackTheme {
        Search(state = SearchUiState())
    }
}