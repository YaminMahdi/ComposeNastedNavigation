package com.mlab.nav.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mlab.nav.navigation.Screens

@Composable
fun ProfileScreen(modifier: Modifier = Modifier){
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Yellow.copy(.05f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text("Profile")
        }
    }
}