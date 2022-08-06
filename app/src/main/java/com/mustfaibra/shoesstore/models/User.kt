package com.mustfaibra.shoesstore.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Int,
    val name: String,
    val profile: String? = null,
    val phone: String? = null,
    val email: String? = null,
    val gender: Int? = 1,
    val token: String? = null,
)
