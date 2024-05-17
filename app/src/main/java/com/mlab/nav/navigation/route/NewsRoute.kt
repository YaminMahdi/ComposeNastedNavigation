package com.mlab.nav.navigation.route

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mlab.nav.navigation.Screens
import com.mlab.nav.presentation.screen.NewsScreen

fun NavGraphBuilder.newsRoute(
    onNavigateToUserDetails: (Screens.News.Details) -> Unit,
    modifier: Modifier,
) {
    composable<Screens.News> {
        NewsScreen(onNavigateToUserDetails,modifier)
    }
}