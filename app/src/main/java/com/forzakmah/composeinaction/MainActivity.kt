package com.forzakmah.composeinaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.forzakmah.composeinaction.ui.theme.ComposeInActionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ComposeInActionTheme {
                MainScreen()
            }
        }
    }
}

/**
 * Main Composable
 */
@Composable
fun MainScreen() {
    Scaffold { paddingValues ->
        NavApp(modifier = Modifier.padding(paddingValues))
    }
}

/**
 * Basic graph navigation
 * @param modifier [Modifier]
 * @param navController [NavHostController]
 * @param startDestination [String]
 */
@Composable
fun NavApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Routing.mainScreen
) {
    NavHost(
        modifier = modifier.statusBarsPadding(),
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Routing.mainScreen) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Hello Android...")
            }
        }
    }
}

/**
 * Navigation routes
 */
object Routing {
    const val mainScreen = "mainScreen"
}