package io.github.angelchv.focustrack.data.remote.movie

import io.github.angelchv.focustrack.domain.model.Movie

interface MovieService {
    suspend fun getTrendingMovies(): List<Movie>
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
}