package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailScreen
import io.github.angelchv.focustrack.ui.screens.search.SearchScreen

@Composable
fun SearchNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Route = Route.Search,
) {
    NavHost(
        navController, startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Route.Search> { SearchScreen() }
        composable<Route.MovieDetail> { MovieDetailScreen() }
    }
}