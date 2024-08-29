package com.example.android_view_edu.domain

class AddShopItemUseCase(private val repository : ShopListRepository) {

    fun addShopItem(shopItem: ShopItem) {
        repository.addShopItem(shopItem)
    }

}