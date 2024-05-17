package com.mlab.nav.navigation.route

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mlab.nav.navigation.Screens
import com.mlab.nav.presentation.screen.ProfileScreen

fun NavGraphBuilder.profileRoute(
    modifier: Modifier,
) {
    composable<Screens.Profile> {
        ProfileScreen(modifier = modifier)
    }
}