package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "order_payments")
@Serializable
data class OrderPayment(
    @PrimaryKey(autoGenerate = true) val orderPaymentId: Int? = null,
    val orderId: String,
    val providerId: String?,
)
