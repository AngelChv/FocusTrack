package io.github.angelchv.focustrack.ui.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.angelchv.focustrack.data.repository.MovieRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    var uiState by mutableStateOf(SearchUiState())
        private set

    fun onQueryChange(newQuery: String) {
        uiState = uiState.copy(query = newQuery)
        performSearch()
    }

    fun setSearching(active: Boolean) {
        uiState = uiState.copy(isSearching = active)
    }

    fun setExpanded(active: Boolean) {
        uiState = uiState.copy(isExpanded = active)
    }

    fun performSearch() {
        val query = uiState.query.trim()
        if (query.isEmpty()) return

        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val results = movieRepository.searchMovies(query)
                uiState = uiState.copy(isLoading = false, results = results)
            } catch (e: Exception) {
                Log.e("SearchViewModel", "Search failed", e)
                uiState = uiState.copy(isLoading = false)
            }
        }
    }
}