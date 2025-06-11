package io.github.angelchv.focustrack.data.repository

import io.github.angelchv.focustrack.data.remote.movie.MovieService
import io.github.angelchv.focustrack.domain.model.Movie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieService: MovieService,
) {
    suspend fun getTrendingMovies(): List<Movie> {
        return movieService.getTrendingMovies()
    }

    suspend fun getPopularMovies(): List<Movie> {
        return movieService.getPopularMovies()
    }

    suspend fun getTopRatedMovies(): List<Movie> {
        return movieService.getTopRatedMovies()
    }

    suspend fun getNowPlayingMovies(): List<Movie> {
        return movieService.getNowPlayingMovies()
    }

    suspend fun getUpcomingMovies(): List<Movie> {
        return movieService.getUpcomingMovies()
    }
}