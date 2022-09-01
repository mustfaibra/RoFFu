package com.mustfaibra.roffu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.mustfaibra.roffu.providers.LocalNavHost
import com.mustfaibra.roffu.screens.holder.HolderScreen
import com.mustfaibra.roffu.ui.theme.StoreTheme
import com.mustfaibra.roffu.utils.LocalScreenSize
import com.mustfaibra.roffu.utils.getScreenSize
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            /** The status bar color which is dynamic */
            val defaultStatusBarColor = MaterialTheme.colors.background.toArgb()
            var statusBarColor by remember { mutableStateOf(defaultStatusBarColor) }
            window.statusBarColor = statusBarColor

            /** Our navigation controller */
            val navController = rememberNavController()

            /** Getting screen size */
            val size = LocalContext.current.getScreenSize()

            StoreTheme {
                CompositionLocalProvider(
                    LocalScreenSize provides size,
                    LocalNavHost provides navController
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background) {
                        HolderScreen(
                            onStatusBarColorChange = {
                                /** Updating the color of the status bar */
                                statusBarColor = it.toArgb()
                            }
                        )
                    }
                }
            }
        }
    }
}