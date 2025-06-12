package io.github.angelchv.focustrack.ui.screens.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.data.repository.AuthRepository
import io.github.angelchv.focustrack.data.repository.UserListsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListsViewModel @Inject constructor(
    private val listRepository: UserListsRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {
    var uiState by mutableStateOf(UserListsUiState())
        private set


    init {
        loadLists()
    }

    private fun loadLists() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val userId = authRepository.getCurrentUser()?.uid
            if (userId == null) return@launch
            val lists = listRepository.getUserLists(userId)
            uiState = uiState.copy(userLists = lists, isLoading = false)
        }
    }

    fun createList(name: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val userId = authRepository.getCurrentUser()?.uid
            if (userId == null) return@launch
            val success = listRepository.addList(userId, name)
            if (success) loadLists()
            uiState = uiState.copy(isLoading = false)
        }
    }
}