package com.mustfaibra.roffu.screens.productdetails


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.roffu.models.Product
import com.mustfaibra.roffu.repositories.ProductsRepository
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
class ProductDetailsViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : ViewModel() {
    private val _detailsUiState = mutableStateOf<UiState>(UiState.Loading)
    val detailsUiState: State<UiState> = _detailsUiState

    private val _product = mutableStateOf<Product?>(null)
    val product: State<Product?> = _product

    private val _selectedSize = mutableStateOf(0)
    val selectedSize: State<Int> = _selectedSize

    private val _sizeScale = mutableStateOf(1f)
    val sizeScale: State<Float> = _sizeScale

    private val _selectedColor = mutableStateOf("")
    val selectedColor: State<String> = _selectedColor

    fun getProductDetails(productId: Int) {
        _detailsUiState.value = UiState.Loading
        viewModelScope.launch {
            productsRepository.getProductDetails(productId = productId).let {
                when (it) {
                    is DataResponse.Success -> {
                        /** Got a response from the server successfully */
                        _detailsUiState.value = UiState.Success
                        it.data?.let { product ->
                            _product.value = product
                            _selectedColor.value = product.basicColorName
                            _selectedSize.value = product.sizes?.maxOf { size -> size.size } ?: 0
                        }
                    }
                    is DataResponse.Error -> {
                        /** An error happened when fetching data from the server */
                        _detailsUiState.value = UiState.Error(error = it.error ?: Error.Network)
                    }
                }
            }
        }
    }

    /**
     * Update the current product's color.
     * @param color The new color name
     */
    fun updateSelectedColor(color: String) {
        _selectedColor.value = color
    }

    /**
     * Update the current product's size.
     * @param size the new selected size.
     */
    fun updateSelectedSize(size: Int) {
        /** Check when user click again on the same size ! */
        if(size == _selectedSize.value) return
        /** Update the product's image scale depending on the new size */
        _sizeScale.value = if (size < _selectedSize.value) {
            _sizeScale.value.minus(0.1f)
        } else {
            _sizeScale.value.plus(0.1f)
        }
        _selectedSize.value = size
    }
}