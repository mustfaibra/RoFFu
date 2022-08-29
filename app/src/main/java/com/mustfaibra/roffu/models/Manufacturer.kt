package com.mustfaibra.roffu.models

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Manufacturer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @DrawableRes val icon: Int,
) {
    @Ignore
    val products = mutableListOf<Product>()
}
