package com.mustfaibra.roffu.screens.holder


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.roffu.models.CartItem
import com.mustfaibra.roffu.repositories.ProductsRepository
import com.mustfaibra.roffu.utils.getStructuredCartItems
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val cartItems: MutableList<CartItem> = mutableStateListOf()
    val productsOnCartIds: MutableList<Int> = mutableStateListOf()
    val productsOnBookmarksIds: MutableList<Int> = mutableStateListOf()

    init {
        getCartItems()
        getCartItemsIds()
        getBookmarksItemsIds()
    }

    private fun getCartItems() {
        if (cartItems.isNotEmpty()) return
        viewModelScope.launch {
            productsRepository.getLocalCart().distinctUntilChanged().collect {
                it.getStructuredCartItems().let { updates ->
                    if (
                        updates.isEmpty()
                        || updates.any { item ->
                            item.cartId in cartItems.map { cartItem -> cartItem.cartId }
                        }
                    ) {
                        cartItems.clear()
                    }
                    /** There are a cart items */
                    cartItems.addAll(updates)
                }
            }
        }
    }

    private fun getCartItemsIds() {
        viewModelScope.launch {
            productsRepository.getCartProductsIdsFlow().distinctUntilChanged().collect {
                if (productsOnCartIds.isNotEmpty()) productsOnCartIds.clear()
                productsOnCartIds.addAll(it)
            }
        }
    }

    private fun getBookmarksItemsIds() {
        viewModelScope.launch {
            productsRepository.getBookmarksProductsIdsFlow().distinctUntilChanged().collect {
                if (productsOnBookmarksIds.isNotEmpty()) productsOnBookmarksIds.clear()
                productsOnBookmarksIds.addAll(it)
            }
        }
    }


    fun updateCart(productId: Int, currentlyOnCart: Boolean) {
        viewModelScope.launch {
            productsRepository.updateCartState(
                productId = productId,
                alreadyOnCart = currentlyOnCart,
            )
        }
    }

    fun updateBookmarks(productId: Int, currentlyOnBookmarks: Boolean) {
        viewModelScope.launch {
            productsRepository.updateBookmarkState(
                productId = productId,
                alreadyOnBookmark = currentlyOnBookmarks,
            )
        }
    }

}