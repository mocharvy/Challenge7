package com.programmer.challenge7_ma.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.programmer.challenge7_ma.api.ApiClient
import com.programmer.challenge7_ma.menu.MenuCategory
import com.programmer.challenge7_ma.menu.DataCategory
import com.programmer.challenge7_ma.menu.MenuListData
import com.programmer.challenge7_ma.menu.MenuList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuViewModel : ViewModel() {
    private val listMenu = MutableLiveData<List<MenuListData>>()
    private val categories = MutableLiveData<List<DataCategory>>()
    fun setListMenu() {
        ApiClient.instance
            .getListMenu()
            .enqueue(object : Callback<MenuList> {
                override fun onResponse(
                    call: Call<MenuList>,
                    response: Response<MenuList>
                ) {
                    if (response.isSuccessful){
                        listMenu.postValue(response.body()?.data)
                    }
                }
                override fun onFailure(call: Call<MenuList>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })

    }

    fun getListMenu(): LiveData<List<MenuListData>> = listMenu

    fun setCategories() {
        ApiClient.instance
            .getCategoryMenu()
            .enqueue(object : Callback<MenuCategory> {
                override fun onResponse(
                    call: Call<MenuCategory>,
                    response: Response<MenuCategory>
                ) {
                    if (response.isSuccessful){
                        categories.postValue(response.body()?.data)
                    }
                }
                override fun onFailure(call: Call<MenuCategory>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })

    }

    fun getCategories(): MutableLiveData<List<DataCategory>> = categories
}