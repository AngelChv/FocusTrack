package io.github.angelchv.focustrack.ui.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.ui.components.EmailField
import io.github.angelchv.focustrack.ui.components.EnableButton
import io.github.angelchv.focustrack.ui.components.FocusTrackLogo
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

    LoginScaffold(
        state = state,
        onEmailChanged = { viewModel.onEmailChanged(it) },
        onPasswordChanged = { viewModel.onPasswordChanged(it) },
        onLoginClick = { viewModel.login { onLoginSuccess() } },
        onNavigateToRegister = onNavigateToRegister
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScaffold(
    state: LoginUiState,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onLoginClick: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.login_title),
                        Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
            )
        }
    ) { paddingValues: PaddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Login(
                modifier = Modifier
                    .align(Alignment.Center),
                state = state,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onLoginClick = onLoginClick,
                onNavigateToRegister = onNavigateToRegister,
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
    onLoginClick: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FocusTrackLogo()
        }

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
                isHidingPassword = false, // Todo: implement in the viewmodel
                onToggleHidePassword = {},
                onValueChange = { onPasswordChanged(it) },
            )
        }

        item {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                EnableButton(
                    text = stringResource(R.string.login_button),
                    enabled = state.isLoginEnabled
                ) { onLoginClick() }
                TextButton({ onNavigateToRegister() }) { Text(stringResource(R.string.register_prompt)) }
            }
        }

        item {
            HeaderImage(modifier = Modifier.padding(all = 16.dp))
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