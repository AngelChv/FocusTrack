package io.github.angelchv.focustrack.ui.screens.auth.register

import android.app.Activity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.core.auth.CredentialManager
import io.github.angelchv.focustrack.core.auth.SessionManager
import io.github.angelchv.focustrack.core.errors.auth.AuthException
import io.github.angelchv.focustrack.core.util.isEmailValid
import io.github.angelchv.focustrack.core.util.isPasswordValid
import io.github.angelchv.focustrack.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
    private val credentialManager: CredentialManager,
) : ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set // Only the ViewModel can update the state

    fun onEmailChanged(email: String) {
        uiState =
            uiState.copy(
                email = email,
                isRegisterEnabled = validateForm(email, uiState.password, uiState.confirmPassword),
                errorMessageId = null,
            )
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(
            password = password,
            isRegisterEnabled = validateForm(uiState.email, password, uiState.confirmPassword),
            errorMessageId = null,
        )
    }

    fun onTogglePasswordVisibility() {
        uiState = uiState.copy(isHidingPassword = !uiState.isHidingPassword)
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        uiState = uiState.copy(
            confirmPassword = confirmPassword,
            isRegisterEnabled = validateForm(uiState.email, uiState.password, confirmPassword),
            errorMessageId = null,
            )
    }

    fun onToggleConfirmPasswordVisibility() {
        uiState = uiState.copy(isHidingConfirmPassword = !uiState.isHidingConfirmPassword)
    }

    private fun validateForm(email: String, password: String, confirmPassword: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
                && password == confirmPassword
    }

    fun register(activity: Activity, onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(errorMessageId = null)
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.registerUser(uiState.email, uiState.password)

            result.onSuccess { user ->
                sessionManager.setUser(user)
                // Todo: only save when is new
                credentialManager.savePasswordCredential(
                    activity, uiState.email, uiState.password
                )
                onSuccess()
                uiState = uiState.copy(isLoading = false)
            }.onFailure { exception ->
                val errorMessageId = (exception as? AuthException)?.messageResId
                uiState = uiState.copy(isLoading = false, errorMessageId = errorMessageId)
            }
        }
    }
}