package com.mlab.nav.navigation.route

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mlab.nav.navigation.Screens
import com.mlab.nav.navigation.graph.SetupBottomNavGraph
import com.mlab.nav.navigation.route
import com.mlab.nav.presentation.UserViewModel
import kotlinx.coroutines.flow.mapNotNull

fun NavGraphBuilder.mainRoute(
    onDataLoaded: () -> Unit,
    modifier: Modifier,
    navController: NavHostController
) {
    composable<Screens> {
        val context = LocalContext.current
        val navControllerBottomBar = rememberNavController()
        val viewModel = hiltViewModel<UserViewModel>(context as ViewModelStoreOwner)

        val currentDestination by navControllerBottomBar.currentBackStackEntryFlow.mapNotNull { it.destination.route?.split(".")?.lastOrNull() }
            .collectAsStateWithLifecycle("")

        SetupBottomNavGraph(
            startDestination = Screens.Home,
            currentDestination = currentDestination,
            onDestinationChanged  = {destination->
                navControllerBottomBar.navigate(destination.route){
                    popUpTo(Screens.Home)
                    launchSingleTop = true
                }
                viewModel.changeBottomNavDestination(destination.name)
            },
            navController =navController,
            navControllerBottomBar =navControllerBottomBar,
            onDataLoaded = onDataLoaded,
            modifier=modifier
        )
    }
}