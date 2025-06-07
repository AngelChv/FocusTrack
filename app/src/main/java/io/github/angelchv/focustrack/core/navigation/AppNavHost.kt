package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import io.github.angelchv.focustrack.ui.screens.auth.login.LoginScreen
import io.github.angelchv.focustrack.ui.screens.auth.register.RegisterScreen
import io.github.angelchv.focustrack.ui.screens.home.HomeScreen
import io.github.angelchv.focustrack.ui.screens.splash.SplashScreen

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
    startDestination: Route = Route.Splash,
) {
    NavHost(navController, startDestination = startDestination) {
        composable<Route.Splash> {
            SplashScreen(
                onSessionRestored = {
                    navController.navigateAndClear(Route.Home)
                },
                onNavigateToLogin = {
                    navController.navigateAndClear(Route.Login)
                },
            )
        }

        composable<Route.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Route.Register) },
            )
        }
        composable<Route.Register> { RegisterScreen() }

        composable<Route.Home> { HomeScreen() }
    }
}

fun NavHostController.navigateAndClear(route: Route) = navigate(route) {
    popUpTo(graph.startDestinationId) {
        inclusive = true
    }
    graph.setStartDestination(route)
}