package com.programmer.challenge7_ma.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import com.programmer.challenge7_ma.menu.MenuCategory
import com.programmer.challenge7_ma.menu.MenuList
import com.programmer.challenge7_ma.menu.OrderRequest
import com.programmer.challenge7_ma.menu.OrderResponse

interface APIService {
    @GET("listmenu")
    fun getListMenu(
    ): Call<MenuList>

    @GET("category-menu")
    fun getCategoryMenu(
    ): Call<MenuCategory>

    @POST("order-menu")
    fun order(
        @Body orderRequest: OrderRequest
    ): Call<OrderResponse>
}

