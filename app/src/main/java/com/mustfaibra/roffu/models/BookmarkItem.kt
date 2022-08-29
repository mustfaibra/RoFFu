package com.mustfaibra.roffu.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks")
data class BookmarkItem(
    @PrimaryKey(autoGenerate = true) val bookmarkId: Int? = null,
    val productId: Int? = null,
){
    /** This will deals with the data from server and local */
    @Ignore
    var product: Product? = null
}
