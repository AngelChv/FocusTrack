package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.angelchv.focustrack.ui.screens.auth.login.LoginScreen
import io.github.angelchv.focustrack.ui.screens.auth.register.RegisterScreen
import io.github.angelchv.focustrack.ui.screens.splash.SplashScreen

@Composable
fun AuthNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Route = Route.Splash,
    onNavigateToHome: () -> Unit,
) {
    NavHost(
        navController, startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Route.Splash> {
            SplashScreen(
                onSessionRestored = { onNavigateToHome() },
                onNavigateToLogin = {
                    navController.navigateAndClear(Route.Login)
                },
            )
        }

        composable<Route.Login> {
            LoginScreen(
                onLoginSuccess = { onNavigateToHome() },
                onNavigateToRegister = { navController.navigate(Route.Register) },
            )
        }

        composable<Route.Register> {
            RegisterScreen(
                onRegisterSuccess = { onNavigateToHome() },
                onNavBack = navController::popBackStack,
            )
        }
    }
}