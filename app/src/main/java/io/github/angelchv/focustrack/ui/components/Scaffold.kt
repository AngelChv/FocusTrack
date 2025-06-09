package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.angelchv.focustrack.core.navigation.Route
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme


@Composable
fun FocusTrackScaffold(
    currentRoute: Route?,
    content: @Composable BoxScope.() -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceBright),
        topBar = {
            when (currentRoute) {
                Route.Login, Route.Register -> FocusTrackTopAppBar()
                else -> {}
            }
        },
        bottomBar = {
            when (currentRoute) {
                Route.Login, Route.Register -> BottomAppBar { BottomChvDesign() }
                else -> {}
            }
        },
    ) { paddingValues: PaddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            content()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun FocusTrackScaffoldPreview() {
    FocusTrackTheme {
        FocusTrackScaffold(Route.Login) {}
    }
}