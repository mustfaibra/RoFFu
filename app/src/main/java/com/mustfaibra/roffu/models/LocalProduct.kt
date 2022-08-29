package com.mustfaibra.roffu.models

import androidx.room.Embedded
import androidx.room.Relation

class LocalProduct(
    @Embedded
    val product: Product,
    @Relation(parentColumn = "manufacturerId", entityColumn = "id")
    val manufacturer: Manufacturer,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
    )
    val copies: List<ProductColor>?,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
    )
    val sizes: List<ProductSize>?,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
    )
    val reviews: List<Review>?,

    )
