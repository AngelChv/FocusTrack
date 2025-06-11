package io.github.angelchv.focustrack.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    // Auth
    @Serializable
    object Splash : Route

    @Serializable
    object Login : Route

    @Serializable
    object Register : Route

    // Main routes
    @Serializable
    object Home : Route

    @Serializable
    object Search : Route

    @Serializable
    object Lists : Route

    @Serializable
    object Profile : Route

    // Details
    @Serializable
    object MovieDetail : Route

    @Serializable
    object ListDetail : Route

    companion object {
        val all = listOf(
            Splash, Login, Register, Home, Search,
            Lists, Profile, MovieDetail, ListDetail,
        )

        val mainFlows = listOf(Home, Search, Lists, Profile)

        fun fromFullRouteString(route: String?): Route? {
            val name = route?.substringAfterLast('.')?.substringBefore('?')
            return all.firstOrNull { it.javaClass.simpleName == name }
        }
    }
}