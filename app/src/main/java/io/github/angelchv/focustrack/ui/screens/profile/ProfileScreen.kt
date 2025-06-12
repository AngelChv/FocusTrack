package io.github.angelchv.focustrack.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLogout: () -> Unit
) {
    val state = viewModel.uiState
    Profile(state = state, onLogout = {
        viewModel.onLogout()
        onLogout()
    })
}

@Composable
fun Profile(
    onLogout: () -> Unit = {},
    state: ProfileUiState,
) {
    Column {
        ProfileHeader(
            state.user, Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
            onClick = { onLogout()  }
        ) {
            Text(stringResource(R.string.logout))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, group = "Profile")
@Composable
fun ProfileScreenPreview() {
    FocusTrackTheme {
        Profile(state = ProfileUiState())
    }
}