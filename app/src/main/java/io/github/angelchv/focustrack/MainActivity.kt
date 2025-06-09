package io.github.angelchv.focustrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.angelchv.focustrack.core.navigation.AppNavHost
import io.github.angelchv.focustrack.core.navigation.Route
import io.github.angelchv.focustrack.ui.components.FocusTrackScaffold
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentRouteString = currentBackStack?.destination?.route
            val currentRoute = Route.fromFullRouteString(currentRouteString)

            FocusTrackTheme {
                FocusTrackScaffold(currentRoute) {
                    AppNavHost(Modifier.align(Alignment.Center), navController)
                }
            }
        }
    }
}