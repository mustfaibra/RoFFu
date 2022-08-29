package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "reviews")
@Serializable
data class Review(
    @PrimaryKey(autoGenerate = true) val reviewId: Int? = null,
    val productId: Int,
    val userId: Int? = null,
    val userName: String,
    val profile: String,
    val rate: Double,
    val review: String,
)
