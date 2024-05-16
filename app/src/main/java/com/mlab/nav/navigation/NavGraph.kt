package com.mlab.nav.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute

@Composable
fun SetupMainNavGraph(
    startDestination: Any,
    navController: NavHostController,
    onDataLoaded: () -> Unit
) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ){
//        addMainNavGraph(navController)
        mainRoute(onDataLoaded)
        newsRoute(onNavigateToUserDetails ={
            navController.navigate(it)
        })
        userDetailsRoute()
        newsDetailsRoute()
    }
}
@Composable
fun SetupBottomNavGraph(
    startDestination: Any,
    navController: NavHostController,
    navControllerBottomBar: NavHostController,
    onDataLoaded: () -> Unit
) {
    LaunchedEffect(Unit) {
        onDataLoaded()
    }
    NavHost(
        startDestination = startDestination,
        navController = navControllerBottomBar
    ){
//        addMainNavGraph(navController)

        homeRoute(
            onNavigateToUserDetails = {
                navController.navigate(it)
            },
        )
    }
}
fun NavGraphBuilder.addMainNavGraph(navController: NavController) {
    navigation<MainScreens>(startDestination = MainScreens.Home) {
        composable<MainScreens.Home> {

        }
    }
}

fun NavGraphBuilder.mainRoute(
    onDataLoaded: () -> Unit
) {
    composable<MainScreens> {
        MainScreen()
    }
}

@Composable
fun MainScreen() {

}

fun NavGraphBuilder.homeRoute(
    onNavigateToUserDetails: (InnerScreens.UserDetails) -> Unit,
) {
    composable<MainScreens.Home> {
        HomeScreen(onNavigateToUserDetails)
    }
}

fun NavGraphBuilder.newsRoute(
    onNavigateToUserDetails: (InnerScreens.NewsDetails) -> Unit,
) {
    composable<MainScreens.News> {
        NewsScreen(onNavigateToUserDetails)
    }
}

fun NavGraphBuilder.userDetailsRoute(
) {
    composable<InnerScreens.UserDetails> {
        UserDetailsScreen(it.toRoute())
    }
}

fun NavGraphBuilder.newsDetailsRoute(
) {
    composable<InnerScreens.NewsDetails> {
        NewsDetailsScreen(it.toRoute())
    }
}


@Composable
fun HomeScreen(
    onNavigateToUserDetails: (InnerScreens.UserDetails) -> Unit
) {

}

@Composable
fun NewsScreen(onNavigateToUserDetails: (InnerScreens.NewsDetails) -> Unit) {
    onNavigateToUserDetails.invoke(InnerScreens.NewsDetails(""))
}

@Composable
fun UserDetailsScreen(details: InnerScreens.UserDetails) {

}

@Composable
fun NewsDetailsScreen(details: InnerScreens.NewsDetails) {

}
