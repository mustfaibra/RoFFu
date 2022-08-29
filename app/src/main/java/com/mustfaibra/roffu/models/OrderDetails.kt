package com.mustfaibra.roffu.models

import androidx.room.Embedded
import androidx.room.Relation

data class OrderDetails(
    @Embedded
    val order: Order,
    @Relation(
        parentColumn = "orderId",
        entityColumn = "orderId",
        entity = OrderPayment::class,
    )
    val orderPayment: OrderPaymentDetails,
    @Relation(
        parentColumn = "locationId",
        entityColumn = "id",
    )
    val location: Location,

)
