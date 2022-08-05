package com.mustfaibra.shoesstore.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.shoesstore.providers.LocalNavHost
import com.mustfaibra.shoesstore.sealed.Screen
import com.mustfaibra.shoesstore.ui.theme.Dimension
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(splashViewModel: SplashViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(MaterialTheme.colors.primary, MaterialTheme.colors.primaryVariant)
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val controller = LocalNavHost.current
        val isAppLaunchedBefore by splashViewModel.isAppLaunchedBefore.collectAsState(initial = false)
        LaunchedEffect(key1 = Unit) {
            delay(3000)
            if (isAppLaunchedBefore) {
                /** Launched before, we should go to home now */
                controller.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            } else {
                /** Not launched before so we should navigate to Onboard screen */
                controller.navigate(Screen.Onboard.route) {
                    popUpTo(Screen.Splash.route) {
                        inclusive = true
                    }
                }
            }
        }

        Image(
            modifier = Modifier.size(Dimension.xlIcon),
            imageVector = Icons.Rounded.Home,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onPrimary)
        )

    }
}