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
import androidx.compose.ui.tooling.preview.Preview
import com.mlab.nav.navigation.Screens

@Composable
fun HomeScreen(
    onNavigateToUserDetails: (Screens.Home.Details) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red.copy(.02f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Home")
        Button(onClick = {
            onNavigateToUserDetails.invoke(Screens.Home.Details("69","edit"))
        }) {
            Text("Go to User details")
        }
    }
}

@Preview
@Composable
private fun Main() {
    HomeScreen({},Modifier)
}