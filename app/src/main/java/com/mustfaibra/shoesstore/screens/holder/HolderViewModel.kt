package com.mustfaibra.shoesstore.screens.holder


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.shoesstore.repositories.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class HolderViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : ViewModel() {

    val productsOnCartIds: MutableList<Int> = mutableStateListOf()

    fun getCartItems(){
        viewModelScope.launch {
            productsRepository.getCartProductsIdsFlow().distinctUntilChanged().collect{
                if(productsOnCartIds.isNotEmpty()) productsOnCartIds.clear()
                productsOnCartIds.addAll(it)
            }
        }
    }

    fun updateCart(productId: Int, currentlyOnCart: Boolean) {
        viewModelScope.launch {
            productsRepository.updateCartState(productId = productId,
                alreadyOnCart = currentlyOnCart)
        }
    }
}