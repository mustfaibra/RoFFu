package com.mustfaibra.roffu.screens.home


import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.roffu.models.Advertisement
import com.mustfaibra.roffu.models.Manufacturer
import com.mustfaibra.roffu.repositories.BrandsRepository
import com.mustfaibra.roffu.sealed.DataResponse
import com.mustfaibra.roffu.sealed.Error
import com.mustfaibra.roffu.sealed.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val brandsRepository: BrandsRepository,
) : ViewModel() {
    val searchQuery = mutableStateOf("")

    val homeAdvertisementsUiState = mutableStateOf<UiState>(UiState.Success)
    val advertisements: MutableList<Advertisement> = mutableStateListOf()

    val brandsUiState = mutableStateOf<UiState>(UiState.Loading)
    val brands: MutableList<Manufacturer> = mutableStateListOf()

    val currentSelectedBrandIndex = mutableStateOf(0)

    fun updateCurrentSelectedBrandId(index: Int) {
        currentSelectedBrandIndex.value = index
    }

    fun updateSearchInputValue(value: String) {
        this.searchQuery.value = value
    }

    fun getHomeAdvertisements() {
        if (advertisements.isNotEmpty()) return

        /** start loading */
        homeAdvertisementsUiState.value = UiState.Loading
        viewModelScope.launch {
            brandsRepository.getBrandsAdvertisements().let {
                when (it) {
                    is DataResponse.Success -> {
                        /** Got a response from the server successfully */
                        homeAdvertisementsUiState.value = UiState.Success
                        it.data?.let { responseAds ->
                            advertisements.addAll(responseAds)
                        }
                    }
                    is DataResponse.Error -> {
                        /** An error happened when fetching data from the server */
                        homeAdvertisementsUiState.value =
                            UiState.Error(error = it.error ?: Error.Network)
                    }
                }
            }
        }
    }

    fun getBrandsWithProducts() {
        if (brands.isNotEmpty()) return

        /** start loading */
        brandsUiState.value = UiState.Loading
        viewModelScope.launch {
            brandsRepository.getBrandsWithProducts().let {
                when (it) {
                    is DataResponse.Success -> {
                        /** Got a response from the server successfully */
                        brandsUiState.value = UiState.Success
                        it.data?.let { responseBrands ->
                            brands.addAll(responseBrands)
                        }
                    }
                    is DataResponse.Error -> {
                        /** An error happened when fetching data from the server */
                        brandsUiState.value = UiState.Error(error = it.error ?: Error.Network)
                    }
                }
            }
        }
    }
}