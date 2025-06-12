package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.core.navigation.Route
import io.github.angelchv.focustrack.data.di.activityViewModel
import io.github.angelchv.focustrack.ui.screens.listDetail.ListDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusTrackTopAppBar() {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(scrollBehavior: TopAppBarScrollBehavior? = TopAppBarDefaults.enterAlwaysScrollBehavior()) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.displaySmall
            )
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior? = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    currentRoute: Route? = null,
    listDetailViewModel: ListDetailViewModel = activityViewModel(),
) {
    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        ConfirmDialog(
            dialogTitle = stringResource(R.string.delete_list),
            onConfirmation = {
                openDialog.value = false
                listDetailViewModel.deleteCurrentList()
            },
            onDismissRequest = { openDialog.value = false }
        )
    }

    TopAppBar(
        title = {
            Text(
                stringResource(R.string.lists),
                style = MaterialTheme.typography.displaySmall
            )
        },
        scrollBehavior = scrollBehavior,
        actions = {
            if (currentRoute is Route.ListDetail) {
                IconButton({}, enabled = false) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_list),
                    )
                }

                IconButton({ openDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_list),
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar() {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.profile),
                style = MaterialTheme.typography.displaySmall
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = stringResource(R.string.edit_profile),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.settings),
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    )
}

@Preview
@Composable
fun FocusTrackTopAppBarPreview() {
    FocusTrackTopAppBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeTopAppBarPreview() {
    HomeTopAppBar()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ListsTopAppBarPreview() {
    ListsTopAppBar()
}

@Preview
@Composable
fun ProfileTopAppBarPreview() {
    ProfileTopAppBar()
}
