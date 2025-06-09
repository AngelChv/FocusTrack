package io.github.angelchv.focustrack.ui.screens.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isHidingPassword: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessageId: Int? = null,
    val isLoginEnabled: Boolean = false,
    val isLoginSuccessful: Boolean = false,
)
