package io.github.angelchv.focustrack.ui.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onSessionRestored: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val isSessionRestored = viewModel.isSessionRestored

    LaunchedEffect(isSessionRestored) {
        when (isSessionRestored) {
            true -> onSessionRestored()
            false -> onNavigateToLogin()
            null -> Unit // Still loading
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
