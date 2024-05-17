package com.mlab.nav.navigation.route

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mlab.nav.navigation.Screens
import com.mlab.nav.presentation.screen.UserDetailsScreen

fun NavGraphBuilder.userDetailsRoute(modifier: Modifier) {
    composable<Screens.Home.Details> {
        UserDetailsScreen(it.toRoute(),modifier)
    }
}
