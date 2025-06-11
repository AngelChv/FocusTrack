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
    data class MovieDetail(val movieId: Int? = null) : Route

    @Serializable
    object ListDetail : Route

    companion object {
        val mainFlows = listOf(Home, Search, Lists, Profile)

        fun fromFullRouteString(route: String?): Route? {
            val name = route?.substringAfterLast('.')?.substringBefore('/')?.substringBefore('?')

            return when (name) {
                Splash::class.simpleName -> Splash
                Login::class.simpleName -> Login
                Register::class.simpleName -> Register
                Home::class.simpleName -> Home
                Search::class.simpleName -> Search
                Lists::class.simpleName -> Lists
                Profile::class.simpleName -> Profile
                ListDetail::class.simpleName -> ListDetail
                MovieDetail::class.simpleName -> MovieDetail()
                else -> null
            }
        }
    }
}