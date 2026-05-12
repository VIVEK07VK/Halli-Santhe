package com.hallisanthe.app.repository

import com.hallisanthe.app.models.CartItem
import com.hallisanthe.app.room.CartDao
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {
    val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun addOrUpdateItem(cartItem: CartItem) {
        val existingItem = cartDao.getCartItemById(cartItem.productId)
        if (existingItem != null) {
            val updatedQuantity = existingItem.quantity + cartItem.quantity
            cartDao.updateCartItem(existingItem.copy(quantity = updatedQuantity))
        } else {
            cartDao.insertOrUpdateCartItem(cartItem)
        }
    }

    suspend fun updateQuantity(productId: String, newQuantity: Int) {
        if (newQuantity <= 0) {
            removeItem(productId)
            return
        }
        val existingItem = cartDao.getCartItemById(productId)
        if (existingItem != null) {
            cartDao.updateCartItem(existingItem.copy(quantity = newQuantity))
        }
    }

    suspend fun removeItem(productId: String) {
        val existingItem = cartDao.getCartItemById(productId)
        if (existingItem != null) {
            cartDao.deleteCartItem(existingItem)
        }
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
