package io.github.angelchv.focustrack.ui.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import io.github.angelchv.focustrack.data.repository.MovieRepository
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        viewModelScope.launch {
            loadTrendingMovies()
            loadPopularMovies()
            loadTopRatedMovies()
            loadNowPlayingMovies()
            loadUpcomingMovies()
        }
    }

    suspend fun loadTrendingMovies() {
        try {
            uiState = uiState.copy(isLoading = true)
            val movies = movieRepository.getTrendingMovies()
            uiState = uiState.copy(isLoading = false, trendingMovies = movies)
        } catch (e: HttpException) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        }
    }

    suspend fun loadPopularMovies() {
        try {
            uiState = uiState.copy(isLoading = true)
            val movies = movieRepository.getPopularMovies()
            uiState = uiState.copy(isLoading = false, popularMovies = movies)
        } catch (e: HttpException) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        }
    }

    suspend fun loadTopRatedMovies() {
        try {
            uiState = uiState.copy(isLoading = true)
            val movies = movieRepository.getTopRatedMovies()
            uiState = uiState.copy(isLoading = false, topRatedMovies = movies)
        } catch (e: HttpException) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        }
    }

    suspend fun loadNowPlayingMovies() {
        try {
            uiState = uiState.copy(isLoading = true)
            val movies = movieRepository.getNowPlayingMovies()
            uiState = uiState.copy(isLoading = false, nowPlayingMovies = movies)
        } catch (e: HttpException) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        }
    }

    suspend fun loadUpcomingMovies() {
        try {
            uiState = uiState.copy(isLoading = true)
            val movies = movieRepository.getUpcomingMovies()
            uiState = uiState.copy(isLoading = false, upcomingMovies = movies)
        } catch (e: HttpException) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        } catch (e: Exception) {
            Log.e("HomeViewModel", "Error loading movies", e)
            uiState = uiState.copy(isLoading = false)
        }
    }
}