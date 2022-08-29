package com.mustfaibra.roffu.screens.cart


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.roffu.models.CartItem
import com.mustfaibra.roffu.repositories.ProductsRepository
import com.mustfaibra.roffu.utils.getDiscountedValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductsRepository,
) : ViewModel() {

    val totalPrice = mutableStateOf(0.0)
    val isSyncingCart = mutableStateOf(false)
    private val _cartOptionsMenuExpanded = mutableStateOf(false)
    val cartOptionsMenuExpanded: State<Boolean> = _cartOptionsMenuExpanded

    fun updateCart(items: List<CartItem>){
        totalPrice.value = 0.0
        items.forEach { cartItem ->
            /** Now should update the totalPrice */
            totalPrice.value += cartItem.product?.price?.times(cartItem.quantity)
                ?.getDiscountedValue(cartItem.product?.discount ?: 0) ?: 0.0
        }
    }

    fun removeCartItem(productId: Int) {
        /** remove item from cart , we sent it the current state to invert it */
        viewModelScope.launch {
            productRepository.updateCartState(productId = productId, alreadyOnCart = true)
        }
    }

    fun updateQuantity(productId: Int, quantity: Int) {
        viewModelScope.launch {
            productRepository.updateCartItemQuantity(id = productId, quantity = quantity)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            productRepository.clearCart()
        }
    }

    fun syncCartItems(
        onSyncSuccess: () -> Unit,
        onSyncFailed: (reason: Int) -> Unit,
    ) {
        isSyncingCart.value = true
        viewModelScope.launch {
            delay(3000)
            isSyncingCart.value = false
            onSyncSuccess()
        }
    }

    fun toggleOptionsMenuExpandState() {
        _cartOptionsMenuExpanded.value = !_cartOptionsMenuExpanded.value
    }
}