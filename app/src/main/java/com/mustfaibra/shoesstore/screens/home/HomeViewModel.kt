package com.mustfaibra.shoesstore.screens.home


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mustfaibra.shoesstore.models.Advertisement
import com.mustfaibra.shoesstore.sealed.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)
    val searchQuery = mutableStateOf("")

    val homeAdvertisementsUiState = mutableStateOf<UiState>(UiState.Success)
    val advertisements: MutableList<Advertisement> = mutableStateListOf()

    fun updateSearchInputValue(value: String) {
        this.searchQuery.value = value
    }

    fun getHomeAdvertisements() {
        if(advertisements.isNotEmpty()) return
        /** start loading */
        homeAdvertisementsUiState.value = UiState.Loading

        advertisements.addAll(listOf())
    }
}