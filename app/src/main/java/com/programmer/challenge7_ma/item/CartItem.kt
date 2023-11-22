package com.programmer.challenge7_ma.item

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_item")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val foodName: String,
    var totalPrice: Int,
    var price: Int,
    var quantity: Int,
    val imageResourceId: String,
    var note: String? = ""
) {
    fun calculateTotalPrice(): Int {
        return quantity * price
    }
}
