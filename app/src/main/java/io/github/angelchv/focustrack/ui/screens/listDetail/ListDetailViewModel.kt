package io.github.angelchv.focustrack.ui.screens.listDetail

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
class ListDetailViewModel @Inject constructor(
    private val listDetailRepository: UserListsRepository,
    private val authRepository: AuthRepository,
    private val movieRepository: MovieRepository,
) : ViewModel() {
    var uiState by mutableStateOf(ListDetailUiState())
        private set

    fun loadListDetail(listId: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val userId = authRepository.getCurrentUser()?.uid
                if (userId == null) return@launch
                val list = listDetailRepository.getListById(userId, listId)
                val movies = list?.movieIds?.map { movieId ->
                    movieRepository.getMovieDetailsById(movieId)
                } ?: emptyList()
                uiState = uiState.copy(isLoading = false, list = list, movies = movies)
            } catch (e: HttpException) {
                Log.e("ListDetailViewModel", "Error loading list", e)
                uiState = uiState.copy(isLoading = false)
            } catch (e: Exception) {
                Log.e("ListDetailViewModel", "Error loading list", e)
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun removeMovieFromList(movieId: Int) {
        val list = uiState.list ?: return
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val userId = authRepository.getCurrentUser()?.uid
                if (userId == null) return@launch

                // Todo: Check if movie is removed correctly
                if (listDetailRepository.removeMoveFromList(userId, list.id, movieId)) {
                    // Update state removing the film
                    val updatedMovieIds = list.movieIds - movieId
                    val updatedMovies = uiState.movies?.filterNot { it.id == movieId }

                    uiState = uiState.copy(
                        list = list.copy(movieIds = updatedMovieIds),
                        movies = updatedMovies
                    )
                }

                uiState = uiState.copy(isLoading = false)
            } catch (e: HttpException) {
                Log.e("ListDetailViewModel", "Error removing the movie to the list", e)
                uiState = uiState.copy(isLoading = false)
            } catch (e: Exception) {
                Log.e("ListDetailViewModel", "Error removing the movie to the list", e)
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun deleteCurrentList() {
        Log.d("ListDetailViewModel", "deleteCurrentList()")
        Log.d("ListDetailViewModel", "list: ${uiState.list}")
        val list = uiState.list ?: return
        deleteList(list.id)
    }

    fun deleteList(listId: String) {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val userId = authRepository.getCurrentUser()?.uid ?: return@launch
                uiState = if (listDetailRepository.deleteListById(userId, listId)) {
                    Log.d("ListDetailViewModel", "deleteCurrentList() successful")
                    uiState.copy(navigateBack = true, isLoading = false)
                } else {
                    Log.e("ListDetailViewModel", "Error deleting list")
                    uiState.copy(errorMessage = "Error deleting list", isLoading = false)
                }
            } catch (e: Exception) {
                Log.e("ListDetailViewModel", "Error deleting list", e)
                uiState = uiState.copy(errorMessage = "Error deleting list", isLoading = false)
            }
        }
    }

    fun onNavigatedBack() {
        uiState = uiState.copy(navigateBack = false)
    }
}