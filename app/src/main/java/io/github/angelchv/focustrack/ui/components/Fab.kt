package io.github.angelchv.focustrack.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AddToListFab() {
    FloatingActionButton({}) {
        Icon(Icons.Default.Add, contentDescription = "Añadir a lista")
    }
}

@Preview
@Composable
fun AddToListFabPreview() {
    AddToListFab()
}