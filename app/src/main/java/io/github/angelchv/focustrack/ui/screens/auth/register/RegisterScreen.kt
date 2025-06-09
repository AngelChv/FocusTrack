package io.github.angelchv.focustrack.ui.screens.auth.register

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.core.navigation.Route
import io.github.angelchv.focustrack.ui.components.EmailField
import io.github.angelchv.focustrack.ui.components.EnableButton
import io.github.angelchv.focustrack.ui.components.FocusTrackScaffold
import io.github.angelchv.focustrack.ui.components.PasswordField
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onNavBack: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    val context = LocalContext.current
    val activity = context as? Activity

    LaunchedEffect(state.isRegisterSuccessful) {
        if (state.isRegisterSuccessful) {
            onRegisterSuccess()
        }
    }

    Register(
        state = state,
        onEmailChanged = { viewModel.onEmailChanged(it) },
        onPasswordChanged = { viewModel.onPasswordChanged(it) },
        onTogglePasswordVisibility = { viewModel.onTogglePasswordVisibility() },
        onConfirmPasswordChanged = { viewModel.onConfirmPasswordChanged(it) },
        onToggleConfirmPasswordVisibility = { viewModel.onToggleConfirmPasswordVisibility() },
        onRegisterClick = {
            activity?.let {
                viewModel.register(it) { onRegisterSuccess() }
            }
        },
        onNavBack = onNavBack,
    )
}


@Composable
fun Register(
    modifier: Modifier = Modifier,
    state: RegisterUiState,
    onEmailChanged: (String) -> Unit = {},
    onPasswordChanged: (String) -> Unit = {},
    onTogglePasswordVisibility: () -> Unit = {},
    onConfirmPasswordChanged: (String) -> Unit = {},
    onToggleConfirmPasswordVisibility: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onNavBack: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                stringResource(R.string.register_title),
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
                PasswordField(
                    value = state.confirmPassword,
                    label = stringResource(R.string.confirm_password_label),
                    isHidingPassword = state.isHidingConfirmPassword,
                    onToggleHidePassword = { onToggleConfirmPasswordVisibility() },
                    onValueChange = { onConfirmPasswordChanged(it) },
                )
            }

            item {
                EnableButton(
                    text = stringResource(R.string.register_button),
                    enabled = state.isRegisterEnabled,
                ) { onRegisterClick() }
            }

            item {
                TextButton({ onNavBack() }) { Text(stringResource(R.string.already_have_account)) }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
    FocusTrackTheme {
        FocusTrackScaffold(Route.Register) {
            Register(
                modifier = Modifier.align(Alignment.Center),
                state = RegisterUiState(
                    email = "angel@gmail.com",
                    password = "12345",
                    isLoading = false,
                    errorMessageId = R.string.error_unknown,
                ),
            )
        }
    }
}