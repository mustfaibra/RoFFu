package com.mustfaibra.shoesstore.screens.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustfaibra.shoesstore.components.SimpleLoadingDialog

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
    ) {
        val context = LocalContext.current
        val uiState by remember { cartViewModel.cartState }
        val totalPrice by remember { cartViewModel.totalPrice }
        val cartItems = cartViewModel.cartItems
        val isSyncingCart by remember { cartViewModel.isSyncingCart }
        if (isSyncingCart) {
            SimpleLoadingDialog(title = "Please wait while we sync your cart")
        }


    }
}