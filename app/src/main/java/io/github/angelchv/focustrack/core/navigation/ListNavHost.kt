package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.github.angelchv.focustrack.ui.screens.listDetail.ListDetailScreen
import io.github.angelchv.focustrack.ui.screens.lists.ListsScreen
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailScreen

@Composable
fun ListNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: Route = Route.Lists,
) {
    NavHost(
        navController, startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Route.Lists> { ListsScreen() }
        composable<Route.ListDetail> { ListDetailScreen() }
        composable<Route.MovieDetail> { MovieDetailScreen() }
    }
}