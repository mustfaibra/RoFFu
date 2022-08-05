package com.mustfaibra.shoesstore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mustfaibra.shoesstore.models.Version

// 3/20/2022

@Database(
    entities = [Version::class],
    version = 1, exportSchema = false)
abstract class RoomDatabase : RoomDatabase() {

    /** A function that used to retrieve Room's related dao instance */
    abstract fun getDao(): RoomDao
}