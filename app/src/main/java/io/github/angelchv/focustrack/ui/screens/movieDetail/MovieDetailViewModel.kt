package io.github.angelchv.focustrack.ui.screens.movieDetail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.data.repository.AuthRepository
import io.github.angelchv.focustrack.data.repository.MovieRepository
import io.github.angelchv.focustrack.data.repository.UserListsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val authRepository: AuthRepository,
    private val listRepository: UserListsRepository,
) : ViewModel() {
    var uiState by mutableStateOf(MovieDetailUiState())
        private set // Only the ViewModel can update the state

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val movie = movieRepository.getMovieDetailsById(movieId)
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

    fun addMovieToList(listId: String) {
        val movieId = uiState.movieDetail?.id ?: return
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val userId = authRepository.getCurrentUser()?.uid
                if (userId == null) return@launch
                // Todo: Check if movie is added correctly
                listRepository.addMovieToList(userId, listId, movieId)
                uiState = uiState.copy(isLoading = false)
            } catch (e: HttpException) {
                Log.e("MovieDetailViewModel", "Error adding the movie to the list", e)
                uiState = uiState.copy(isLoading = false)
            } catch (e: Exception) {
                Log.e("MovieDetailViewModel", "Error adding the movie to the list", e)
                uiState = uiState.copy(isLoading = false)
            }
        }
    }
}