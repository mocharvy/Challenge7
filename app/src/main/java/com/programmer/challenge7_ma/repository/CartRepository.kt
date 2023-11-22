package com.programmer.challenge7_ma.repository

import androidx.lifecycle.LiveData
import com.programmer.challenge7_ma.database.CartItemDao
import com.programmer.challenge7_ma.item.CartItem

class CartRepository(private val cartDao: CartItemDao) {

    val allCartItems: LiveData<List<CartItem>> = cartDao.getAllCartItems()

    fun insertCartItem(cartItem: CartItem) {
        cartDao.insertCartItem(cartItem)
    }

    fun deleteCartItem(cartItem: CartItem) {
        cartDao.deleteCartItem(cartItem)
    }

    fun updateCartItem(cartItem: CartItem) {
        cartDao.updateCartItem(cartItem)
    }

    fun deleteAllCartItems() {
        cartDao.deleteAllCartItems()
    }

    fun getCartByFoodName(foodName:String):CartItem?{
        return cartDao.getCartItemByFoodName(foodName)
    }
}
