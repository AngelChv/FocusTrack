package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import io.github.angelchv.focustrack.ui.screens.auth.LoginScreen
import io.github.angelchv.focustrack.ui.screens.auth.RegisterScreen
import io.github.angelchv.focustrack.ui.screens.home.HomeScreen

/**
 * Composable function responsible for setting up the navigation graph of the application.
 *
 * This function defines all possible navigation destinations (screens) and the routes
 * that lead to them. It uses Jetpack Compose Navigation to manage the navigation flow.
 *
 * @param navController The controller responsible for managing app navigation.
 *                      Defaults to a new controller remembered across compositions.
 * @param startDestination The route that should be displayed when the NavHost is first composed.
 *                         Defaults to the [Route.Home] screen.
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Route = Route.Home,
) {
    NavHost(navController, startDestination = startDestination) {
        composable<Route.Login> { LoginScreen() }
        composable<Route.Register> { RegisterScreen() }
        composable<Route.Home> { HomeScreen() }
    }
}