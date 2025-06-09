package io.github.angelchv.focustrack.ui.screens.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.core.auth.SessionManager
import io.github.angelchv.focustrack.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authRepository: AuthRepository,
) : ViewModel() {
    var isSessionRestored by mutableStateOf<Boolean?>(null)
        private set

    init {
        restoreSession()
    }

    private fun restoreSession() {
        val user = authRepository.getCurrentUser()
        if (user != null) {
            sessionManager.setUser(user)
            isSessionRestored = true
        } else {
            isSessionRestored = false
        }
    }
}