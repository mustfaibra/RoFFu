package com.mustfaibra.roffu.screens.login


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.roffu.repositories.UserRepository
import com.mustfaibra.roffu.sealed.DataResponse
import com.mustfaibra.roffu.sealed.Error
import com.mustfaibra.roffu.sealed.UiState
import com.mustfaibra.roffu.utils.LOGGED_USER_ID
import com.mustfaibra.roffu.utils.UserPref
import com.mustfaibra.roffu.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
@SuppressLint("StaticFieldLeak")
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val context: Context,
) : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)
    val emailOrPhone = mutableStateOf<String?>("mustfaibra@gmail.com")
    val password = mutableStateOf<String?>("12344321")

    fun updateEmailOrPhone(value: String?) {
        this.emailOrPhone.value = value
    }

    fun updatePassword(value: String?) {
        this.password.value = value
    }

    fun authenticateUser(
        emailOrPhone: String,
        password: String,
        onAuthenticated: () -> Unit,
        onAuthenticationFailed: () -> Unit,
    ) {
        if (emailOrPhone.isBlank() || password.isBlank()) onAuthenticationFailed()
        else {
            uiState.value = UiState.Loading
            /** We will use the coroutine so that we don't block our dear : The UiThread */
            viewModelScope.launch {
                delay(3000)
                userRepository.signInUser(
                    email = emailOrPhone,
                    password = password,
                ).let {
                    when (it) {
                        is DataResponse.Success -> {
                            it.data?.let { user ->
                                /** Authenticated successfully */
                                uiState.value = UiState.Success
                                UserPref.updateUser(user = user)
                                /** save user id */
                                saveUserIdToPreferences(userId = user.userId)
                                onAuthenticated()
                            }
                        }
                        is DataResponse.Error -> {
                            /** An error occurred while authenticating */
                            uiState.value = UiState.Error(error = it.error ?: Error.Network)
                            onAuthenticationFailed()
                        }
                    }
                }
            }
        }
    }

    private suspend fun saveUserIdToPreferences(userId: Int) {
        context.dataStore.edit {
            it[LOGGED_USER_ID] = userId
        }
    }
}