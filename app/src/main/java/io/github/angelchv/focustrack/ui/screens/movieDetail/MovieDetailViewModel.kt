package io.github.angelchv.focustrack.ui.screens.movieDetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.data.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    var uiState by mutableStateOf(MovieDetailUiState())
        private set // Only the ViewModel can update the state

    fun loadMovieDetail(movieId: Int) {
        Log.d("MovieDetailViewModel", "Loading movie detail for movieId: $movieId")
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val movie = movieRepository.getMovieDetailsById(movieId)
                Log.d("MovieDetailViewModel", "Loaded movie detail: $movie")
                uiState = uiState.copy(isLoading = false, movieDetail = movie)
            } catch (e: HttpException) {
                Log.e("MovieDetailViewModel", "Error loading movie", e)
                uiState = uiState.copy(isLoading = false)
            } catch (e: Exception) {
                Log.e("MovieDetailViewModel", "Error loading movie", e)
                uiState = uiState.copy(isLoading = false)
            }
        }
    }
}