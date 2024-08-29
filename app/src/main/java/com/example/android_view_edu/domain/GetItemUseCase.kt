package com.example.android_view_edu.domain

class GetItemUseCase(private val repository : ShopListRepository) {

    fun getShopItem(id : Int) : ShopItem? = repository.getShopItem(id)

}