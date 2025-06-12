package io.github.angelchv.focustrack.ui.screens.listDetail

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.data.di.activityViewModel
import io.github.angelchv.focustrack.domain.model.UserList
import io.github.angelchv.focustrack.ui.components.MovieCardInfo
import io.github.angelchv.focustrack.ui.screens.lists.UserListsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetailScreen(
    onNavigateToMovieDetail: (Int) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateBack: () -> Unit,
    viewModel: ListDetailViewModel = activityViewModel(),
    listViewModel: UserListsViewModel = activityViewModel(),
) {
    val state = viewModel.uiState

    ListDetail(
        state = state, scrollBehavior = scrollBehavior,
        onNavigateToMovieDetail = onNavigateToMovieDetail,
        onRemoveMovieClick = { movieId ->
            viewModel.removeMovieFromList(movieId)
        },
        onNavigateBack = {
            // Todo: Check it
            listViewModel.removeList(state.list?.id ?: "", onComplete = onNavigateBack)
            viewModel.onNavigatedBack()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListDetail(
    state: ListDetailUiState,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    onNavigateToMovieDetail: (Int) -> Unit = {},
    onRemoveMovieClick: (Int) -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    LaunchedEffect(state.navigateBack) {
        if (state.navigateBack) {
            Log.d("ListDetailScreen", "navigateBack")
            onNavigateBack()
        }
    }

    // Todo: show snack bar or a text whit the errors.
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
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                contentPadding = PaddingValues(bottom = 64.dp),
            ) {
                items(it.movieIds.size) { index ->
                    val movie = state.movies?.get(index)?.toMovie()
                    movie?.let {
                        MovieCardInfo(
                            movie = it,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            onClick = { movieClickedId ->
                                onNavigateToMovieDetail(movieClickedId)
                            },
                            showRemoveButton = true,
                            onRemoveClick = { movieClickedId ->
                                onRemoveMovieClick(movieClickedId)
                            }
                        )
                    }
                }
            }
        }
    } ?: run {
        if (!state.isLoading) {
            Text("No list found")
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(64.dp)
                    .fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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