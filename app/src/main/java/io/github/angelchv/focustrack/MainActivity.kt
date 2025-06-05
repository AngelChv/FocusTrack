package io.github.angelchv.focustrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import io.github.angelchv.focustrack.core.navigation.AppNavHost
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocusTrackTheme {
                AppNavHost()
            }
        }
    }
}