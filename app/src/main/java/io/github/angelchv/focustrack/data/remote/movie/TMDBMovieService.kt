package io.github.angelchv.focustrack.data.remote.movie

import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi
import io.github.angelchv.focustrack.domain.model.Movie
import io.github.angelchv.focustrack.domain.model.MovieDetail
import javax.inject.Inject

class TMDBMovieService @Inject constructor(
    private val api: TMDBApi,
) : MovieService {
    override suspend fun getTrendingMovies(): List<Movie> {
        return api.getTrendingMovies().results.map { it.toDomainMovie() }
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return api.getPopularMovies().results.map { it.toDomainMovie() }
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return api.getTopRatedMovies().results.map { it.toDomainMovie() }
    }

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return api.getNowPlayingMovies().results.map { it.toDomainMovie() }
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        return api.getUpcomingMovies().results.map { it.toDomainMovie() }
    }

    override suspend fun getMovieDetailsById(movieId: Int): MovieDetail {
        return api.getMovieDetailsById(movieId).toDomain()
    }
}