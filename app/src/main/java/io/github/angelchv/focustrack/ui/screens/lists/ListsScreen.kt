package io.github.angelchv.focustrack.ui.screens.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.data.di.activityViewModel
import io.github.angelchv.focustrack.domain.model.UserList

@Composable
fun ListsScreen(
    modifier: Modifier = Modifier,
    onNavigateToListDetail: (listId: String) -> Unit,
    viewModel: UserListsViewModel = activityViewModel(),
) {
    val state = viewModel.uiState

    UserLists(state, modifier, onNavigateToListDetail)
}

@Composable
fun UserLists(
    state: UserListsUiState,
    modifier: Modifier = Modifier,
    onNavigateToListDetail: (String) -> Unit,
) {
    Column(modifier) {
        if (state.userLists.isEmpty() && !state.isLoading) {
            Box(Modifier.fillMaxSize()) {
                Text(
                    "Crea una lista con el botón de abajo a la derecha +",
                    Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(state.userLists.size) { index ->
                ListItem(
                    list = state.userLists[index], modifier = Modifier.padding(8.dp),
                    onClick = onNavigateToListDetail
                )
            }
        }
    }
}

@Composable
fun ListItem(
    list: UserList, modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    OutlinedCard(modifier = modifier, onClick = { onClick(list.tmdbId) }) {
        Text(
            text = list.name,
            textAlign = TextAlign.Center,
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserListsPreview() {
    UserLists(
        UserListsUiState(
            userLists = (1..10).map {
                UserList("", "Lista $it", emptyList<Int>())
            }
        )
    ) {}
}