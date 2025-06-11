package io.github.angelchv.focustrack.ui.screens.movieDetail

import io.github.angelchv.focustrack.domain.model.MovieDetail


data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
)
