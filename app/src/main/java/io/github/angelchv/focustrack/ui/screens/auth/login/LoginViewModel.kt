package io.github.angelchv.focustrack.ui.screens.auth.login

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.credentials.CustomCredential
import androidx.credentials.PasswordCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.core.errors.auth.AuthException
import io.github.angelchv.focustrack.core.auth.SessionManager
import io.github.angelchv.focustrack.core.auth.CredentialManager
import io.github.angelchv.focustrack.core.util.isEmailValid
import io.github.angelchv.focustrack.core.util.isPasswordValid
import io.github.angelchv.focustrack.data.repository.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionManager: SessionManager,
    private val credentialManager: CredentialManager,
) : ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set // Only the ViewModel can update the state

    fun onEmailChanged(email: String) {
        uiState =
            uiState.copy(email = email, isLoginEnabled = validateForm(email, uiState.password))
    }

    fun onPasswordChanged(password: String) {
        uiState = uiState.copy(
            password = password, isLoginEnabled = validateForm(uiState.email, password)
        )
    }

    fun onTogglePasswordVisibility() {
        uiState = uiState.copy(isHidingPassword = !uiState.isHidingPassword)
    }

    private fun validateForm(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    fun login(activity: Activity, onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val result = authRepository.loginUser(uiState.email, uiState.password)

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

    fun attemptCredentialSignIn(activity: Activity) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val credential = credentialManager.getCredential(activity)

            when (credential) {
                is PasswordCredential -> {
                    val email = credential.id
                    val password = credential.password
                    uiState = uiState.copy(email = email, password = password)
                    uiState = uiState.copy(isLoading = false, isLoginEnabled = true)
                }

                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val authCredential = credentialManager.getGoogleIdCredential(credential)
                        val result = authRepository.loginWithCredential(authCredential)
                        result.onSuccess { user ->
                            sessionManager.setUser(user)
                            uiState = uiState.copy(isLoading = false, isLoginSuccessful = true)
                        }.onFailure { exception ->
                            val errorMessageId = (exception as? AuthException)?.messageResId
                            uiState =
                                uiState.copy(isLoading = false, errorMessageId = errorMessageId)
                        }
                    } else {
                        Log.e("LoginViewModel", "Not a valid credential: ${credential.type}")
                        // Todo: localization and update errorMessageId in state (noCredentialException)
                        uiState =
                            uiState.copy(isLoading = false, errorMessageId = R.string.error_unknown)
                    }
                }

                else -> {
                    uiState = uiState.copy(isLoading = false)
                }
            }
        }
    }
}