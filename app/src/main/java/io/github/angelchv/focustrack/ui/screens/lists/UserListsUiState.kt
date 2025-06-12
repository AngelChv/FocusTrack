package io.github.angelchv.focustrack.ui.screens.lists

import io.github.angelchv.focustrack.domain.model.UserList

data class UserListsUiState(
    val isLoading: Boolean = false,
    val userLists: List<UserList> = emptyList(),
)
