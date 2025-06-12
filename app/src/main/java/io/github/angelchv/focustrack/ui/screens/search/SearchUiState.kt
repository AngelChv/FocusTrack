package io.github.angelchv.focustrack.ui.screens.search

import io.github.angelchv.focustrack.domain.model.Movie

data class SearchUiState(
    val isLoading: Boolean = false,
    val isSearching: Boolean = false,
    val isExpanded: Boolean = false,
    val query: String = "",
    val results: List<Movie> = emptyList(),
)
