package com.mlab.nav.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mlab.nav.navigation.Screens

@Composable
fun NewsDetailsScreen(details: Screens.News.Details, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(details.id,modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    }
}
