package io.github.angelchv.focustrack.domain.dto

import com.google.gson.annotations.SerializedName
import io.github.angelchv.focustrack.domain.model.MovieDetail

data class TMDBMovieDetailDto(
    val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("belongs_to_collection") val belongsToCollection: CollectionDto?,
    val budget: Int,
    val genres: List<GenreDto>?,
    val homepage: String?,
    val id: Int,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyDto>?,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountryDto>?,
    @SerializedName("release_date") val releaseDate: String?,
    val revenue: Int,
    val runtime: Int,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguageDto>?,
    val status: String?,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
) {
    data class CollectionDto(
        val id: Int,
        val name: String,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("backdrop_path") val backdropPath: String?
    )

    data class GenreDto(
        val id: Int,
        val name: String
    )

    data class ProductionCompanyDto(
        val id: Int,
        @SerializedName("logo_path") val logoPath: String?,
        val name: String,
        @SerializedName("origin_country") val originCountry: String
    )

    data class ProductionCountryDto(
        @SerializedName("iso_3166_1") val iso31661: String,
        val name: String
    )

    data class SpokenLanguageDto(
        @SerializedName("english_name") val englishName: String,
        @SerializedName("iso_639_1") val iso6391: String,
        val name: String
    )

    fun toDomain(): MovieDetail = MovieDetail(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview.orEmpty(),
        posterPath = posterPath,
        backdropPath = backdropPath,
        adult = adult,
        originalLanguage = originalLanguage,
        genres = genres?.map { it.name } ?: emptyList(),
        releaseDate = releaseDate,
        runtime = runtime,
        voteAverage = voteAverage,
        voteCount = voteCount,
        tagline = tagline.orEmpty(),
        imdbId = imdbId,
        homepage = homepage,
        productionCompanies = productionCompanies?.map { it.name } ?: emptyList(),
        spokenLanguages = spokenLanguages?.map { it.englishName } ?: emptyList(),
        status = status.orEmpty(),
        budget = budget,
        revenue = revenue
    )
}
