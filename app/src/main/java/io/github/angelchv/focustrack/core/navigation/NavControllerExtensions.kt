package io.github.angelchv.focustrack.core.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateAndClear(route: Route) = navigate(route) {
    popUpTo(graph.startDestinationId) {
        inclusive = true
    }
    graph.setStartDestination(route)
}