package io.github.angelchv.focustrack.domain.dto

import com.google.gson.annotations.SerializedName
import io.github.angelchv.focustrack.domain.model.Movie

data class TMDBMoviesResponseDto(
    val page: Int,
    @SerializedName("results") val results: List<TMDBMovieDto>,
)

data class TMDBMovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    val adult: Boolean,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("genre_ids") val genreIds: List<Int>?,
    val popularity: Double,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
) {
    fun toDomainMovie(): Movie = Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        adult = adult,
        originalLanguage = originalLanguage,
        genreIds = genreIds,
        popularity = popularity,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )
}

