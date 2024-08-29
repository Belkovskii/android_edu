package com.example.android_view_edu.domain

class GetShopListUseCase(private val repository: ShopListRepository) {

    fun getShopItemsList() : List<ShopItem> = repository.getShopItemsList()

}