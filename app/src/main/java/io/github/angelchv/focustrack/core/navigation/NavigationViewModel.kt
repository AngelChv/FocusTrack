package io.github.angelchv.focustrack.core.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
    private val _currentFlow = MutableStateFlow<Route>(Route.Splash)
    val currentFlow: StateFlow<Route> = _currentFlow

    fun setCurrentFlow(flow: Route) {
        _currentFlow.value = flow
    }
}