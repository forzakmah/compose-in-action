package com.forzakmah.composeinaction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.forzakmah.composeinaction.screen.chat.MessageScreen
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
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            Routing.mainScreen
        ) {
            MessageScreen(
                onBackClick = navController::popBackStack
            )
        }
    }
}

/**
 * Navigation routes
 */
object Routing {
    const val mainScreen = "mainScreen"
}