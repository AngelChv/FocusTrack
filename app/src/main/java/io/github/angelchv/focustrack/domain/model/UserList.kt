package io.github.angelchv.focustrack.domain.model

data class UserList(
    val id: String = "",
    val name: String = "",
    val movieIds: List<Int> = emptyList()
)
