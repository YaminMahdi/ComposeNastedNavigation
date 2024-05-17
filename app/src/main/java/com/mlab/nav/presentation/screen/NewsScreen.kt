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
fun NewsScreen(
    onNavigateToUserDetails: (Screens.News.Details) -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue.copy(.05f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("News")
        Button(onClick = {
            onNavigateToUserDetails.invoke(Screens.News.Details("69-69"))
        }) {
            Text("Go to News details")
        }
    }
}