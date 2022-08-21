package com.mustfaibra.shoesstore.screens.login


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.shoesstore.repositories.UserRepository
import com.mustfaibra.shoesstore.sealed.DataResponse
import com.mustfaibra.shoesstore.sealed.Error
import com.mustfaibra.shoesstore.sealed.UiState
import com.mustfaibra.shoesstore.utils.UserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)
    val emailOrPhone = mutableStateOf<String?>(null)
    val password = mutableStateOf<String?>(null)

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