package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val image: Int,
    val price: Double,
    val description: String,
    val discount: Int = 0,
    val manufacturerId: Int,
    val basicColorName: String,
) {
    @Ignore
    var manufacturer: Manufacturer? = null
    @Ignore
    var colors: List<ProductColor>? = null
    @Ignore
    var reviews: List<Review>? = null
    @Ignore
    var sizes: List<ProductSize>? = null
}
