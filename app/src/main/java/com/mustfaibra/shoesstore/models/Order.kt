package com.mustfaibra.shoesstore.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mustfaibra.shoesstore.models.OrderItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "orders")
@Serializable
data class Order(
    @PrimaryKey val orderId: Int,
    val userId: Int,
    val total: Double,
    val createdAt: String,
    val modifiedAt: String,
    @SerialName("delivery") val needDelivery: Boolean = true,
    val isDelivered: Boolean = false,
    val payment: String,
){
    @Ignore var orderItems: List<OrderItem>? = null
    @Ignore var orderPayment: OrderPayment? = null
}
