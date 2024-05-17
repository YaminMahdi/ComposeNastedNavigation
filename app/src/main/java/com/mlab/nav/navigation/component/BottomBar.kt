package com.mlab.nav.navigation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mlab.nav.navigation.MainScreenType

@Composable
fun BottomBar(
    currentDestination: String,
    onDestinationChanged: (MainScreenType) -> Unit
) {
    NavigationBar {
        MainScreenType.entries.forEach{  destination ->
            NavigationBarItem(
                icon = { Icon(destination.icon, contentDescription = destination.name) },
                label = { Text(destination.name) },
                selected = currentDestination == destination.name,
                onClick = {
                    onDestinationChanged(destination)
                },
            )
        }
    }
}