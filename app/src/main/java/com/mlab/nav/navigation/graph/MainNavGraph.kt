package com.mlab.nav.navigation.graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mlab.nav.navigation.Screens
import com.mlab.nav.navigation.component.TopBar
import com.mlab.nav.navigation.route
import com.mlab.nav.navigation.route.mainRoute
import com.mlab.nav.navigation.route.newsDetailsRoute
import com.mlab.nav.navigation.route.userDetailsRoute
import com.mlab.nav.presentation.UserViewModel
import kotlinx.coroutines.flow.mapNotNull

@Composable
fun SetupMainNavGraph(
    startDestination: Any,
    navController: NavHostController,
    onDataLoaded: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<UserViewModel>(context as ViewModelStoreOwner)
    val backStack by navController.currentBackStackEntryFlow.mapNotNull { it.destination.route }
        .collectAsState(Screens.serializer().route)
    val currentBottomNavDestination by viewModel.currentBottomNavDestination.collectAsStateWithLifecycle()
    val isOnMainScreen = backStack == Screens.serializer().route
    var mainModifier : Modifier
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                isOnMainScreen = isOnMainScreen,
                title =
                if (isOnMainScreen)
                    currentBottomNavDestination
                else
                    backStack
                        .split('.').last()
                        .split('/').first(),
                onBackPress = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        mainModifier = Modifier.padding(top = it.calculateTopPadding())
        NavHost(
            startDestination = startDestination,
            navController = navController
        ) {
            mainRoute(
                onDataLoaded = onDataLoaded,
                modifier = mainModifier,
                navController = navController
            )
            userDetailsRoute(
                modifier = mainModifier
            )
            newsDetailsRoute(
                modifier = mainModifier
            )
        }
    }

}