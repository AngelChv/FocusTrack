package io.github.angelchv.focustrack.data.di

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel

@Composable
inline fun <reified VM : ViewModel> activityViewModel(): VM {
    val activity = LocalActivity.current as ComponentActivity
    return hiltViewModel(activity)
}
