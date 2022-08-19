package com.mustfaibra.shoesstore.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mustfaibra.shoesstore.models.*
import kotlinx.coroutines.flow.Flow

// 3/20/2022

@Dao
interface RoomDao {

    /** Manufacturer operations */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManufacturer(manufacturer: Manufacturer)

    @Query("SELECT * FROM manufacturer")
    suspend fun getManufacturersWithProducts(): List<LocalManufacturer>


    /** Products operations */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOtherProductCopy(productColor: ProductColor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSize(productSize: ProductSize)


    /** Advertisements operations */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdvertisement(advertisement: Advertisement)

    @Query("SELECT * FROM advertisement")
    suspend fun getAdvertisements(): List<Advertisement>


    /** All Cart operations */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItem)

    @Transaction
    @Query("SELECT * from cart")
    fun getCartItems(): Flow<MutableList<CartItemWithProduct>>

    @Query("SELECT productId FROM cart")
    fun getCartProductsIds(): Flow<List<Int>>

    @Update
    suspend fun updateCartItem(cartItem: CartItem)

    @Query("UPDATE cart SET quantity = :quantity WHERE productId = :productId ")
    suspend fun updateCartItemQuantity(productId: Int, quantity: Int)

    @Query("DELETE FROM cart WHERE productId = :productId ")
    suspend fun deleteCartItem(productId: Int)

    @Query("Delete FROM cart")
    suspend fun clearCart()


    /** Bookmarks operations */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBookmarkItem(bookmarkItem: BookmarkItem)

    @Transaction
    @Query("SELECT * from bookmarks")
    fun getBookmarkItems(): Flow<MutableList<BookmarkItemWithProduct>>

    @Query("SELECT productId FROM bookmarks")
    fun getBookmarkProductsIds(): Flow<List<Int>>

    @Query("DELETE FROM bookmarks WHERE productId = :productId ")
    suspend fun deleteBookmarkItem(productId: Int)

    @Query("Delete FROM bookmarks")
    suspend fun clearBookmark()

    /** Product reviews */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<Review>)


    /** User operations */
    @Query("SELECT * FROM user WHERE token != null")
    suspend fun getLoggedUser(): User?

}