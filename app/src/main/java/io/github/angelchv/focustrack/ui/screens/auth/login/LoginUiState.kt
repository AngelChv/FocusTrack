package io.github.angelchv.focustrack.ui.screens.auth.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessageId: Int? = null,
    val isLoginEnabled: Boolean = false,
)
