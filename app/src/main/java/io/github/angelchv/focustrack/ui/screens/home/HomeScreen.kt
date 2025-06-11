package io.github.angelchv.focustrack.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@Composable
fun HomeScreen(
    onNavigateToMovieDetail: () -> Unit,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Greeting(
                name = "Home",
            )

            // TODO: REMOVE
            Button(onClick = onNavigateToMovieDetail) {
                Text("Go to Movie Detail")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FocusTrackTheme {
        Greeting("Android")
    }
}