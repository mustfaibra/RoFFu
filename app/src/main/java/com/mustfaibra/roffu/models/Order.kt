package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mustfaibra.roffu.utils.getFormattedDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Entity(tableName = "orders")
@Serializable
data class Order(
    @PrimaryKey val orderId: String,
    val userId: Int,
    val total: Double,
    val createdAt: String = Date().getFormattedDate("yyyy-MM-dd HH:mm"),
    val modifiedAt: String = Date().getFormattedDate("yyyy-MM-dd HH:mm"),
    @SerialName("delivery") val needDelivery: Boolean = true,
    val isDelivered: Boolean = false,
    val locationId: Int?,
){
    @Ignore var orderItems: List<OrderItem>? = null
    @Ignore var orderPayment: OrderPayment? = null
}
