package io.github.angelchv.focustrack.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.github.angelchv.focustrack.ui.screens.listDetail.ListDetailScreen
import io.github.angelchv.focustrack.ui.screens.listDetail.ListDetailViewModel
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
        composable<Route.Lists> {
            ListsScreen(
                onNavigateToListDetail = { listId ->
                    navController.navigate(Route.ListDetail(listId))
                }
            )
        }
        composable<Route.ListDetail> { backStackEntry ->
            val listDetail = backStackEntry.toRoute<Route.ListDetail>()
            val viewModel: ListDetailViewModel = hiltViewModel()
            LaunchedEffect(listDetail.listId) {
                listDetail.listId?.let {
                    viewModel.loadListDetail(it)
                }
            }
            ListDetailScreen()
        }
        composable<Route.MovieDetail> { MovieDetailScreen() }
    }
}