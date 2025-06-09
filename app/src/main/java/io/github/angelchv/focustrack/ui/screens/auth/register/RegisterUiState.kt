package io.github.angelchv.focustrack.ui.screens.auth.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val isHidingPassword: Boolean = true,
    val confirmPassword: String = "",
    val isHidingConfirmPassword: Boolean = true,
    val isLoading: Boolean = false,
    val errorMessageId: Int? = null,
    val isRegisterEnabled: Boolean = false,
    val isRegisterSuccessful: Boolean = false,
)
