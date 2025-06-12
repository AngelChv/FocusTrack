package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.angelchv.focustrack.data.di.activityViewModel
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailScreen
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailViewModel
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
        composable<Route.Search> {
            SearchScreen(
                onNavigateToMovieDetail = { movieId ->
                    navController.navigate(Route.MovieDetail(movieId))
                }
            )
        }

        composable<Route.MovieDetail> { backStackEntry ->
            val movieDetail = backStackEntry.toRoute<Route.MovieDetail>()
            // Important! for use the same instance than the FAB
            val viewModel: MovieDetailViewModel = activityViewModel()
            LaunchedEffect(movieDetail.movieId) {
                movieDetail.movieId?.let {
                    viewModel.loadMovieDetail(it)
                }
            }
            MovieDetailScreen()
        }
    }
}