package com.mustfaibra.shoesstore.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "order_payments")
@Serializable
data class OrderPayment(
    @PrimaryKey(autoGenerate = true) val orderPaymentId: Int? = null,
    val orderId: Int,
    val total: Double,
    val provider: String,
    val createdAt: String,
    val modifiedAt: String,
)
