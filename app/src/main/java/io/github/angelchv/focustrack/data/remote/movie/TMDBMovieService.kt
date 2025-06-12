package io.github.angelchv.focustrack.data.remote.movie

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.angelchv.focustrack.data.remote.tmdb.TMDBApi
import io.github.angelchv.focustrack.domain.model.Movie
import io.github.angelchv.focustrack.domain.model.MovieDetail
import javax.inject.Inject

class TMDBMovieService @Inject constructor(
    private val api: TMDBApi,
    @ApplicationContext private val context: Context,
) : MovieService {
    override suspend fun getTrendingMovies(): List<Movie> {
        val language = TMDBApi.getTmdbLanguageTag(context)
        return api.getTrendingMovies(language = language).results.map { it.toDomainMovie() }
    }

    override suspend fun getPopularMovies(): List<Movie> {
        val language = TMDBApi.getTmdbLanguageTag(context)
        return api.getPopularMovies(language = language).results.map { it.toDomainMovie() }
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        val language = TMDBApi.getTmdbLanguageTag(context)
        return api.getTopRatedMovies(language = language).results.map { it.toDomainMovie() }
    }

    override suspend fun getNowPlayingMovies(): List<Movie> {
        val language = TMDBApi.getTmdbLanguageTag(context)
        return api.getNowPlayingMovies(language = language).results.map { it.toDomainMovie() }
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        val language = TMDBApi.getTmdbLanguageTag(context)
        return api.getUpcomingMovies(language = language).results.map { it.toDomainMovie() }
    }

    override suspend fun getMovieDetailsById(movieId: Int): MovieDetail {
        val language = TMDBApi.getTmdbLanguageTag(context)
        return api.getMovieDetailsById(movieId, language = language).toDomain()
    }
}