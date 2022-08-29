package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val address: String,
    val city: String,
    val country: String,
)
