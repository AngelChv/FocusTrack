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
    private val listRepository: UserListsRepository,
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
                val list = listRepository.getListById(userId, listId)
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
}