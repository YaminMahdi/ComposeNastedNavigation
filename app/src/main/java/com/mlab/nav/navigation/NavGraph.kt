package com.mlab.nav.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation


fun NavGraphBuilder.addMainNavGraph(navController: NavController) {
    navigation<Screens>(startDestination = Screens.Home) {
        composable<Screens.Home> {

        }
    }
}