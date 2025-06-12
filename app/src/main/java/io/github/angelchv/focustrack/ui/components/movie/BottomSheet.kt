package io.github.angelchv.focustrack.ui.components.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.domain.model.UserList
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@Composable
fun SelectableListItem(
    list: UserList,
    isSelected: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    ElevatedCard(Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { onToggle(!isSelected) }
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = list.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Checkbox(
                checked = isSelected,
                onCheckedChange = onToggle
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMovieToListsSheet(
    userLists: List<UserList>,
    onDismiss: () -> Unit,
    onConfirm: (selectedListIds: List<String>) -> Unit,
) {
    val selectedLists = remember { mutableStateMapOf<String, Boolean>() }

    LaunchedEffect(userLists) {
        userLists.forEach { selectedLists[it.id] = false }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Text(
            text = stringResource(R.string.add_to_list),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn {
            items(userLists.size) { index ->
                val list = userLists[index]
                SelectableListItem(
                    list = list,
                    isSelected = selectedLists[list.id] == true,
                    onToggle = { selectedLists[list.id] = it }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = {
                val selectedIds = selectedLists.filterValues { it }.keys.toList()
                onConfirm(selectedIds)
                onDismiss()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = stringResource(R.string.confirm))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectableListItemPreview() {
    FocusTrackTheme {
        SelectableListItem(
            list = UserList("1", "List 1", emptyList()),
            isSelected = true,
            onToggle = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddMovieToListsSheetPreview() {
    FocusTrackTheme {
        AddMovieToListsSheet(
            userLists = listOf(UserList("1", "List 1", emptyList())),
            onDismiss = {},
            onConfirm = {},
        )
    }
}