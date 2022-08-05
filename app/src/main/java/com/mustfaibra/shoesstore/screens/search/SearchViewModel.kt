package com.mustfaibra.shoesstore.screens.search


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mustfaibra.shoesstore.sealed.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)

}