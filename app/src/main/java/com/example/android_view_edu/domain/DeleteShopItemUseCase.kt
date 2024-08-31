package com.example.android_view_edu.domain

class DeleteShopItemUseCase(private val repository : ShopListRepository) {

    fun deleteShopItem(id : Int) {
        repository.deleteShopItem(id)
    }

}