package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mustfaibra.roffu.sealed.AdvertisementType

@Entity
data class Advertisement(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val image: Int,
    val advertisedId: Int,
    val type: Int = 0,
){
    val advertisementType: AdvertisementType
        get() = when(type){
            0 -> AdvertisementType.Product
            else -> AdvertisementType.Store
        }
}