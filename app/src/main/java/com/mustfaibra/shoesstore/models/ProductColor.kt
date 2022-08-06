package com.mustfaibra.shoesstore.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "productCopies", primaryKeys = ["productId","colorName"])
@Serializable
data class ProductColor(
    val productId: Int,
    val colorName: String,
    val image: Int,
)
