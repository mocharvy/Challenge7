package com.programmer.challenge7_ma.menu

data class OrderRequest(
    val orders: List<Order>,
    val total: Int?,
    val username: String?
)
