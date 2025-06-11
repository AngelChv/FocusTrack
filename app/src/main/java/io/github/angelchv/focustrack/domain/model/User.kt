package io.github.angelchv.focustrack.domain.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val uid: String,
    val email: String?,
    val name: String?,
    val photoUrl: String?,
) {
    companion object {
        val example = User("1", "angel@gmail.com", "AngelChv", null)
    }
}

fun FirebaseUser.toDomainUser(): User {
    return User(
        uid = uid,
        email = email,
        name = displayName,
        photoUrl = photoUrl?.toString()
    )
}