package com.mlab.nav.navigation.route

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mlab.nav.navigation.Screens
import com.mlab.nav.presentation.screen.NewsDetailsScreen


fun NavGraphBuilder.newsDetailsRoute(modifier: Modifier) {
    composable<Screens.News.Details> {
        NewsDetailsScreen(it.toRoute(),modifier)
    }
}