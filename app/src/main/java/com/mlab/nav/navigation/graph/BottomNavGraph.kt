package com.mlab.nav.navigation.graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mlab.nav.navigation.MainScreenType
import com.mlab.nav.navigation.component.BottomBar
import com.mlab.nav.navigation.route.homeRoute
import com.mlab.nav.navigation.route.newsRoute
import com.mlab.nav.navigation.route.profileRoute

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
        }){ padding->
        val paddingModifier = Modifier.padding(bottom = padding.calculateBottomPadding())
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