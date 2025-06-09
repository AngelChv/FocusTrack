package io.github.angelchv.focustrack.data.remote.auth

import com.google.firebase.auth.AuthCredential
import io.github.angelchv.focustrack.domain.model.User

/**
 * Defines operations for user authentication.
 *
 * This interface outlines the contract for services that handle
 * user login, registration, logout, and retrieval of the current user.
 */
interface AuthService {

    /**
     * Attempts to log in a user with the provided credentials.
     *
     * @param email The user's email.
     * @param password The user's password.
     * @return The [User] object if login is successful, `null` otherwise.
     */
    suspend fun login(email: String, password: String): User?

    suspend fun loginWhitCredential(credential: AuthCredential): User?

    /**
     * Attempts to register a new user with the provided credentials.
     *
     * @param email The email for the new user.
     * @param password The password for the new user.
     * @return The newly registered [User] object if registration is successful, `null` otherwise.
     */
    suspend fun register(email: String, password: String): User?

    /**
     * Logs out the currently authenticated user.
     */
    fun logout()

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The current [User] if authenticated, `null` otherwise.
     */
    fun getCurrentUser(): User?
}