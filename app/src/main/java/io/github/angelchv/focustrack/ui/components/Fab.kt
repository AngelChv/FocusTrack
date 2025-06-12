package io.github.angelchv.focustrack.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.data.di.activityViewModel
import io.github.angelchv.focustrack.ui.components.movie.AddMovieToListsSheet
import io.github.angelchv.focustrack.ui.screens.lists.UserListsViewModel
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailViewModel

@Composable
fun AddToListFab(
    listViewModel: UserListsViewModel = activityViewModel(),
    movieViewModel: MovieDetailViewModel = activityViewModel()
) {
    val openSheet = remember { mutableStateOf(false) }
    val userLists = listViewModel.uiState.userLists

    FloatingActionButton(onClick = { openSheet.value = true }) {
        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_to_list))
    }

    if (openSheet.value) {
        AddMovieToListsSheet(
            userLists = userLists,
            onDismiss = { openSheet.value = false },
            onConfirm = { selectedListIds ->
                selectedListIds.forEach { listId ->
                    movieViewModel.addMovieToList(listId)
                }
            }
        )
    }
}

@Composable
fun CreateListFab(
    viewModel: UserListsViewModel = activityViewModel()
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    ExtendedFloatingActionButton(
        onClick = { openAlertDialog.value = true },
        icon = { Icon(Icons.Filled.Add, stringResource(R.string.add_to_list)) },
        text = { Text(text = stringResource(R.string.add_to_list)) },
    )

    when {
        openAlertDialog.value -> {
            CreateListDialog(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    viewModel.createList(it)
                },
                dialogTitle = stringResource(R.string.list_name),
            )
        }
    }
}

@Preview
@Composable
fun AddToListFabPreview() {
    AddToListFab()
}