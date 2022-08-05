package com.mustfaibra.shoesstore.screens.splash

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mustfaibra.shoesstore.sealed.UiState
import com.mustfaibra.shoesstore.utils.APP_LAUNCHED
import com.mustfaibra.shoesstore.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)

    /** Is the app launched before or not? */
    val isAppLaunchedBefore = context.dataStore.data.map {
        it[APP_LAUNCHED] ?: false
    }
}