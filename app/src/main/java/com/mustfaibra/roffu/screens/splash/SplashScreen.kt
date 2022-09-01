package com.mustfaibra.roffu.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.roffu.R
import com.mustfaibra.roffu.sealed.Screen
import com.skydoves.whatif.whatIfNotNull
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onSplashFinished: (nextDestination: Screen) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val isAppLaunchedBefore by splashViewModel.isAppLaunchedBefore
            .collectAsState(initial = false)
        val loggedUserId by splashViewModel.loggedUserId
            .collectAsState(initial = null)

        LaunchedEffect(key1 = Unit) {
            delay(3000)
            if (isAppLaunchedBefore) {
                loggedUserId.whatIfNotNull(
                    whatIf = {
                        splashViewModel.checkLoggedUser(
                            userId = it,
                            onCheckFinish = {
                                /** Launched before and user checked, we should go to home now */
                                onSplashFinished(Screen.Home)
                            }
                        )
                    },
                    whatIfNot = {
                        /** Launched before, we should go to home now */
                        onSplashFinished(Screen.Home)
                    }
                )

            } else {
                /** Not launched before so we should navigate to Onboard screen */
                onSplashFinished(Screen.Onboard)
            }
        }
        val appName = stringResource(id = R.string.app_name)
        Text(
            /** Manipulate app's name with a different text styles */
            text = buildAnnotatedString {
                append(appName.take(2))
                withStyle(
                    style = MaterialTheme.typography.h1
                        .copy(
                            fontSize = 64.sp,
                            color = MaterialTheme.colors.primary,
                            fontFamily = FontFamily.Cursive,
                        ).toSpanStyle(),
                ){
                    append(appName.slice(2..3))
                }
                append(appName.takeLast(1))
            },
            style = MaterialTheme.typography.h1
                .copy(
                    fontSize = 64.sp,
                    color = MaterialTheme.colors.secondary,
                    fontFamily = FontFamily.Cursive,
                ),
        )
    }
}