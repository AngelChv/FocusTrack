package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.angelchv.focustrack.ui.screens.home.HomeScreen
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailScreen

@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Route = Route.Home,
) {
    NavHost(
        navController, startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Route.Home> {
            HomeScreen(onNavigateToMovieDetail = {
                navController.navigate(Route.MovieDetail)
            })
        }
        composable<Route.MovieDetail> { MovieDetailScreen() }
    }
}