package io.github.angelchv.focustrack.ui.screens.auth.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.core.session.SessionManager
import io.github.angelchv.focustrack.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
) : ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set // Only the ViewModel can update the state

    fun onEmailChanged(email: String) {
        uiState = uiState.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        uiState = uiState.copy(confirmPassword = confirmPassword)
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.registerUser(uiState.email, uiState.password)

            result.onSuccess { user ->
                sessionManager.setUser(user)
                onSuccess()
                uiState = uiState.copy(isLoading = false)
            }.onFailure { exception ->
                uiState = uiState.copy(isLoading = false, errorMessage = exception.message)
            }
        }
    }
}