package com.mlab.nav.navigation

import kotlinx.serialization.Serializable

sealed class InnerScreens {
    @Serializable
    data class UserDetails(val id: String, val type: String)
    @Serializable
    data class NewsDetails(val id: String)
}

sealed class MainScreens {
    @Serializable
    object Home
    @Serializable
    object News
    @Serializable
    object Profile
}