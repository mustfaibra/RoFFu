package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val cartId: Int? = null,
    val productId: Int? = null,
    val quantity: Int,
) {
    /** This will deals with the data from server and local */
    @Ignore var product: Product? = null
}
