package com.example.android_view_edu.domain

import androidx.lifecycle.MutableLiveData

class GetShopListUseCase(private val repository: ShopListRepository) {

    fun getShopItemsList() : MutableLiveData<List<ShopItem>> = repository.getShopItemsList()

}