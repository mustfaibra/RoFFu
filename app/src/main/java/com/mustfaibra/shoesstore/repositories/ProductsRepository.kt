package com.mustfaibra.shoesstore.repositories

import com.mustfaibra.shoesstore.data.local.RoomDao
import com.mustfaibra.shoesstore.models.BookmarkItem
import com.mustfaibra.shoesstore.models.CartItem
import com.mustfaibra.shoesstore.models.Product
import com.mustfaibra.shoesstore.models.ProductDetails
import com.mustfaibra.shoesstore.sealed.DataResponse
import com.mustfaibra.shoesstore.sealed.Error
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val dao: RoomDao,
) {

    /** Cart operations */
    fun getCartProductsIdsFlow() = dao.getCartProductsIds()

    suspend fun updateCartState(productId: Int, alreadyOnCart: Boolean) {
        /** Handle the local storing process */
        handleLocalCart(productId = productId, alreadyOnCart = alreadyOnCart)
        /** Handle the remote process */
    }

    private suspend fun handleLocalCart(productId: Int, alreadyOnCart: Boolean) {
        if (alreadyOnCart) {
            /** Already on local , delete it */
            dao.deleteCartItem(productId = productId)
        } else {
            /** not on local , add it */
            val cartItem = CartItem(
                productId = productId,
                quantity = 1,
            )
            addToLocalCart(cartItem = cartItem)
        }
    }

    private suspend fun addToLocalCart(cartItem: CartItem) {
        /** Add it to cart items */
        dao.insertCartItem(cartItem = cartItem)
    }

    fun getLocalCart() = dao.getCartItems()

    suspend fun clearCart() {
        dao.clearCart()
    }

    suspend fun updateCartItemQuantity(id: Int, quantity: Int) {
        /** Update local cart item quantity */
        dao.updateCartItemQuantity(productId = id, quantity = quantity)
    }


    /** Bookmarks operations */

    fun getBookmarksProductsIdsFlow() = dao.getBookmarkProductsIds()

    suspend fun updateBookmarkState(productId: Int, alreadyOnBookmark: Boolean) {
        /** Handle the local storing process */
        handleLocalBookmark(productId = productId, alreadyOnBookmark = alreadyOnBookmark)
        /** Handle the remote process */
    }

    private suspend fun handleLocalBookmark(productId: Int, alreadyOnBookmark: Boolean) {
        if (alreadyOnBookmark) {
            /** Already on local , delete it */
            dao.deleteBookmarkItem(productId = productId)
        } else {
            /** Add it to bookmark items */
            dao.insertBookmarkItem(bookmarkItem = BookmarkItem(productId = productId))
        }
    }

    fun getLocalBookmarks() = dao.getBookmarkItems()

    suspend fun getProductDetails(productId: Int): DataResponse<Product> {
        /** Check the local storage */
        dao.getProductDetails(productId = productId)?.let {
            return DataResponse.Success(data = it.getStructuredProducts())
        }
        /** Doesn't exist on the local, check remote */
        return DataResponse.Error(error = Error.Network)
    }

}

private fun ProductDetails.getStructuredProducts(): Product {
    return this.product.also {
        it.colors = this.colors
        it.reviews = this.reviews
        it.sizes = this.sizes
        it.manufacturer = this.manufacturer
    }
}
