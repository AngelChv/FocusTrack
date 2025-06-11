package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.core.navigation.Route
import io.github.angelchv.focustrack.ui.screens.profile.ProfileViewModel

@Composable
fun FocusTrackNavBar(
    currentFlow: Route?,
    viewModel: ProfileViewModel = hiltViewModel(),
    onFlowSelected: (Route) -> Unit = {},
) {
    val photoUrl = viewModel.uiState.user?.photoUrl

    NavigationBar {
        NavigationBarItem(
            selected = currentFlow == Route.Home,
            onClick = { onFlowSelected(Route.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
            label = { Text(stringResource(R.string.home)) }
        )

        NavigationBarItem(
            selected = currentFlow == Route.Search,
            onClick = { onFlowSelected(Route.Search) },
            icon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
            label = { Text(stringResource(R.string.search)) }
        )

        NavigationBarItem(
            selected = currentFlow == Route.Lists,
            onClick = { onFlowSelected(Route.Lists) },
            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.List,
                    contentDescription = stringResource(R.string.lists)
                )
            },
            label = { Text(stringResource(R.string.lists)) }
        )

        NavigationBarItem(
            selected = currentFlow == Route.Profile,
            onClick = { onFlowSelected(Route.Profile) },
            icon = {
                UserImage(Modifier.size(24.dp), photoUrl)
            },
            label = { Text(stringResource(R.string.profile)) }
        )
    }
}