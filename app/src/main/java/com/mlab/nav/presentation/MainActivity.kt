package com.mlab.nav.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.mlab.nav.navigation.MainScreens
import com.mlab.nav.navigation.SetupMainNavGraph
import com.mlab.nav.ui.theme.ComposeNavTheme

class MainActivity : ComponentActivity() {
    private var keepSplashOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { keepSplashOpened }
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            ComposeNavTheme {
                SetupMainNavGraph(
                    navController = navController,
                    startDestination = MainScreens.Home,
                    onDataLoaded = { keepSplashOpened = false }
                )
            }

        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeNavTheme {
        Greeting("Android")
    }
}