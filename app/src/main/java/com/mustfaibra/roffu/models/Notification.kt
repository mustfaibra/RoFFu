package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.mustfaibra.roffu.sealed.Screen


@Entity(tableName = "notifications")
data class Notification(
    @PrimaryKey val id: Int,
    val body: String,
    val target: String,
    val targetId: Int,
    val time: String,
) {
    @Ignore val destination: Screen? = when (target) {
        "product" -> Screen.ProductDetails
        else -> null
    }
}