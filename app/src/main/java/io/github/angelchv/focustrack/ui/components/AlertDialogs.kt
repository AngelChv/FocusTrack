package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.angelchv.focustrack.R

@Composable
fun CreateListDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit,
    dialogTitle: String,
) {
    var listName by remember { mutableStateOf("") }

    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            OutlinedTextField(
                value = listName,
                onValueChange = { listName = it },
                placeholder = { Text(dialogTitle) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (listName.isNotBlank()) onConfirmation(listName)
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.dismiss))
            }
        }
    )
}

@Preview
@Composable
fun CreateListDialogPreview() {
    CreateListDialog(
        onDismissRequest = { },
        onConfirmation = { },
        dialogTitle = "Title"
    )
}