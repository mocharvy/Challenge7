package com.programmer.challenge7_ma.menu

import com.google.gson.annotations.SerializedName

class DataCategory (
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?
)