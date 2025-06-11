package io.github.angelchv.focustrack.data.remote.tmdb

import io.github.angelchv.focustrack.domain.dto.TMDBMoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    companion object {
        const val IMAGE_BASE_URL: String = "https://image.tmdb.org/t/p/"
        enum class ImageSize(val value: String) {
            W92("w92"),
            W154("w154"),
            W185("w185"),
            W342("w342"),
            W500("w500"),
            W780("w780"),
            ORIGINAL("original")
        }
    }

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String = "day",
        @Query("language") language: String = "en-US",
    ): TMDBMoviesResponseDto

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
    ): TMDBMoviesResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "en-US",
    ): TMDBMoviesResponseDto

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "en-US",
    ): TMDBMoviesResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "en-US",
    ): TMDBMoviesResponseDto
}