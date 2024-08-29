package com.example.android_view_edu.domain

class DeleteShoItemUseCase(private val repository : ShopListRepository) {

    fun deleteShopItem(id : Int) {
        repository.deleteShopItem(id)
    }

}