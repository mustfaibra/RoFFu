package com.mustfaibra.shoesstore.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "order_items")
@Serializable
data class OrderItem(
    @PrimaryKey val orderItemId: Int? = null,
    val orderId: Int? = null,
    val quantity: Int,
    val createdAt: String,
    val modifiedAt: String,
    val productId: Int? = null,
    val userId: Int? = null,
){
    /** This data will deals with the server from server */
    @Ignore var product: Product? = null
    @Ignore var order: Order? = null
}
