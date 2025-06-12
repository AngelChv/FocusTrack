package io.github.angelchv.focustrack.domain.dto

data class CreateUserListDto(
    val name: String = "",
    val movieIds: List<Int> = emptyList()
)
