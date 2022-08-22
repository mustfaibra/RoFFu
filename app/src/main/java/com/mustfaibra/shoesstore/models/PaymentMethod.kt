package com.mustfaibra.shoesstore.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class PaymentMethod(
    val id: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val account: String? = null,
)
