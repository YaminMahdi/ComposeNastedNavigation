package com.mlab.nav.navigation.route

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mlab.nav.navigation.Screens
import com.mlab.nav.presentation.screen.HomeScreen

fun NavGraphBuilder.homeRoute(
    onNavigateToUserDetails: (Screens.Home.Details) -> Unit,
    modifier: Modifier,
) {
    composable<Screens.Home> {
        HomeScreen(
            onNavigateToUserDetails= onNavigateToUserDetails,
            modifier = modifier
        )
    }
}