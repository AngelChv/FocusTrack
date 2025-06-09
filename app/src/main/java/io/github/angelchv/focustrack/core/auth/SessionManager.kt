package io.github.angelchv.focustrack.core.auth

import io.github.angelchv.focustrack.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages the current user session throughout the application.
 *
 * Provides a [StateFlow] to observe the current [User] and methods
 * to update or clear the session. It's a singleton, ensuring a single
 * source of truth for user session state.
 */
@Singleton
class SessionManager @Inject constructor() {

    /**
     * Private mutable state flow holding the current user.
     * Emits `null` if no user is in session.
     */
    private val _user = MutableStateFlow<User?>(null)

    /**
     * Publicly exposed state flow for observing the current user session.
     * Consumers can collect this flow to react to changes in the user's login state.
     */
    val user: StateFlow<User?> = _user

    /**
     * Sets the current active user in the session.
     *
     * @param user The [User] object representing the logged-in user.
     */
    fun setUser(user: User) {
        _user.value = user
    }

    /**
     * Clears the current user session, typically on logout.
     * Sets the user state to `null`.
     */
    fun clearSession() {
        _user.value = null
    }
}