package io.github.angelchv.focustrack.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.core.navigation.Route
import io.github.angelchv.focustrack.data.remote.auth.FirebaseAuthServiceImpl
import io.github.angelchv.focustrack.data.repository.AuthRepository
import io.github.angelchv.focustrack.ui.components.FocusTrackScaffold
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val state = viewModel.uiState
    Profile(state)
}

@Composable
fun Profile(
    state: ProfileUiState,
) {
    Column {
        ProfileHeader(
            state.user, Modifier.fillMaxWidth()
        )

        // TODO: REMOVE
        Button(onClick = { AuthRepository(FirebaseAuthServiceImpl()).logoutUser() }) {
            Text("Logout")
        }

    }
}

@Preview(showSystemUi = true, group = "Profile")
@Composable
fun ProfileScreenPreview() {
    FocusTrackTheme {
        FocusTrackScaffold(Route.Profile, Route.Profile) {
            Profile(ProfileUiState())
        }
    }
}