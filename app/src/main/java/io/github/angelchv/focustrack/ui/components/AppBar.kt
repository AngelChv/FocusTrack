package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.R

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