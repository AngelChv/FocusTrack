package io.github.angelchv.focustrack.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val adult: Boolean,
    val originalLanguage: String,
    val genreIds: List<Int>?,
    val popularity: Double,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
) {
    companion object {
        val example = Movie(
            id = 1,
            title = "Example Movie",
            overview = "This is an example movie overview.",
            posterPath = "https://example.com/poster.jpg",
            backdropPath = "https://example.com/backdrop.jpg",
            adult = false,
            originalLanguage = "en",
            genreIds = listOf(1, 2, 3),
            popularity = 10.0,
            releaseDate = "2023-01-01",
            voteAverage = 8.5,
            voteCount = 1000,
        )
    }
}
