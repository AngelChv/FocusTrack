package io.github.angelchv.focustrack.ui.screens.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.data.di.activityViewModel
import io.github.angelchv.focustrack.domain.model.UserList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(
    modifier: Modifier = Modifier,
    onNavigateToListDetail: (listId: String) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    // Important! for use the same instance than the FAB
    viewModel: UserListsViewModel = activityViewModel(),
) {
    val state = viewModel.uiState

    UserLists(
        state = state,
        modifier = modifier,
        onNavigateToListDetail = onNavigateToListDetail,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserLists(
    state: UserListsUiState,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    modifier: Modifier = Modifier,
    onNavigateToListDetail: (String) -> Unit,
) {
    if (state.isLoading && state.userLists.isEmpty()) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                Modifier
                    .align(Alignment.Center)
                    .padding(64.dp)
            )
        }
    }

    Column(modifier.fillMaxSize()) {
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
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).fillMaxSize(),
            columns = GridCells.Adaptive(150.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(bottom = 64.dp),
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
    OutlinedCard(modifier = modifier, onClick = { onClick(list.id) }) {
        Text(
            text = list.name,
            textAlign = TextAlign.Center,
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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