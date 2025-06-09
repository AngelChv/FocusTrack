package io.github.angelchv.focustrack.data.repository

import com.google.firebase.auth.AuthCredential
import io.github.angelchv.focustrack.core.errors.auth.AuthException
import io.github.angelchv.focustrack.data.remote.auth.AuthService
import io.github.angelchv.focustrack.domain.model.User
import javax.inject.Inject
import kotlin.Result

/**
 * Manages authentication data operations using an [AuthService].
 *
 * @param authService Service for authentication tasks.
 */
class AuthRepository @Inject constructor(
    private val authService: AuthService,
) {
    /**
     * Logs in a user.
     *
     * @param email User's email.
     * @param password User's password.
     * @return [Result] with [User] on success, or [AuthException] on failure.
     */
    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val user = authService.login(email, password)
            if (user != null) Result.success(user)
            else Result.failure(AuthException.UnknownAuthException())
        } catch (e: AuthException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(AuthException.UnknownAuthException(e))
        }
    }

    suspend fun loginWithCredential(credential: AuthCredential): Result<User> {
        return try {
            val user = authService.loginWhitCredential(credential)
            if (user != null) Result.success(user)
            else Result.failure(AuthException.UnknownAuthException())
        } catch (e: AuthException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(AuthException.UnknownAuthException(e))
        }
    }

    /**
     * Registers a new user.
     *
     * @param email New user's email.
     * @param password New user's password.
     * @return [Result] with new [User] on success, or [AuthException] on failure.
     */
    suspend fun registerUser(email: String, password: String): Result<User> {
        return try {
            val user = authService.register(email, password)
            if (user != null) Result.success(user)
            else Result.failure(AuthException.UnknownAuthException())
        } catch (e: AuthException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(AuthException.UnknownAuthException(e))
        }
    }

    /** Logs out the current user. */
    fun logoutUser() = authService.logout()

    /**
     * Gets the current authenticated user.
     *
     * @return Current [User] or `null` if not authenticated.
     */
    fun getCurrentUser(): User? = authService.getCurrentUser()
}