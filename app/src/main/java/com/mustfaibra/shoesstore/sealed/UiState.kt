package com.mustfaibra.shoesstore.sealed

import com.mustfaibra.shoesstore.sealed.Error as ErrorBody

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    class Error(val error: ErrorBody) : UiState()
}
