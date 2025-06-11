package io.github.angelchv.focustrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.angelchv.focustrack.core.navigation.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusTrackScaffold(
    currentFlow: Route?,
    currentRoute: Route?,
    onFlowSelected: (Route) -> Unit = {},
    content: @Composable BoxScope.(TopAppBarScrollBehavior) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceBright),
        topBar = {
            if (currentFlow == Route.Splash && currentRoute != Route.Splash) FocusTrackTopAppBar()
            else if (currentFlow == Route.Home) HomeTopAppBar(scrollBehavior)
            else if (currentRoute == Route.Profile) ProfileTopAppBar()
        },
        bottomBar = {
            if (currentFlow == Route.Splash && currentRoute != Route.Splash) {
                BottomAppBar { BottomChvDesign() }
            } else if (Route.mainFlows.contains(currentFlow)) FocusTrackNavBar(
                currentFlow,
                onFlowSelected = onFlowSelected,
            )
        },
    ) { paddingValues: PaddingValues ->
        Box(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            content(scrollBehavior)
        }
    }
}