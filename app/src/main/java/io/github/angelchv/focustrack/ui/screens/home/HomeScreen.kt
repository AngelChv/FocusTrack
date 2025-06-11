package io.github.angelchv.focustrack.ui.screens.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi.Companion.ImageSize
import io.github.angelchv.focustrack.domain.model.Movie
import io.github.angelchv.focustrack.ui.components.MovieCard
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToMovieDetail: (movieId: Int) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState

    Home(
        state = state,
        scrollBehavior = scrollBehavior,
        onNavigateToMovieDetail = onNavigateToMovieDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    state: HomeUiState,
    scrollBehavior: TopAppBarScrollBehavior,
    onNavigateToMovieDetail: (movieId: Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 64.dp)
    ) {
        state.trendingMovies?.let { trendingMovies ->
            item {
                MovieSection(
                    title = "Tendencias",
                    movies = trendingMovies,
                    // Todo: send movieId to detail
                    onMovieClick = { movieId -> onNavigateToMovieDetail(movieId) }
                )
            }
        }

        state.popularMovies?.let { popularMovies ->
            item {
                MovieSection(
                    title = "Populares",
                    movies = popularMovies,
                    // Todo: send movieId to detail
                    onMovieClick = { movieId -> onNavigateToMovieDetail(movieId) }
                )
            }
        }

        state.topRatedMovies?.let { topRatedMovies ->
            item {
                MovieSection(
                    title = "Mejor Valoradas",
                    movies = topRatedMovies,
                    // Todo: send movieId to detail
                    onMovieClick = { movieId -> onNavigateToMovieDetail(movieId) }
                )
            }
        }

        state.nowPlayingMovies?.let { nowPlayingMovies ->
            item {
                MovieSection(
                    title = "En Cines",
                    movies = nowPlayingMovies,
                    // Todo: send movieId to detail
                    onMovieClick = { movieId -> onNavigateToMovieDetail(movieId) }
                )
            }
        }

        state.upcomingMovies?.let { upcomingMovies ->
            item {
                MovieSection(
                    title = "Por Estrenar",
                    movies = upcomingMovies,
                    // Todo: send movieId to detail
                    onMovieClick = { movieId -> onNavigateToMovieDetail(movieId) }
                )
            }
        }
    }
}

@Composable
fun MovieSection(
    title: String, movies: List<Movie>,
    onMovieClick: (movieId: Int) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            // Todo: implement see more button
            /*
            OutlinedButton({},
                Modifier.padding(horizontal = 16.dp)) {
                Text(text = "Ver más")
            }
             */
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies.size) { index ->
                MovieCard(
                    movie = movies[index],
                    size = ImageSize.W185,
                    modifier = Modifier
                        .width(120.dp)
                        .aspectRatio(2 / 3f),
                    onClick = {
                        Log.d("Home", "Clicked on movie ${movies[index].title}")
                        onMovieClick(movies[index].id)
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = "id:pixel_5", showSystemUi = false, showBackground = true)
@Composable
fun HomePreview() {
    val exampleMovies = List(5) { index ->
        Movie.example.copy(
            id = index,
            title = "Movie $index",
            posterPath = null
        )
    }

    val previewState = HomeUiState(
        isLoading = false,
        trendingMovies = exampleMovies,
        popularMovies = exampleMovies,
        topRatedMovies = exampleMovies,
        nowPlayingMovies = exampleMovies,
        upcomingMovies = exampleMovies,
    )

    FocusTrackTheme {
        Home(
            state = previewState,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            onNavigateToMovieDetail = {}
        )
    }
}