package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.angelchv.focustrack.ui.screens.profile.ProfileScreen

@Composable
fun ProfileNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Route = Route.Profile,
) {
    NavHost(
        navController, startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Route.Profile> { ProfileScreen() }
    }
}