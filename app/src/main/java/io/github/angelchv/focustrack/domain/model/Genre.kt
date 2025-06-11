package io.github.angelchv.focustrack.domain.model

data class Genre(
    val id: Int,
    val name: String,
) {
    companion object {
        val example = Genre(
            id = 1,
            name = "Example Genre",
        )
    }
}