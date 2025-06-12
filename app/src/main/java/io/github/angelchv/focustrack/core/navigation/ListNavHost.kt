package io.github.angelchv.focustrack.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.angelchv.focustrack.data.di.activityViewModel
import io.github.angelchv.focustrack.ui.screens.listDetail.ListDetailScreen
import io.github.angelchv.focustrack.ui.screens.listDetail.ListDetailViewModel
import io.github.angelchv.focustrack.ui.screens.lists.ListsScreen
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailScreen
import io.github.angelchv.focustrack.ui.screens.movieDetail.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    startDestination: Route = Route.Lists,
) {
    NavHost(
        navController, startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<Route.Lists> {
            ListsScreen(
                scrollBehavior = scrollBehavior,
                onNavigateToListDetail = { listId ->
                    navController.navigate(Route.ListDetail(listId))
                }
            )
        }
        composable<Route.ListDetail> { backStackEntry ->
            val listDetail = backStackEntry.toRoute<Route.ListDetail>()
            val viewModel: ListDetailViewModel = activityViewModel()
            LaunchedEffect(listDetail.listId) {
                listDetail.listId?.let {
                    viewModel.loadListDetail(it)
                }
            }
            ListDetailScreen(
                scrollBehavior = scrollBehavior,
                onNavigateToMovieDetail = { movieId ->
                    navController.navigate(Route.MovieDetail(movieId))
                },
                onNavigateBack = {
                    navController.popBackStack()
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