package com.mlab.nav.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.Person3
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable


@Serializable
object Screens {
    @Serializable object Home{
        @Serializable data class Details(val id: String, val type: String)
    }
    @Serializable object News{
        @Serializable data class Details(val id: String)
    }
    @Serializable object Profile
}

@OptIn(ExperimentalSerializationApi::class)
val <T> KSerializer<T>.route
    get() = descriptor.serialName

enum class MainScreenType(val icon: ImageVector, val route: Any) {
    Home(icon = Icons.Rounded.Home, route = Screens.Home),
    News(icon = Icons.Rounded.Newspaper, route = Screens.News),
    Profile(icon = Icons.Rounded.Person3, route = Screens.Profile)
}