package io.github.angelchv.focustrack.data.remote.auth

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.github.angelchv.focustrack.core.errors.auth.AuthException
import io.github.angelchv.focustrack.core.errors.auth.toAuthException
import io.github.angelchv.focustrack.domain.model.User
import io.github.angelchv.focustrack.domain.model.toDomainUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


/**
 * Firebase implementation of [AuthService] for user authentication.
 * Uses Firebase Authentication to handle login, registration, and user management.
 */
class FirebaseAuthServiceImpl @Inject constructor() : AuthService {
    private val auth = Firebase.auth
    private var authStateListener: FirebaseAuth.AuthStateListener? = null

    /**
     * Logs in a user via Firebase Authentication.
     * Maps Firebase user to domain [User] or throws [AuthException].
     * @throws AuthException if login fails.
     */
    override suspend fun login(email: String, password: String): User? {
        try {
            return auth.signInWithEmailAndPassword(email, password).await().user?.toDomainUser()
        } catch (e: Exception) {
            throw e.toAuthException()
        }
    }

    override suspend fun loginWhitCredential(credential: AuthCredential): User? {
        try {
            return auth.signInWithCredential(credential).await().user?.toDomainUser()
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Google sign in failed", e)
            // Todo: add the new exceptions
            throw e.toAuthException()
        }
    }

    /**
     * Registers a new user via Firebase Authentication.
     * Maps Firebase user to domain [User] or throws [AuthException].
     * @throws AuthException if registration fails.
     */
    override suspend fun register(email: String, password: String): User? {
        try {
            return auth.createUserWithEmailAndPassword(email, password).await().user?.toDomainUser()
        } catch (e: Exception) {
            throw e.toAuthException()
        }
    }

    /**
     * Logs out the current user from Firebase.
     */
    override fun logout() {
        auth.signOut()
    }

    /**
     * Retrieves the current Firebase user and maps to domain [User].
     * Returns `null` if no user is signed in or on error.
     * @throws AuthException if retrieval fails.
     */
    override fun getCurrentUser(): User? {
        try {
            return auth.currentUser?.toDomainUser()
        } catch (e: Exception) {
            throw e.toAuthException()
        }
    }

    override fun setAuthStateListener(onAuthStateChanged: (User?) -> Unit) {
        if (authStateListener != null) return

        authStateListener = FirebaseAuth.AuthStateListener {
            val firebaseUser = it.currentUser
            onAuthStateChanged(firebaseUser?.toDomainUser())
        }

        if (authStateListener != null) {
            auth.addAuthStateListener(authStateListener!!)
        }
    }
}