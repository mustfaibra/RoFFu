package com.mustfaibra.shoesstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.mustfaibra.shoesstore.providers.LocalLanguage
import com.mustfaibra.shoesstore.providers.LocalNavHost
import com.mustfaibra.shoesstore.screens.holder.HolderScreen
import com.mustfaibra.shoesstore.sealed.Language
import com.mustfaibra.shoesstore.ui.theme.StoreTheme
import com.mustfaibra.shoesstore.utils.LocalScreenSize
import com.mustfaibra.shoesstore.utils.LocaleHelper
import com.mustfaibra.shoesstore.utils.getScreenSize
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

            val mainViewModel: MainViewModel = viewModel(this)
            val navController = rememberNavController()

            /** App language handling */
            val currentLanguageAsState = mainViewModel.currentLanguage.collectAsState("ar")
            val currentLanguage by remember { currentLanguageAsState }

            /** Changing app's locale dynamically */
            LocaleHelper.applyLanguage(this@MainActivity, currentLanguage)
            val showRTL = currentLanguage == Language.Arabic.code


            /** Getting screen size */
            val size = LocalContext.current.getScreenSize()

            StoreTheme {
                CompositionLocalProvider(
                    LocalLayoutDirection provides if (showRTL) LayoutDirection.Rtl else LayoutDirection.Ltr,
                    LocalLanguage provides currentLanguage,
                    LocalScreenSize provides size,
                    LocalMainViewModel provides mainViewModel,
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