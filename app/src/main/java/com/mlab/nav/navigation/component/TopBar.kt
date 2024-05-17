package com.mlab.nav.navigation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar(
    isOnMainScreen: Boolean,
    title: String,
    onBackPress: () -> Unit,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            Row {
                AnimatedVisibility(visible = !isOnMainScreen) {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    )
}