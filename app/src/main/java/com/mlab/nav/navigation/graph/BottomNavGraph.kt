package com.mlab.nav.navigation.graph

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mlab.nav.navigation.MainScreenType
import com.mlab.nav.navigation.Screens
import com.mlab.nav.navigation.component.BottomBar
import com.mlab.nav.navigation.route
import com.mlab.nav.navigation.route.homeRoute
import com.mlab.nav.navigation.route.newsRoute
import com.mlab.nav.navigation.route.profileRoute
import kotlinx.coroutines.flow.mapNotNull

@Composable
fun SetupBottomNavGraph(
    startDestination: Any,
    currentDestination: String,
    onDestinationChanged: (MainScreenType) -> Unit,
    navController: NavHostController,
    navControllerBottomBar: NavHostController,
    onDataLoaded: () -> Unit,
    modifier: Modifier,
) {
    LaunchedEffect(Unit) {
        onDataLoaded()
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                currentDestination = currentDestination,
                onDestinationChanged = onDestinationChanged
            )
        },
        contentWindowInsets = WindowInsets.navigationBars
    ){ padding->
        val paddingModifier = Modifier.padding(padding)
        NavHost(
            startDestination = startDestination,
            navController = navControllerBottomBar
        ) {
            homeRoute(
                onNavigateToUserDetails = {
                    navController.navigate(it)
                },
                modifier = paddingModifier
            )
            newsRoute(
                onNavigateToUserDetails = {
                    navController.navigate(it)
                },
                modifier = paddingModifier
            )
            profileRoute(
                modifier = paddingModifier
            )
        }
    }
}