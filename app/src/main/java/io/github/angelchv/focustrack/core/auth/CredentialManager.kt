package io.github.angelchv.focustrack.core.auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.angelchv.focustrack.R
import javax.inject.Inject
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class CredentialManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val credentialManager = CredentialManager.Companion.create(context)

    suspend fun getCredential(activity: Activity): Credential? {
        val request = GetCredentialRequest(
            listOf(
                GetPasswordOption(),
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setAutoSelectEnabled(false)
                    .setServerClientId(context.getString(R.string.default_web_client_id))
                    .build()
            )
        )

        return try {
            credentialManager.getCredential(
                request = request,
                context = activity,
            ).credential
        } catch (e: Exception) {
            Log.e("CredClient", "No saved credential: ${e.message}")
            null
        }
    }

    fun getGoogleIdCredential(credential: Credential): AuthCredential {
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
        return GoogleAuthProvider.getCredential(googleIdTokenCredential.idToken, null)
    }

    // Todo: handle the exception and send a message to the user
    suspend fun savePasswordCredential(activity: Activity, email: String, password: String) {
        val request = CreatePasswordRequest(
            id = email,
            password = password,
            isAutoSelectAllowed = false
        )

        try {
            credentialManager.createCredential(activity, request)
        } catch (e: Exception) {
            Log.e("CredClient", "Save password failed: ${e.message}")
        }
    }
}