package io.github.angelchv.focustrack.ui.screens.auth.login

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.ui.components.EmailField
import io.github.angelchv.focustrack.ui.components.EnableButton
import io.github.angelchv.focustrack.ui.components.FocusTrackLogo
import io.github.angelchv.focustrack.ui.components.GoogleSignInButton
import io.github.angelchv.focustrack.ui.components.HeaderImage
import io.github.angelchv.focustrack.ui.components.PasswordField
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(state.isLoginSuccessful) {
        if (state.isLoginSuccessful) {
            onLoginSuccess()
        }
    }

    LoginScaffold(
        state = state,
        onEmailChanged = { viewModel.onEmailChanged(it) },
        onPasswordChanged = { viewModel.onPasswordChanged(it) },
        onTogglePasswordVisibility = { viewModel.onTogglePasswordVisibility() },
        onLoginClick = {
            activity?.let {
                viewModel.login(it) { onLoginSuccess() }
            }
        },
        onNavigateToRegister = onNavigateToRegister,
        onCredentialSignIn = {
            activity?.let {
                viewModel.attemptCredentialSignIn(
                    activity = it,
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScaffold(
    state: LoginUiState,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onTogglePasswordVisibility: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onCredentialSignIn: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton({}) {
                        FocusTrackLogo(Modifier.size(32.dp))
                    }
                },
                title = {
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Column {
                    HeaderImage(Modifier
                        .size(60.dp)
                        .align(Alignment.CenterHorizontally))
                    Text(
                        text = "© 2025 FocusTrack",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        },
    ) { paddingValues: PaddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Login(
                modifier = Modifier.align(Alignment.Center),
                state = state,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onTogglePasswordVisibility = onTogglePasswordVisibility,
                onLoginClick = onLoginClick,
                onNavigateToRegister = onNavigateToRegister,
                onCredentialSignIn = onCredentialSignIn,
            )
        }
    }
}

@Composable
fun Login(
    modifier: Modifier = Modifier,
    state: LoginUiState,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onTogglePasswordVisibility: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onCredentialSignIn: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                stringResource(R.string.login_title),
                style = MaterialTheme.typography.headlineLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (state.isLoading) {
            item {
                CircularProgressIndicator(Modifier.size(64.dp))
            }
        } else {
            state.errorMessageId?.let {
                item {
                    // Todo: make a ErrorText composable
                    Text(stringResource(it), color = MaterialTheme.colorScheme.error)
                }
            }

            item {
                EmailField(state.email) { onEmailChanged(it) }
            }

            item {
                PasswordField(
                    value = state.password,
                    isHidingPassword = state.isHidingPassword,
                    onToggleHidePassword = { onTogglePasswordVisibility() },
                    onValueChange = { onPasswordChanged(it) },
                )
            }

            item {
                EnableButton(
                    text = stringResource(R.string.login_button),
                    enabled = state.isLoginEnabled
                ) { onLoginClick() }
            }

            item {
                TextButton({ onNavigateToRegister() }) { Text(stringResource(R.string.register_prompt)) }
            }

            item {
                GoogleSignInButton { onCredentialSignIn() }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    FocusTrackTheme {
        LoginScaffold(
            state = LoginUiState(
                email = "angel@gmail.com",
                password = "12345",
                isLoading = false,
                errorMessageId = R.string.error_unknown,
            ),
        )
    }
}