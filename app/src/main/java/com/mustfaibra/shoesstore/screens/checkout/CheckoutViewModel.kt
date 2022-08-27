package com.mustfaibra.shoesstore.screens.checkout


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustfaibra.shoesstore.R
import com.mustfaibra.shoesstore.models.CartItem
import com.mustfaibra.shoesstore.models.Location
import com.mustfaibra.shoesstore.models.UserPaymentProviderDetails
import com.mustfaibra.shoesstore.repositories.ProductsRepository
import com.mustfaibra.shoesstore.repositories.UserRepository
import com.mustfaibra.shoesstore.sealed.Error
import com.mustfaibra.shoesstore.sealed.UiState
import com.mustfaibra.shoesstore.utils.getDiscountedValue
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _deliveryAddress = mutableStateOf<Location?>(null)
    val deliveryAddress: State<Location?> = _deliveryAddress

    private val _paymentProviders: MutableList<UserPaymentProviderDetails> = mutableStateListOf()
    val paymentProviders: List<UserPaymentProviderDetails> = _paymentProviders

    private val _selectedPaymentMethodId = mutableStateOf<String?>(null)
    val selectedPaymentMethodId: State<String?> = _selectedPaymentMethodId
    val subTotalPrice = mutableStateOf(0.0)

    private val _checkoutState = mutableStateOf<UiState>(UiState.Idle)
    val checkoutState: State<UiState> = _checkoutState

    init {
        getUserPaymentProviders()
        getUserLocations()
    }

    private fun getUserPaymentProviders() {
        viewModelScope.launch {
            userRepository.getUserPaymentProviders().let {
                _paymentProviders.addAll(it)
            }
        }
    }

    private fun getUserLocations() {
        viewModelScope.launch {
            userRepository.getUserLocations().let {
                if (it.isNotEmpty()) {
                    _deliveryAddress.value = it.first()
                }
            }
        }
    }

    fun updateSelectedPaymentMethod(id: String) {
        _selectedPaymentMethodId.value = id
    }

    fun setUserCart(cartItems: List<CartItem>) {
        subTotalPrice.value = 0.0
        cartItems.forEach { cartItem ->
            /** Now should update the sub total price */
            subTotalPrice.value += cartItem.product?.price?.times(cartItem.quantity)
                ?.getDiscountedValue(cartItem.product?.discount ?: 0) ?: 0.0
        }
    }

    fun makeTransactionPayment(
        items: List<CartItem>,
        total: Double,
        onCheckoutSuccess: () -> Unit,
        onCheckoutFailed: (message: Int) -> Unit,
    ) {
        _checkoutState.value = UiState.Idle
        _selectedPaymentMethodId.value.whatIfNotNull(
            whatIf = {
                _checkoutState.value = UiState.Loading
                viewModelScope.launch {
                    delay(5000)
                    /** Now clear the cart */
                    productsRepository.saveOrders(
                        items = items,
                        providerId = _selectedPaymentMethodId.value,
                        total = total,
                        deliveryAddressId = _deliveryAddress.value?.id,
                        onFinished = {
                            _checkoutState.value = UiState.Success
                            onCheckoutSuccess()
                        }
                    )
                }
            },
            whatIfNot = {
                _checkoutState.value = UiState.Error(error = Error.Unknown)
                onCheckoutFailed(R.string.please_select_payment)
            },
        )
    }
}