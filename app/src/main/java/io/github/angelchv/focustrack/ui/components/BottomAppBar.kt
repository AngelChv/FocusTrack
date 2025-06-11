package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.angelchv.focustrack.R

@Composable
fun MovieDetailBottomAppBar() {
    BottomAppBar(
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                FilledIconButton({}) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorito")
                }
                FilledTonalIconButton({}) {
                    Icon(Icons.Default.Check, contentDescription = "Vista")
                }
                FilledTonalIconButton({}) {
                    Icon(painter = painterResource(R.drawable.eye_24), contentDescription = "Por ver")
                }
                // To separate from the fab
                Spacer(modifier = Modifier.width(56.dp))
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MovieDetailBottomAppBarPreview() {
    MovieDetailBottomAppBar()
}