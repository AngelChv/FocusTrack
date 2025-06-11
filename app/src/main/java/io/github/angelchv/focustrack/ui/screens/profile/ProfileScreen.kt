package io.github.angelchv.focustrack.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.data.remote.auth.FirebaseAuthServiceImpl
import io.github.angelchv.focustrack.data.repository.AuthRepository
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
            state.user, Modifier.fillMaxWidth().padding(horizontal = 16.dp)
        )

        // TODO: REMOVE
        Button(onClick = { AuthRepository(FirebaseAuthServiceImpl()).logoutUser() }) {
            Text("Logout")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, group = "Profile")
@Composable
fun ProfileScreenPreview() {
    FocusTrackTheme {
        Profile(ProfileUiState())
    }
}