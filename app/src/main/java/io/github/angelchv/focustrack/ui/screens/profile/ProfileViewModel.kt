package io.github.angelchv.focustrack.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.core.auth.SessionManager
import io.github.angelchv.focustrack.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authRepository: AuthRepository
) : ViewModel() {
    var uiState by mutableStateOf(ProfileUiState())
        private set // Only the ViewModel can update the state

    init {
        viewModelScope.launch {
            sessionManager.user.collect { user ->
                uiState = uiState.copy(user = user)
            }
        }
    }

    fun onLogout() {
        authRepository.logoutUser()
        sessionManager.clearSession()
    }
}