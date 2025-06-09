package io.github.angelchv.focustrack.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    object Splash : Route

    @Serializable
    object Login : Route

    @Serializable
    object Register : Route

    @Serializable
    object Home : Route

    companion object {
        val all = listOf(Splash, Login, Register, Home)
        fun fromFullRouteString(route: String?): Route? {
            val name = route?.substringAfterLast('.')?.substringBefore('?')
            return all.firstOrNull { it.javaClass.simpleName == name }
        }
    }
}