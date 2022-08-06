package com.mustfaibra.shoesstore.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mustfaibra.shoesstore.models.Advertisement
import com.mustfaibra.shoesstore.models.CartItem
import com.mustfaibra.shoesstore.models.CartItemWithProduct
import com.mustfaibra.shoesstore.models.Manufacturer
import com.mustfaibra.shoesstore.models.Product
import com.mustfaibra.shoesstore.models.ProductColor
import com.mustfaibra.shoesstore.models.ProductSize
import com.mustfaibra.shoesstore.models.Review
import com.mustfaibra.shoesstore.models.User
import kotlinx.coroutines.flow.Flow

// 3/20/2022

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManufacturer(manufacturer: Manufacturer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOtherProductCopy(productColor: ProductColor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSize(productSize: ProductSize)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAdvertisement(advertisement: Advertisement)

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

    @Delete
    suspend fun deleteCartItem(cartItem: CartItem)

    @Query("Delete FROM cart")
    suspend fun clearCart()

    /** Product reviews */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviews(reviews: List<Review>)

    @Query("SELECT * FROM user WHERE token != null")
    suspend fun getLoggedUser(): User?
}