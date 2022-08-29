package com.mustfaibra.roffu.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PaymentProvider(
    @PrimaryKey val id: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
)
