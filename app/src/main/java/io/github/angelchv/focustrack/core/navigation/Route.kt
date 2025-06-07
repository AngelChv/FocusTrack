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
}