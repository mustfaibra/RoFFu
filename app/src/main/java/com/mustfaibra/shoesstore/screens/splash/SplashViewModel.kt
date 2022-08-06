package com.mustfaibra.shoesstore.screens.splash

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.shoesstore.models.User
import com.mustfaibra.shoesstore.repositories.UserRepository
import com.mustfaibra.shoesstore.sealed.UiState
import com.mustfaibra.shoesstore.utils.APP_LAUNCHED
import com.mustfaibra.shoesstore.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepository: UserRepository,
) : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)

    /** Is the app launched before or not? */
    val isAppLaunchedBefore = context.dataStore.data.map {
        it[APP_LAUNCHED] ?: false
    }

    val user = mutableStateOf<User?>(null)
    val isUserChecked = mutableStateOf(false)

    init {
        viewModelScope.launch {
            userRepository.getLoggedUser().let {
                if (it != null) {
                    user.value = it
                }
            }
            isUserChecked.value = true
        }
    }
}