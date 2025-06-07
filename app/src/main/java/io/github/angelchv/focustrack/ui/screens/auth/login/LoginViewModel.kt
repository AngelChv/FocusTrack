package io.github.angelchv.focustrack.ui.screens.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.core.errors.auth.AuthException
import io.github.angelchv.focustrack.core.session.SessionManager
import io.github.angelchv.focustrack.core.util.isEmailValid
import io.github.angelchv.focustrack.core.util.isPasswordValid
import io.github.angelchv.focustrack.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set // Only the ViewModel can update the state

    fun onEmailChanged(email: String) {
        uiState =
            uiState.copy(email = email, isLoginEnabled = validateForm(email, uiState.password))
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(
            password = password,
            isLoginEnabled = validateForm(uiState.email, password)
        )
    }

    private fun validateForm(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.loginUser(uiState.email, uiState.password)

            result.onSuccess { user ->
                sessionManager.setUser(user)
                onSuccess()
                uiState = uiState.copy(isLoading = false)
            }.onFailure { exception ->
                val errorMessageId = (exception as? AuthException)?.messageResId
                uiState = uiState.copy(isLoading = false, errorMessageId = errorMessageId)
            }
        }
    }
}