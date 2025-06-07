package io.github.angelchv.focustrack.domain.model

import com.google.firebase.auth.FirebaseUser

data class User(private val uid: String, private val email: String, private val name: String)

fun FirebaseUser.toDomainUser(): User {
    return User(
        uid = uid,
        email = email ?: "",
        name = displayName ?: ""
    )
}