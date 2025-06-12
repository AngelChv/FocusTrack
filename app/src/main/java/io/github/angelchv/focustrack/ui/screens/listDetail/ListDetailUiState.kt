package io.github.angelchv.focustrack.ui.screens.listDetail

import io.github.angelchv.focustrack.domain.model.MovieDetail
import io.github.angelchv.focustrack.domain.model.UserList

data class ListDetailUiState(
    val isLoading: Boolean = false,
    val list: UserList? = null,
    val movies: List<MovieDetail>? = null,
    val errorMessage: String? = null,
    val navigateBack: Boolean = false,
)
