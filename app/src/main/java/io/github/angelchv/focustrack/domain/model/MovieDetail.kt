package io.github.angelchv.focustrack.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val adult: Boolean,
    val originalLanguage: String,
    val genres: List<String>,
    val releaseDate: String?,
    val runtime: Int,
    val voteAverage: Double,
    val voteCount: Int,
    val tagline: String,
    val imdbId: String?,
    val homepage: String?,
    val productionCompanies: List<String>,
    val spokenLanguages: List<String>,
    val status: String,
    val budget: Int,
    val revenue: Int
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            overview = overview,
            posterPath = posterPath,
            backdropPath = backdropPath,
            adult = adult,
            originalLanguage = originalLanguage,
            genreIds = emptyList(),
            popularity = 0.0, // Todo: change model to nullable
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }

    companion object {
        val example = MovieDetail(
            id = 123,
            title = "Malditos bastardos",
            originalTitle = "Inglourious Basterds",
            overview = "La infame y salvaje historia de una desenfrenada venganza.",
            posterPath = "https://image.tmdb.org/t/p/w500/poster_example.jpg",
            backdropPath = "https://image.tmdb.org/t/p/original/backdrop_example.jpg",
            adult = false,
            originalLanguage = "en",
            genres = listOf("Drama", "Suspense", "Bélica", "Comedia", "Terror"),
            releaseDate = "2009-08-21",
            runtime = 146,
            voteAverage = 8.3,
            voteCount = 8500,
            tagline = "¿Cuál es tu vibra?",
            imdbId = "tt0361748",
            homepage = "https://www.examplemoviehomepage.com",
            productionCompanies = listOf("A Band Apart", "Universal Pictures"),
            spokenLanguages = listOf("English", "German", "French"),
            status = "Released",
            budget = 70000000,
            revenue = 321000000
        )
    }
}