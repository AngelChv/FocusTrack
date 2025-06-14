package io.github.angelchv.focustrack

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.angelchv.focustrack.core.auth.SessionManager
import io.github.angelchv.focustrack.core.navigation.AuthNavHost
import io.github.angelchv.focustrack.core.navigation.HomeNavHost
import io.github.angelchv.focustrack.core.navigation.ListNavHost
import io.github.angelchv.focustrack.core.navigation.NavigationViewModel
import io.github.angelchv.focustrack.core.navigation.ProfileNavHost
import io.github.angelchv.focustrack.core.navigation.Route
import io.github.angelchv.focustrack.core.navigation.SearchNavHost
import io.github.angelchv.focustrack.data.remote.auth.AuthService
import io.github.angelchv.focustrack.ui.components.FocusTrackScaffold
import io.github.angelchv.focustrack.ui.theme.FocusTrackTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var authService: AuthService
    @Inject lateinit var sessionManager: SessionManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: NavigationViewModel = hiltViewModel()
            val currentFlow by viewModel.currentFlow.collectAsState<Route>()

            LaunchedEffect(Unit) {
                authService.setAuthStateListener { user ->
                    if (user != null) {
                        sessionManager.setUser(user)
                    } else {
                        sessionManager.clearSession()
                        if (viewModel.currentFlow != Route.Splash) {
                            viewModel.setCurrentFlow(Route.Splash)
                        }
                    }
                }
            }

            val context = LocalContext.current
            val authNavController = rememberNavController()
            val homeNavController = rememberNavController()
            val searchNavController = rememberNavController()
            val listsNavController = rememberNavController()
            val profileNavController = rememberNavController()


            val navController = when (currentFlow) {
                Route.Home -> homeNavController
                Route.Search -> searchNavController
                Route.Lists -> listsNavController
                Route.Profile -> profileNavController
                else -> authNavController
            }

            val currentBackStack by navController.currentBackStackEntryAsState()
            val currentRouteString = currentBackStack?.destination?.route
            val currentRoute = Route.fromFullRouteString(currentRouteString)

            BackHandler(enabled = true) {
                if (currentFlow != Route.Splash && currentFlow != Route.Home) {
                    viewModel.setCurrentFlow(Route.Home)
                } else if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                } else {
                    (context as? Activity)?.finish()
                }
            }


            FocusTrackTheme {
                FocusTrackScaffold(
                    currentFlow,
                    currentRoute,
                    onFlowSelected = { selected ->
                        if (selected == currentFlow) {
                            navController.popBackStack(
                                navController.graph.startDestinationId,
                                false
                            )
                        } else {
                            viewModel.setCurrentFlow(selected)
                        }
                    }) { scrollBehavior ->
                    when (currentFlow) {
                        Route.Splash -> AuthNavHost(
                            Modifier.align(
                                Alignment.Center
                            ), navController,
                            onNavigateToHome = {
                                viewModel.setCurrentFlow(Route.Home)
                            }
                        )

                        Route.Home -> HomeNavHost(
                            Modifier.align(Alignment.Center),
                            navController,
                            scrollBehavior,
                        )

                        Route.Search -> SearchNavHost(
                            Modifier.align(Alignment.Center),
                            navController,
                        )

                        Route.Lists -> ListNavHost(
                            navController = navController,
                            scrollBehavior = scrollBehavior
                        )

                        Route.Profile -> ProfileNavHost(
                            navController = navController,
                            onLogout = {
                                viewModel.setCurrentFlow(Route.Splash)
                            }
                        )

                        else -> {}
                    }
                }
            }
        }
    }
}