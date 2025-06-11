package io.github.angelchv.focustrack.ui.screens.home

import io.github.angelchv.focustrack.domain.model.Movie

data class HomeUiState(
    val isLoading: Boolean = false,
    val trendingMovies: List<Movie>? = null,
    val popularMovies: List<Movie>? = null,
    val topRatedMovies: List<Movie>? = null,
    val nowPlayingMovies: List<Movie>? = null,
    val upcomingMovies: List<Movie>? = null,
)