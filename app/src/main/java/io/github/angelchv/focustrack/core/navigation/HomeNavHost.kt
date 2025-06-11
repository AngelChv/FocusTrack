package io.github.angelchv.focustrack.core.navigation

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.angelchv.focustrack.ui.screens.home.HomeScreen
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailScreen
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    startDestination: Route = Route.Home,
) {
    NavHost(
        navController, startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Route.Home> {
            HomeScreen(
                scrollBehavior = scrollBehavior,
                onNavigateToMovieDetail = { movieId ->
                    Log.d("HomeNavHost", "Navigating to MovieDetailScreen with movieId $movieId")
                    navController.navigate(Route.MovieDetail(movieId))
                })
        }
        composable<Route.MovieDetail> { backStackEntry ->
            val movieDetail = backStackEntry.toRoute<Route.MovieDetail>()
            val viewModel: MovieDetailViewModel = hiltViewModel()
            LaunchedEffect(movieDetail.movieId) {
                movieDetail.movieId?.let {
                    viewModel.loadMovieDetail(it)
                }
            }
            MovieDetailScreen()
        }
    }
}