package com.programmer.challenge7_ma.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.programmer.challenge7_ma.item.CartItem

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_item")
    fun getAllCartItems(): LiveData<List<CartItem>>

    @Query("SELECT * FROM cart_item WHERE foodName = :foodName LIMIT 1")
    fun getCartItemByFoodName(foodName: String): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(cartItem: CartItem)

    @Update
    fun updateCartItem(cartItem: CartItem)

    @Delete
    fun deleteCartItem(cartItem: CartItem)

    @Query("DELETE FROM cart_item")
    fun deleteAllCartItems()
}
