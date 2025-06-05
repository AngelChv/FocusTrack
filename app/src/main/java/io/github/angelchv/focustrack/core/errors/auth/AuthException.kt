package io.github.angelchv.focustrack.core.errors.auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import io.github.angelchv.focustrack.R
import io.github.angelchv.focustrack.core.errors.FocusTrackException

/**
 * Represents different types of authentication-related errors in a Firebase-authenticated app.
 *
 * @param messageResId Resource ID of the message that should be displayed to the user.
 * @param originalError The original exception that caused this error, if available.
 */
sealed class AuthException(
    messageResId: Int,
    originalError: Throwable? = null,
) : FocusTrackException(messageResId, originalError) {
    class InvalidCredentialsException(originalError: Throwable? = null) :
        AuthException(R.string.error_invalid_credentials, originalError)

    class InvalidEmailException(originalError: Throwable? = null) :
        AuthException(R.string.error_invalid_email, originalError)

    class WrongPasswordException(originalError: Throwable? = null) :
        AuthException(R.string.error_wrong_password, originalError)

    class UserNotFoundException(originalError: Throwable? = null) :
        AuthException(R.string.error_user_not_found, originalError)

    class UserAlreadyExistsException(originalError: Throwable? = null) :
        AuthException(R.string.error_user_already_exists, originalError)

    class WeakPasswordException(originalError: Throwable? = null) :
        AuthException(R.string.error_weak_password, originalError)

    class TooManyRequestsException(originalError: Throwable? = null) :
        AuthException(R.string.error_too_many_requests, originalError)

    class NetworkException(originalError: Throwable? = null) :
        AuthException(R.string.error_network, originalError)

    class RequiresRecentLoginException(originalError: Throwable? = null) :
        AuthException(R.string.error_requires_recent_login, originalError)

    class UnknownAuthException(originalError: Throwable? = null) :
        AuthException(R.string.error_unknown, originalError)
}

/**
 * Maps a generic [Exception] from Firebase Authentication to a more specific [AuthException].
 *
 * This allows the UI to handle errors in a type-safe and decoupled way using localized messages.
 */
fun Exception.toAuthException(): AuthException = when (this) {
    is FirebaseAuthWeakPasswordException -> AuthException.WeakPasswordException(this)
    is FirebaseAuthInvalidCredentialsException -> {
        when (errorCode) {
            "ERROR_INVALID_EMAIL" -> AuthException.InvalidEmailException(this)
            "ERROR_WRONG_PASSWORD" -> AuthException.WrongPasswordException(this)
            else -> AuthException.InvalidCredentialsException(this)
        }
    }

    is FirebaseAuthInvalidUserException -> AuthException.UserNotFoundException(this)
    is FirebaseAuthUserCollisionException -> AuthException.UserAlreadyExistsException(this)
    is FirebaseAuthRecentLoginRequiredException -> AuthException.RequiresRecentLoginException(this)
    is FirebaseTooManyRequestsException -> AuthException.TooManyRequestsException(this)
    is FirebaseNetworkException -> AuthException.NetworkException(this)
    else -> AuthException.UnknownAuthException(this)
}